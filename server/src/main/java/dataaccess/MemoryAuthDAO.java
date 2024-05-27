package dataaccess;

import chess.model.AuthData;

public interface MemoryAuthDAO {
    void clearAuth();
    AuthData getAuth(String auth);
    String createAuth(String username);
    void deleteSession(String auth);
}
