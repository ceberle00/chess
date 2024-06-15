package websocket.commands;

public class Connect extends UserGameCommand {
    private final Integer gameID;
    public Connect(String authToken, Integer gameID) {
        super(authToken, gameID);
        commandType = UserGameCommand.CommandType.CONNECT;
        this.gameID = gameID;
    }
    public Integer getGameID() {
        return this.gameID;
    }
}
