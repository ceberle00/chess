package dataaccess;

import chess.model.AuthData;

public interface MemoryAuthDAO {
    void clearAuth();
    AuthData getAuth(String auth);
    void createAuth(String username);
    void deleteSession(String auth);
}
