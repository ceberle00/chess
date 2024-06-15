package server;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import java.util.Set;
import org.eclipse.jetty.ee9.websocket.api.*;
import org.eclipse.jetty.ee9.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.ee9.websocket.api.annotations.WebSocket;

import websocket.commands.UserGameCommand;

@WebSocket
public class WebsocketHandler {
    private String url = "";
    private Map<Integer, Set<Session>> games = new HashMap<>();
    private Map<String, Session> Connections = new HashMap<>();
    public WebsocketHandler(String url) {
        this.url = url;
    }
    @OnWebSocketMessage
    public void onMessage(Session session, String msg) {
        try {
            UserGameCommand command = new Gson().fromJson(msg, UserGameCommand.class);
        }catch (Exception e) 
        {

        }
    }
}
