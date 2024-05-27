package dataaccess;
import chess.model.*;
import java.util.Vector;

public interface MemoryGameDAO {
    void clearGames();
    Vector<GameData> getGames();
    GameData getGameName(String name);
    void createGame(String name);
    GameData checkGame(int num);
}