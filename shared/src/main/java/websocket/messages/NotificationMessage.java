package websocket.messages;


public class NotificationMessage extends ServerMessage {
    private final String message; //instructions said could be anything, not sure if right
    public NotificationMessage(String error) {
        super(ServerMessageType.NOTIFICATION);
        this.message = error;
    }
    public String getMessage() {
        return this.message;
    }
}
