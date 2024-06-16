package server;

import java.io.IOException;
import com.google.gson.Gson;

import websocket.commands.UserGameCommand;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
@WebSocket
public class WebsocketHandler 
{

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException { 
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
        switch (command.getCommandType()) {
            case CONNECT:
                System.out.print("tee hee");
                //LoadGameMessage val = new LoadGameMessage("% just joined the game", null);
            case LEAVE:
            case RESIGN:
            case MAKE_MOVE:
        }
     }
     
}
