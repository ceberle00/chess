package passoff.service;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import service.GameService;
import service.SystemService;
import service.UserService;

import java.util.ArrayList;

import org.junit.jupiter.api.*;

import chess.ChessGame.TeamColor;
import chess.model.AuthData;
import chess.model.GameData;
import chess.model.UserData;

public class ServiceTests {

    private UserDAO user = new UserDAO();
    private AuthDAO auto = new AuthDAO();
    private GameDAO game = new GameDAO();

    @BeforeEach
    public void setUp() {
        this.user.clearUsers();
        this.auto.clearAuth();
        this.game.clearGames();
    }
    @Test
    public void clearTest() throws Exception
    {
        user.createUser("username", "password", "email");
        user.createUser("username2", "password2", "newEmail");

        String auth1=auto.createAuth("username");
        String auth2=auto.createAuth("username2");

        game.createGame("newGame");

        SystemService service = new SystemService(auto, game, user);
        service.clear();
        //now check
        Assertions.assertNull(user.getUser("username"));
        Assertions.assertNull(user.getUser("username2"));
        Assertions.assertNull(auto.getAuth(auth1));
        Assertions.assertNull(auto.getAuth(auth2));
        Assertions.assertNull(game.getGameName("newGame"));

    }

    
    @Test
    public void joinGameTestPass() throws Exception
    {
        user.createUser("username", "password", "email");
        String auth1=auto.createAuth("username");
        int id = game.createGame("newGame");

        GameService service = new GameService(game, auto);
        service.joinGame(id, "newGame", TeamColor.WHITE, auth1);
        GameData expectedGame = new GameData(id, "username", null, "newGame", service.getGame().checkGame(id).game());
        Assertions.assertEquals(expectedGame, service.getGame().checkGame(id));
    }
    @Test
    public void joinGameTestFail() throws Exception 
    {
        user.createUser("username", "password", "email");
        String auth1=auto.createAuth("username");
        int id = game.createGame("newGame");

        GameService service = new GameService(game, auto);
        service.joinGame(id, "newGame", TeamColor.WHITE, auth1);
        DataAccessException exception = Assertions.assertThrows(DataAccessException.class, () -> {
            service.joinGame(id, "newGame", TeamColor.WHITE, auth1);
        });
        Assertions.assertEquals("color already taken", exception.getMessage());
        //it should fail, make sure that it throws an exception for 
    }
    @Test
    public void listGamesTest() throws Exception 
    {
        user.createUser("username", "password", "email");
        String auth1=auto.createAuth("username");
        int id = game.createGame("newGame");
        int id2 = game.createGame("newSecondGame");
        GameService service = new GameService(game, auto);
        ArrayList<GameData> gamesWanted = new ArrayList<>();
        gamesWanted.add(game.checkGame(id));
        gamesWanted.add(game.checkGame(id2));
        Assertions.assertEquals(gamesWanted, service.listGames(auth1));
    }
    @Test 
    public void listGamesEmptyTest() throws Exception
    {
        user.createUser("username", "password", "email");
        String auth1=auto.createAuth("username");
        game.createGame("newGame");
        game.createGame("newSecondGame");
        GameService service = new GameService(game, auto);
        ArrayList<GameData> gamesWanted = new ArrayList<>();
        game.clearGames();
        Assertions.assertEquals(gamesWanted, service.listGames(auth1));
    }
    @Test
    public void registerNewUserTest() throws Exception 
    {
        UserService service = new UserService(user, auto);
        //create new user 
        UserData user = service.getUser("username");
        Assertions.assertNull(user);

        service.createUser("username", "password", "email");
        service.createAuth("username");
        UserData wantedUser = new UserData("username", "password", "email");
        Assertions.assertEquals(service.getUser("username"), wantedUser);
    }
    @Test public void registerExistingUser() throws Exception 
    {
        UserService service = new UserService(user, auto);
        //create new user 
        UserData user = service.getUser("username");
        Assertions.assertNull(user);

        service.createUser("username", "password", "email");
        service.createAuth("username");
        DataAccessException exception = Assertions.assertThrows(DataAccessException.class, () -> {
            service.createUser("username", "samepass", "otheremail");;
        });
        Assertions.assertEquals("user already taken", exception.getMessage());
    }
}
