package websocket.commands;

public class Leave extends UserGameCommand{
    public Leave(String authToken, Integer gameID) {
        super(authToken, gameID);
        commandType = UserGameCommand.CommandType.LEAVE;
    }
    
}
