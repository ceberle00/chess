package dataaccess;

import chess.model.AuthData;

public interface MemoryAuthDAO {
    void clearAuth() throws DataAccessException;
    AuthData getAuth(String auth) throws DataAccessException;
    String createAuth(String username) throws DataAccessException;
    void deleteSession(String auth) throws DataAccessException;
}
