package websocket.messages;


public class ErrorMessage extends ServerMessage{
    private final String errorMessage; //instructions said could be anything, not sure if right
    public ErrorMessage(String error) {
        super(ServerMessageType.ERROR);
        this.errorMessage = error;
    }
    public String getMessage() {
        return this.errorMessage;
    }
}
