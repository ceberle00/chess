package websocket.messages;


public class NotificationMessage extends ServerMessage {
    private final String ErrorMessage; //instructions said could be anything, not sure if right
    public NotificationMessage(String error) {
        super(ServerMessageType.NOTIFICATION);
        this.ErrorMessage = error;
    }
    public String getMessage() {
        return this.ErrorMessage;
    }
}
