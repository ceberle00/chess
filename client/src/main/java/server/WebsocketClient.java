package server;

import com.google.gson.Gson;

import chess.ChessMove;
import chess.ChessGame.TeamColor;
import chess.model.GameData;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

import websocket.commands.*;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;
public class WebsocketClient extends Endpoint
{
    private String url = "";
    private GameData game;
    private Session sesh;
    private NotificationHandler notificationHandler = new NotificationHandler();

    public void setGame(GameData g) {
        this.game = g;
    }
    public GameData getGame() {
        return this.game;
    }
    public WebsocketClient(Integer url) throws Exception {
        try {
            String urlString = "ws://localhost:";
            urlString += String.valueOf(url);
            urlString += "/ws";
            this.url = urlString;
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.sesh = container.connectToServer(this, new URI(this.url));
            this.sesh.addMessageHandler(new MessageHandler.Whole<String>() {
                public void onMessage(String message) {
                    ServerMessage mess = new Gson().fromJson(message, ServerMessage.class);
                    switch(mess.getServerMessageType()) {
                        case ERROR:
                            ErrorMessage err = new Gson().fromJson(message, ErrorMessage.class);
                            notificationHandler.sendError(err);
                            break;
                        case NOTIFICATION:
                            NotificationMessage not = new Gson().fromJson(message, NotificationMessage.class);
                            notificationHandler.notify(not);
                            break;
                        case LOAD_GAME:
                            try {
                                LoadGameMessage loadGame = new Gson().fromJson(message, LoadGameMessage.class);
                                setGame(loadGame.getGame());
                                notificationHandler.loadGame(loadGame);
                            } catch (Exception e) {
                                System.out.println("Error:" + e.getMessage());
                            }
                            break;
                    }
                    
                }
            });
        }catch (Exception e) {
            System.out.print(e.getMessage());
            e.printStackTrace();
        }
        
    }
    
    @Override
    public void onOpen(Session session, EndpointConfig config) {
        this.sesh = session;
    }
    public void connect(String authToken, Integer game, TeamColor color) throws IOException{
        try {
            Connect con = new Connect(authToken, game, color);
            this.sesh.getBasicRemote().sendText(new Gson().toJson(con));
        }catch (IOException ex) {
            throw new IOException(ex.getMessage());
        }
    }
    public void leave(String authToken, Integer game) throws IOException {
        try {
            Leave leave = new Leave(authToken, game);
            this.sesh.getBasicRemote().sendText(new Gson().toJson(leave));
        }catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }
    public void resign(String authToken, Integer game) throws IOException {
        try {
            Resign resign = new Resign(authToken, game);
            this.sesh.getBasicRemote().sendText(new Gson().toJson(resign));
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }
    public void makeMove(String authToke, Integer game, ChessMove move) throws IOException {
        try {
            MakeMoveCommand makeMove = new MakeMoveCommand(authToke, game, move);
            this.sesh.getBasicRemote().sendText(new Gson().toJson(makeMove));
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }
}
