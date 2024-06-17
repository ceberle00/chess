package server;

import com.google.gson.Gson;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import chess.ChessGame.TeamColor;
import chess.model.*;
import java.util.Collection;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLGameDAO;
import websocket.commands.*;
import websocket.messages.*;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class WebsocketHandler 
{
    private final ConnectionManager connections = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception { 
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
        switch (command.getCommandType()) {
            case CONNECT:
                Connect con = new Gson().fromJson(message, Connect.class);
                connect(con, session);
                break;
            case LEAVE:
                Leave leave = new Gson().fromJson(message, Leave.class);
                try {
                    leave(leave, session);
                } catch (Exception e) {
                    throw new Exception(e.getMessage());
                }
                break;
            case RESIGN:
                Resign resign = new Gson().fromJson(message, Resign.class);
                resign(resign, session);
                break;
            case MAKE_MOVE:
                MakeMoveCommand makeMoveCommand = new Gson().fromJson(message, MakeMoveCommand.class);
                makeMove(makeMoveCommand, session);
                break;
        }
    }
    public void leave(Leave l, Session session) throws Exception{
        Integer gameID = l.getGameID();
        String username = new SQLAuthDAO().getAuth(l.getAuthString()).getUser();
        String message = String.format("%s left the game", username);
        try {
            new SQLGameDAO().leaveGame(l.getAuthString(), gameID);
            var notification = new NotificationMessage(message);
            connections.broadcast(l.getAuthString(), gameID, notification); //this should broadcast what we need
            connections.remove(l.getAuthString(), gameID, session);
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    public void resign(Resign l, Session session) throws Exception{
        Integer gameID = l.getGameID();
        connections.addSession(l.getAuthString(), gameID, session);
        if (new SQLAuthDAO().getAuth(l.getAuthString()) == null) {
            String message = "Invalid Auth";
            ErrorMessage error = new ErrorMessage(message);
            connections.sendMessage(l.getAuthString(),gameID, error);
        }
        else {
            String username = new SQLAuthDAO().getAuth(l.getAuthString()).getUser();
            GameData game = new SQLGameDAO().checkGame(gameID);
            if (!(username.equals(game.blackUsername())) && !(username.equals(game.whiteUsername()))) {
                String message = "You can't resign from a game you aren't in";
                ErrorMessage error = new ErrorMessage(message);
                connections.sendMessage(l.getAuthString(),gameID, error);
            }
            else if (game.game().getIsDone()) {
                String message = "Game is already over";
                ErrorMessage error = new ErrorMessage(message);
                connections.sendMessage(l.getAuthString(),gameID, error);
            }
            else {
                String message = String.format("%s has resigned", username);
                try {
                    SQLGameDAO games = new SQLGameDAO();
                    ChessGame game2 = game.game();
                    game2.setIsDone(true);
                    games.updateGame(gameID, game2);
                    var notification = new NotificationMessage(message);
                    connections.sendMessageToEveryone(l.getAuthString(), gameID, notification); //this should broadcast what we need
                }catch (Exception e) {
                    throw new Exception(e.getMessage());
                }
            }
        }
        
        
    }
    public void connect(Connect con, Session session) throws Exception
    {
        
        String auth = con.getAuthString();
        Integer id = con.getGameID();
        GameData game = new SQLGameDAO().checkGame(id);
        connections.addSession(auth, id, session);
        if (auth == null) {
            ErrorMessage message = new ErrorMessage("User not found");
            connections.sendError(auth, id, message);
        }
        else if (new SQLAuthDAO().getAuth(auth) == null) {
            ErrorMessage message = new ErrorMessage("User not found");
            connections.sendError(auth, id, message);
        }
        
        else if (game == null) {
            ErrorMessage message = new ErrorMessage("Game not found");
            connections.sendError(auth, id, message);

        }
        else {
            String username = new SQLAuthDAO().getAuth(auth).getUser();
            var message = "";
            if (con.gTeamColor() == null) {
                message = String.format("%s is watching the game", username);
                LoadGameMessage load = new LoadGameMessage(message, new SQLGameDAO().checkGame(id));
                NotificationMessage notificationMessage = new NotificationMessage(message);
                connections.broadcast(auth, id, notificationMessage);
                connections.sendMessage(auth, id, load);
            }
            else {
                String color = "";
                if (con.gTeamColor() == ChessGame.TeamColor.WHITE) {
                    color = "WHITE";
                }
                else {
                    color = "BLACK";
                }
                if (color.equals("BLACK") && (game.blackUsername() != null)) {
                    ErrorMessage mess = new ErrorMessage("Color already taken");
                    connections.sendError(auth, id, mess);
                }
                else if (color.equals("WHITE") && (game.whiteUsername() != null)) {
                    ErrorMessage mess = new ErrorMessage("Color already taken");
                    connections.sendError(auth, id, mess);
                }
                else {
                    message = String.format("%s has joined game as %s", username, color);
                    LoadGameMessage load = new LoadGameMessage(message, new SQLGameDAO().checkGame(id));
                    NotificationMessage notificationMessage = new NotificationMessage(message);
                    connections.broadcast(auth, id, notificationMessage);
                    connections.sendMessage(auth, id, load);
                }
                
            }
        }
        
    }
    public void makeMove(MakeMoveCommand make, Session session) throws Exception {
        Integer gameID = make.getGameID();
        GameData game = new SQLGameDAO().checkGame(gameID);
        connections.addSession(make.getAuthString(), gameID, session);
        if (new SQLAuthDAO().getAuth(make.getAuthString()) == null) {
            String message = "User does not exist";
            ErrorMessage error = new ErrorMessage(message);
            connections.sendError(make.getAuthString(), gameID, error);
        }
        else if (new SQLGameDAO().checkGame(make.getGameID()) == null) {
            String message = "Game does not exist";
            ErrorMessage error = new ErrorMessage(message);
            connections.sendError(make.getAuthString(), gameID, error);
        }
        else if (!isInGame(new SQLAuthDAO().getAuth(make.getAuthString()).getUser(), game)){
            String message = "You are not a player!";
            ErrorMessage error = new ErrorMessage(message);
            connections.sendError(make.getAuthString(), gameID, error);
        }
        else if (!isTurn(new SQLAuthDAO().getAuth(make.getAuthString()).getUser(), game)) {
            String message = "It is not your turn";
            ErrorMessage error = new ErrorMessage(message);
            connections.sendError(make.getAuthString(), gameID, error);
        }
        else if (game.game().getIsDone()) {
            String message = "The game is over";
            ErrorMessage error = new ErrorMessage(message);
            connections.sendError(make.getAuthString(), gameID, error);
        }
        else {
            String username = new SQLAuthDAO().getAuth(make.getAuthString()).getUser();
            ChessMove move = make.getMove();
            if (!isMoveValid(move, game)) {
                String message = "Move is not valid";
                ErrorMessage error = new ErrorMessage(message);
                connections.sendError(make.getAuthString(), gameID, error);
            }
            else {
                String start = convertPositionToString(move.getStartPosition());
                String end = convertPositionToString(move.getEndPosition());
                ChessGame newGame = game.game();
                newGame.makeMove(move);
                GameData value = new GameData((int)gameID, game.blackUsername(), game.whiteUsername(), game.gameName(), newGame);
                String message = String.format("%s has moved from %s to %s", username, start, end);
                LoadGameMessage load = new LoadGameMessage(message, value);
                new SQLGameDAO().updateGame(gameID, newGame);
                System.out.println(new SQLGameDAO().checkGame((int)gameID).game().getTeamTurn());
                if (new SQLGameDAO().checkGame(gameID).game().equals(newGame)) {
                    NotificationMessage notify = new NotificationMessage(message);
                    connections.broadcast(make.getAuthString(), gameID, notify);
                    connections.sendMessageToEveryone(make.getAuthString(), gameID, load);
                }
                else {
                    System.out.println("OH NO");
                }
                
            }
            
        }
    }
    public String convertPositionToString(ChessPosition pos) {
        String[] headers = {"a", "b", "c", "d", "e", "f", "g", "h"};
        String column = "";
        for (int i = 0; i < headers.length; i++) {
            if ((i+1) == pos.getColumn()) {
                column = headers[i];
                break;
            }
        }
        column += pos.getRow();
        return column;

    }
    public Boolean isTurn(String username, GameData game) {
        Boolean isTurn = false;
        if (username.equals(game.blackUsername())) {
            if (game.game().getTeamTurn() == TeamColor.BLACK) {
                isTurn = true;
            }
        }
        else if (username.equals(game.whiteUsername())) {
            if (game.game().getTeamTurn() == TeamColor.WHITE) {
                isTurn = true;
            }
        }
        return isTurn;
    }
    public Boolean isInGame(String username, GameData game) {
        Boolean isInGame = false;
        if (username.equals(game.blackUsername())) {
            isInGame = true;
        }
        else if (username.equals(game.whiteUsername())) {
            isInGame = true;
        }
        return isInGame;
    }
    public Boolean isMoveValid(ChessMove move, GameData game) {
        ChessGame chessGame = game.game();
        Boolean isValid = false;
        Collection<ChessMove> moves = chessGame.validMoves(move.getStartPosition());
        if (moves.contains(move)) {
            isValid = true;
        }
        return isValid;
    }
    public Boolean isCheck(ChessMove move, GameData game) {
        return null;
    }
}
