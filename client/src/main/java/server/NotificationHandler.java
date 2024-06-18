package server;

import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;

public class NotificationHandler {
    public NotificationHandler() {};
    public void notify(NotificationMessage notification) {
        System.out.println("Notification: " + notification.getMessage());
    }
    public void sendError(ErrorMessage message) 
    {
        System.out.println("Error: " + message.getMessage());
    }
    public void loadGame(LoadGameMessage message) 
    {
        System.out.println("Game: " + message.getMessage());
    }
}
