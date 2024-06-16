package server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import com.google.gson.Gson;
import java.util.Set;

import javax.management.Notification;
import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

import websocket.commands.*;
import websocket.messages.ServerMessage;
public class WebsocketClient extends Endpoint
{
    private String url = "";
    private String authToken;
    private Session sesh;
    private Map<Integer, Set<Session>> games = new HashMap<>();

    public WebsocketClient(Integer url) throws Exception {
        try {
            String urlString = "ws://localhost:";
            urlString += String.valueOf(url);
            urlString += "/ws";
            this.url = urlString;
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.sesh = container.connectToServer(this, new URI(this.url));
             this.sesh.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    ServerMessage mess = new Gson().fromJson(message, ServerMessage.class);
                }
            });
        }catch (Exception e) {
            System.out.print(e.getMessage());
            e.printStackTrace();
        }
        
    }
    
    /*public void onMessage(String msg) {
        try {
            UserGameCommand command = new Gson().fromJson(msg, UserGameCommand.class);
            authToken = command.getAuthString();
            Integer gameId = command.getGameID();
            if (games.containsKey(gameId)) {
                games.get(gameId).add(this.sesh);
            } else {
                Set<Session> sessions = new HashSet<>();
                sessions.add(this.sesh);
                games.put(gameId, sessions);
            }
            switch (command.getCommandType()) {
                case CONNECT:
                    Connect con = new Connect(authToken, gameId);
                    connect(msg, con);
                case MAKE_MOVE:
                    //MakeMoveCommand move = new MakeMoveCommand(authToken, gam)
                case LEAVE:

                case RESIGN:
            }
        }catch (Exception e) 
        {

        }

    }*/
    @Override
    public void onOpen(Session session, EndpointConfig config) {
        this.sesh = session;
        System.out.println("Connected to " + this.url);
    }
    public void connect(String authToken, Integer game) throws IOException{
        try {
            Connect con = new Connect(authToken, game);
            this.sesh.getBasicRemote().sendText(new Gson().toJson(con));
            //this.sesh.close();
        }catch (IOException ex) {
            throw new IOException(ex.getMessage());
        }
    }
}
