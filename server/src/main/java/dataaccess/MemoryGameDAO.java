package dataaccess;
import chess.ChessGame.TeamColor;
import chess.model.*;

import java.util.Map;

public interface MemoryGameDAO {
    void clearGames();
    Map<Integer, GameData> getGames();
    GameData getGameName(String name);
    int createGame(String name);
    GameData checkGame(int num);
    void joinGame(int gameID, AuthData data, TeamColor color);
}