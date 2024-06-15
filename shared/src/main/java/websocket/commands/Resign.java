package websocket.commands;


public class Resign extends UserGameCommand {
    private final Integer gameID;
    public Resign(String authToken, Integer gameID) {
        super(authToken, gameID);
        commandType = UserGameCommand.CommandType.RESIGN;
        this.gameID = gameID;
    }
}
