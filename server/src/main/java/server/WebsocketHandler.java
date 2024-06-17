package server;

import com.google.gson.Gson;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import chess.model.*;
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
                //LoadGameMessage val = new LoadGameMessage("% just joined the game", null);
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
        String username = new SQLAuthDAO().getAuth(l.getAuthString()).getUser();
        GameData game = new SQLGameDAO().checkGame(gameID);
        if (!(username.equals(game.blackUsername()) && !(username.equals(game.whiteUsername())))) {
            String message = "You can't resign from a game you aren't in";
            ErrorMessage error = new ErrorMessage(message);
            connections.sendMessage(message,gameID, error);
        }
        String message = String.format("%s has resigned", username);
        try {
            SQLGameDAO games = new SQLGameDAO();
            ChessGame game2 = game.game();
            game2.setIsDone(true);
            games.updateGame(gameID, game2);
            var notification = new NotificationMessage(message);
            connections.broadcast(l.getAuthString(), gameID, notification); //this should broadcast what we need
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    public void connect(Connect con, Session session) throws Exception
    {
        String auth = con.getAuthString();
        Integer id = con.getGameID();
        String username = new SQLAuthDAO().getAuth(auth).getUser();
        connections.addSession(auth, id, session); //just add to the game later
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
            message = String.format("%s has joined game as %s", username, color);
            LoadGameMessage load = new LoadGameMessage(message, new SQLGameDAO().checkGame(id));
            NotificationMessage notificationMessage = new NotificationMessage(message);
            connections.broadcast(auth, id, notificationMessage);
            connections.sendMessage(auth, id, load);
        }
    }
    public void makeMove(MakeMoveCommand make, Session session) throws Exception {
        try {
            Integer gameID = make.getGameID();
            String username = new SQLAuthDAO().getAuth(make.getAuthString()).getUser();
            GameData game = new SQLGameDAO().checkGame(gameID);
            ChessMove move = make.getMove();
            String start = convertPositionToString(move.getStartPosition());
            String end = convertPositionToString(move.getEndPosition());
            ChessGame newGame = game.game();
            newGame.makeMove(move);
            GameData value = new GameData(gameID, game.blackUsername(), game.whiteUsername(), game.gameName(), newGame);
            String message = String.format("%s has moved from %s to %s", username, start, end);
            LoadGameMessage load = new LoadGameMessage(message, value);
            new SQLGameDAO().updateGame(gameID, newGame);
            connections.sendMessage(make.getAuthString(), gameID, load);
            connections.broadcast(make.getAuthString(), gameID, load);
        }catch (Exception e) {
            ErrorMessage message = new ErrorMessage(e.getMessage());
            connections.sendMessage(make.getAuthString(), make.getGameID(), message);
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
}
