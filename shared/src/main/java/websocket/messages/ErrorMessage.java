package websocket.messages;


public class ErrorMessage extends ServerMessage{
    private final String ErrorMessage; //instructions said could be anything, not sure if right
    public ErrorMessage(String error) {
        super(ServerMessageType.ERROR);
        this.ErrorMessage = error;
    }
    public String getMessage() {
        return this.ErrorMessage;
    }
}
