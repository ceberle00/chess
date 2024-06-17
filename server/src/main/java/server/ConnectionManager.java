package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import org.eclipse.jetty.websocket.api.Session;

import com.google.gson.Gson;

import websocket.messages.*;


public class ConnectionManager {
    ConcurrentHashMap<Integer, ConcurrentHashMap<String, Session>> sessions = new ConcurrentHashMap<>();
    public void addSession(String auth, Integer id, Session session) {
        if (sessions.get(id) == null) {
            ConcurrentHashMap<String, Session> newGroup = new ConcurrentHashMap<>();
            newGroup.put(auth, session);
            sessions.put(id, newGroup);
        }
        else {
            sessions.get(id).put(auth, session);
        }
    }
    public void sendError(String authToken, int gameID, ErrorMessage error) {
        try {
            String toSend = new Gson().toJson(error);
            sessions.get(gameID).get(authToken).getRemote().sendString(toSend);
        }catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    public void sendMessage(String authToken, int gameID, ServerMessage message) throws IOException {
            String toSend = new Gson().toJson(message);
            if (sessions.get(gameID).get(authToken).isOpen()) {
                try {
                    sessions.get(gameID).get(authToken).getRemote().sendString(toSend);
                } catch (IOException e) {
                    System.out.print("catch");
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    System.out.print("catch");
                    e.printStackTrace();
                } catch (Exception e) {
                    System.out.print("catch");
                    e.printStackTrace();
                }
            }
            
    }
    public void remove(String auth, Integer id, Session session) {
        sessions.get(id).remove(auth, session);
    }
    public void sendMessageToEveryone(String authToken, int gameID, ServerMessage message) throws IOException {
        String toSend = new Gson().toJson(message);
        if (sessions.get(gameID).size() > 1) {
            for (var c : sessions.get(gameID).values()) {
                c.getRemote().sendString(toSend);
            }
        }
    }
    public void broadcast(String excludeVisitorName, Integer id, ServerMessage notification) throws IOException {
        if (sessions.get(id).size() > 1) {
            var removeList = new ArrayList<String>();
            for (var c : sessions.get(id).entrySet()) {
                if (c.getValue().isOpen()) {
                    if (!c.getKey().equals(excludeVisitorName)) {
                        String toSend = new Gson().toJson(notification);
                        c.getValue().getRemote().sendString(toSend); //maybe need to switch
                    }
                } else {
                    removeList.add(c.getKey());
                }
            }
            for (var c : removeList) {
                sessions.get(id).remove(c);
            }
        }
    }
}
