package dao;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLGameDAO;
import dataaccess.SQLUserDAO;
import dataaccess.UserDAO;
import service.SQLGameService;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.*;

import chess.ChessGame.TeamColor;
import chess.model.AuthData;
import chess.model.GameData;
import chess.model.UserData;

    
public class daoTests {
    private SQLAuthDAO auth = new SQLAuthDAO();
    private SQLGameDAO games = new SQLGameDAO();
    private SQLUserDAO users = new SQLUserDAO();

    @BeforeEach
    public void setUp() throws DataAccessException{
        this.auth.clearAuth();
        this.games.clearGames();
        this.users.clearUsers();
    }
    @Test
    public void clearAuthSQL() throws DataAccessException
    {
        String data =this.auth.createAuth("username");
        String b =this.auth.createAuth("username2");
        this.auth.clearAuth();
        Assertions.assertNull(this.auth.getAuth(data));
        Assertions.assertNull(this.auth.getAuth(b));
    }
    @Test
    public void clearUserSQL() throws DataAccessException
    {
        this.users.createUser("username", "password", "email");
        this.users.createUser("username2", "password2", "email2");
        this.users.clearUsers();
        Assertions.assertNull(this.users.getUser("username"));
        Assertions.assertNull(this.users.getUser("username2"));
    }
    @Test
    public void clearGamesSQL() throws DataAccessException
    {
        this.games.createGame("name");
        this.games.createGame("name2");
        this.games.clearGames();;
        Assertions.assertNull(this.games.getGameName("name"));
        Assertions.assertNull(this.games.getGameName("name2"));
    }
    @Test
    public void getAuthSQL()throws DataAccessException {
        String data =this.auth.createAuth("username");
        String b =this.auth.createAuth("username2");
        AuthData value = new AuthData(data, "username");
        AuthData value2 = new AuthData(b, "username2");
        assertEquals(this.auth.getAuth(data), value);
        assertEquals(this.auth.getAuth(b), value2);
    }
    @Test
    public void getAuthSQLFail() throws DataAccessException {
        String value = this.auth.createAuth("username");
        this.auth.clearAuth();
        Assertions.assertNull(this.auth.getAuth(value));
        //Assertions.assertEquals("Error: unauthorized", exception.getMessage());
    }
}
