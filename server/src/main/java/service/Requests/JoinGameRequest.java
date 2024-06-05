package service.Requests;

import chess.ChessGame.TeamColor;

public class JoinGameRequest {
    private TeamColor playerColor;
    private Integer gameID;

    public JoinGameRequest(TeamColor color, Integer id) {
        this.playerColor = color;
        this.gameID = id;
    }
    public Integer getGameID() {
        return this.gameID;
    }
    public TeamColor getColor() {
        return this.playerColor;
    }
}
