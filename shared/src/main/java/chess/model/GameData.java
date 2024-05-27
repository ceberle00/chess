package chess.model;

import chess.ChessGame;

public record GameData(int gameID, String whiteUsername, String blackUsername, String Gamename, ChessGame game ) {
    public String getGameName() {
        return Gamename;
    }
    public int getID() {
        return gameID;
    }
}
