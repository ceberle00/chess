package websocket.commands;

public class Connect extends UserGameCommand {
    public Connect(String authToken, Integer gameID) {
        super(authToken, gameID);
        commandType = UserGameCommand.CommandType.CONNECT; //see if this works
    }
}
