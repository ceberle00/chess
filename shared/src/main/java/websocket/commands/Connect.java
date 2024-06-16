package websocket.commands;

import chess.ChessGame.TeamColor;

public class Connect extends UserGameCommand {
    private TeamColor color;
    public Connect(String authToken, Integer gameID, TeamColor color) {
        super(authToken, gameID);
        commandType = UserGameCommand.CommandType.CONNECT; //see if this works
        this.color = color;
    }
    public TeamColor gTeamColor() {
        return this.color;
    }
}
