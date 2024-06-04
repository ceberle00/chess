package dataaccess;
import chess.model.*;

public interface MemoryUserDAO {
    void clearUsers() throws DataAccessException;
    UserData getUser(String name) throws DataAccessException;
    UserData getUserPass(String name, String pass) throws DataAccessException;
    void createUser(String username, String password, String email) throws Exception;
}
