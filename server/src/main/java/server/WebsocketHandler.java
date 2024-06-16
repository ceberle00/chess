package server;

import java.io.IOException;
import com.google.gson.Gson;

import dataaccess.DataAccessException;
import dataaccess.SQLAuthDAO;
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
    public void onMessage(Session session, String message) throws IOException { 
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
        switch (command.getCommandType()) {
            case CONNECT:
                System.out.print("tee hee");
                //LoadGameMessage val = new LoadGameMessage("% just joined the game", null);
            case LEAVE:
                Leave leave = new Gson().fromJson(message, Leave.class);
                try {
                    leave(leave, session);
                } catch (Exception e) {

                }
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
}
