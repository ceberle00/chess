package dataaccess;
import chess.ChessGame;
import chess.ChessGame.TeamColor;
import chess.model.*;
import javax.xml.crypto.Data;
import java.util.Collection;

import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.List;

public interface MemoryGameDAO {
    void clearGames() throws DataAccessException;
    void setID(Integer value) throws DataAccessException;
    Collection<GameData> getGames() throws DataAccessException;
    GameData getGameName(String name) throws DataAccessException;
    Integer createGame(String name) throws DataAccessException;
    GameData checkGame(int num) throws DataAccessException;
    void joinGame(int gameID, AuthData data, TeamColor color) throws DataAccessException;
}