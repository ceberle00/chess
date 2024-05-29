package chess.model;

import chess.ChessGame;

public record GameData(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game ) {
    public String getGameName() {
        return gameName;
    }
    public int getID() {
        return gameID;
    }
}
