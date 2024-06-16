package server;

import java.io.IOException;
import com.google.gson.Gson;

import chess.ChessGame;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLGameDAO;
import websocket.commands.Connect;
import websocket.commands.Leave;
import websocket.commands.UserGameCommand;
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

                }
                break;
            case RESIGN:
            case MAKE_MOVE:
        }
    }
    public void leave(Leave l, Session session) throws Exception{
        Integer gameID = l.getGameID();
        String username = new SQLAuthDAO().getAuth(l.getAuthString()).getUser();
        String message = String.format("%s left the game", username);
        try {
            var notification = new NotificationMessage(message);
            connections.broadcast(l.getAuthString(), gameID, notification); //this should broadcast what we need
            connections.remove(l.getAuthString(), gameID, session);
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
        if (con.gTeamColor().equals(null)) {
            message = String.format("%s is watching the game", username);
            LoadGameMessage load = new LoadGameMessage(message, new SQLGameDAO().checkGame(id).game());
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
            LoadGameMessage load = new LoadGameMessage(message, new SQLGameDAO().checkGame(id).game());
            NotificationMessage notificationMessage = new NotificationMessage(message);
            connections.broadcast(auth, id, notificationMessage);
            connections.sendMessage(auth, id, load);
        }
    }
}
