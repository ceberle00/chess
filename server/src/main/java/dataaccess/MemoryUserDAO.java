package dataaccess;
import chess.model.*;

public interface MemoryUserDAO {
    void clearUsers();
    UserData getUser(String name);
    UserData getUserPass(String name, String pass);
    void createUser(String username, String password, String email);
}
