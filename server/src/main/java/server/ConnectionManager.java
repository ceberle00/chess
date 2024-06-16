package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.*;


public class ConnectionManager {
    ConcurrentHashMap<Integer, ConcurrentHashMap<String, Session>> sessions = new ConcurrentHashMap<>();
    //multiple sessions by name
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
    public void remove(String auth, Integer id, Session session) {
        sessions.get(id).remove(auth, session);
    }
    public void broadcast(String excludeVisitorName, Integer id, NotificationMessage notification) throws IOException {
        var removeList = new ArrayList<String>();
        
        for (var c : sessions.get(id).entrySet()) {
            if (c.getValue().isOpen()) {
                if (!c.getKey().equals(excludeVisitorName)) {
                    c.getValue().getRemote().sendString(notification.toString()); //maybe need to switch
                }
            } else {
                removeList.add(c.getKey());
            }
        }

        // Clean up any connections that were left open.
        for (var c : removeList) {
            sessions.get(id).remove(c);
        }
    }
}
