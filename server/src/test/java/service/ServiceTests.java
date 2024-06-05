package service;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLGameDAO;
import dataaccess.SQLUserDAO;
import dataaccess.UserDAO;

import static org.junit.jupiter.api.Assertions.*;

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
    private SQLAuthDAO auth = new SQLAuthDAO();
    private SQLGameDAO games = new SQLGameDAO();
    private SQLUserDAO users = new SQLUserDAO();
    
    @BeforeEach
    public void setUp() throws DataAccessException{
        this.user.clearUsers();
        this.auto.clearAuth();
        this.game.clearGames();
        this.auth.clearAuth();
        this.games.clearGames();
        this.users.clearUsers();
    }
    @Test
    public void clearTestSQL() throws Exception{
        users.createUser("username", "password", "email");
        users.createUser("username2", "password2", "newEmail");

        String auth1=auth.createAuth("username");
        String auth2=auth.createAuth("username2");

        games.createGame("newGame");

        SystemService service = new SystemService(auth, games, users);
        service.clear();
        //now check
        Assertions.assertNull(users.getUser("username"));
        Assertions.assertNull(users.getUser("username2"));
        Assertions.assertNull(auth.getAuth(auth1));
        Assertions.assertNull(auth.getAuth(auth2));
        //Assertions.assertNull(game.getGameName("newGame"));

    }
    @Test
    public void clearTest() throws Exception{
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
    public void authCheckPassSQL    () throws Exception {
        users.createUser("username", "password", "email");
        String auth1=auth.createAuth("username");
        games.createGame("newGame");
        SQLGameService gs = new SQLGameService(games, auth);
        AuthData data = auth.getAuth(auth1);
        assertEquals(gs.valiAuthData(auth1), data);
       
    }
    @Test 
    public void authCheckFailSQL() throws Exception {
        users.createUser("username", "password", "email");
        games.createGame("newGame");
        SQLGameService gs = new SQLGameService();
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            gs.valiAuthData("username");;
        });
        Assertions.assertEquals("Error: unauthorized", exception.getMessage());
       
    }
    @Test 
    public void authCheckPass() throws Exception {
        user.createUser("username", "password", "email");
        String auth1=auto.createAuth("username");
        game.createGame("newGame");
        GameService gs = new GameService(game, auto);
        AuthData data = auto.getAuth(auth1);
        assertEquals(gs.valiAuthData(auth1), data);
       
    }
    @Test 
    public void authCheckFail() throws Exception {
        user.createUser("username", "password", "email");
        String auth1=auto.createAuth("username");
        game.createGame("newGame");
        GameService gs = new GameService();
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            gs.valiAuthData(auth1);;
        });
        Assertions.assertEquals("Error: unauthorized", exception.getMessage());
       
    }
    @Test 
    public void createGamePassSQL() throws Exception {
        users.createUser("username", "password", "email");
        auth.createAuth("username");
        int id = games.createGame("newGame");
        assertNotNull(id);
        assertEquals("newGame", games.checkGame(id).gameName());
    }
    @Test 
    public void createGameFailSQL() throws Exception {
        users.createUser("username", "password", "email");
        String auth1=auth.createAuth("username");
        int id = games.createGame("newGame");
        SQLGameService service = new SQLGameService(games, auth);
        assertNotNull(id);
        //adding same game
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            service.createGame(auth1, "newGame");;
        });
        Assertions.assertEquals("Error: already taken", exception.getMessage());
    }
    @Test 
    public void createGamePass() throws Exception {
        user.createUser("username", "password", "email");
        auto.createAuth("username");
        int id = game.createGame("newGame");
        assertNotNull(id);
        assertEquals("newGame", game.checkGame(id).gameName());
    }
    @Test 
    public void createGameFail() throws Exception {
        user.createUser("username", "password", "email");
        String auth1=auto.createAuth("username");
        int id = game.createGame("newGame");
        GameService service = new GameService(game, auto);
        assertNotNull(id);
        //adding same game
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            service.createGame(auth1, "newGame");;
        });
        Assertions.assertEquals("Error: already taken", exception.getMessage());
    }
    @Test
    public void joinGameTestPass() throws Exception{
        user.createUser("username", "password", "email");
        String auth1=auto.createAuth("username");
        int id = game.createGame("newGame");

        GameService service = new GameService(game, auto);
        service.joinGame(id, TeamColor.WHITE, auth1);
        GameData expectedGame = new GameData(id, "username", null, "newGame", service.getGame().checkGame(id).game());
        Assertions.assertEquals(expectedGame, service.getGame().checkGame(id));
    }
    @Test
    public void joinGameTestFail() throws Exception {
        user.createUser("username", "password", "email");
        String auth1=auto.createAuth("username");
        int id = game.createGame("newGame");

        GameService service = new GameService(game, auto);
        service.joinGame(id, TeamColor.WHITE, auth1);
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            service.joinGame(id, TeamColor.WHITE, auth1);
        });
        Assertions.assertEquals("Error: already taken", exception.getMessage());
        //it should fail, make sure that it throws an exception for 
    }
    @Test
    public void listGamesTest() throws Exception {
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
    public void listGamesEmptyTest() throws Exception{
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
    public void registerNewUserTest() throws Exception {
        UserService service = new UserService(user, auto);
        //create new user 
        UserData user = service.getUser("username");
        Assertions.assertNull(user);

        service.createUser("username", "password", "email");
        service.createAuth("username");
        UserData wantedUser = new UserData("username", "password", "email");
        Assertions.assertEquals(service.getUser("username"), wantedUser);
    }
    @Test
    public void registerExistingUser() throws Exception {
        UserService service = new UserService(user, auto);
        //create new user 
        UserData user = service.getUser("username");
        Assertions.assertNull(user);

        service.createUser("username", "password", "email");
        service.createAuth("username");
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            service.createUser("username", "samepass", "otheremail");;
        });
        Assertions.assertEquals("Error: already taken", exception.getMessage());
    }
    @Test
    public void loginUserPass() throws Exception {
        UserService service = new UserService(user, auto);
        //create new user 
        UserData user = service.getUser("username");
        Assertions.assertNull(user);

        service.createUser("username", "password", "email");
        service.createAuth("username");
        Assertions.assertDoesNotThrow(() -> service.loginUser("username", "password"));
    }
    @Test
    public void loginUserFail() throws Exception {
        UserService service = new UserService(user, auto);
        //create new user 
        UserData user = service.getUser("username");
        Assertions.assertNull(user);

        service.createUser("username", "password", "email");
        service.createAuth("username");
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            service.loginUser("username", "wrongPassword");
        });
        Assertions.assertEquals("Error: unauthorized", exception.getMessage());
    }
    @Test
    public void logoutPass() throws Exception {
        UserService service = new UserService(user, auto);

        service.createUser("username", "password", "email");
        String data = service.createAuth("username");
        service.logoutUser(data);
        Assertions.assertNull(service.getAuth().getAuth(data));
    }
    @Test
    public void logoutFail() throws Exception {
        UserService service = new UserService(user, auto);

        service.createUser("username", "password", "email");
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            service.logoutUser("username");
        });
        Assertions.assertEquals("Error: unauthorized", exception.getMessage());
    }
    @Test
    public void joinGameTestPassSQL() throws Exception{
        users.createUser("username", "password", "email");
        String auth1=auth.createAuth("username");
        int id = games.createGame("newGame");

        SQLGameService service = new SQLGameService(games, auth);
        System.out.println("Before joinGame call");
        service.joinGame(id, TeamColor.WHITE, auth1);
        System.out.println("After joinGame call");
        GameData expectedGame = new GameData(id, "username", null, "newGame", service.getGame().checkGame(id).game());
        System.out.println("Before assertEquals call");
        GameData testing = service.getGame().checkGame(id);
        System.out.println("After getting");
        Assertions.assertEquals(expectedGame, testing);
    }
    @Test
    public void joinGameTestFailSQL() throws Exception {
        users.createUser("username", "password", "email");
        String auth1=auth.createAuth("username");
        int id = games.createGame("newGame");

        SQLGameService service = new SQLGameService(games, auth);
        service.joinGame(id, TeamColor.WHITE, auth1);
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            service.joinGame(id, TeamColor.WHITE, auth1);
        });
        Assertions.assertEquals("Error: already taken", exception.getMessage());
        //it should fail, make sure that it throws an exception for 
    }
    @Test
    public void listGamesTestSQL() throws Exception {
        users.createUser("username", "password", "email");
        String auth1=auth.createAuth("username");
        int id = games.createGame("newGame");
        int id2 = games.createGame("newSecondGame");
        SQLGameService service = new SQLGameService(games, auth);
        ArrayList<GameData> gamesWanted = new ArrayList<>();
        gamesWanted.add(games.checkGame(id));
        gamesWanted.add(games.checkGame(id2));
        Assertions.assertEquals(gamesWanted, service.listGames(auth1));
    }
    @Test 
    public void listGamesEmptyTestSQL() throws Exception{
        users.createUser("username", "password", "email");
        String auth1=auth.createAuth("username");
        games.createGame("newGame");
        games.createGame("newSecondGame");
        SQLGameService service = new SQLGameService(games, auth);
        ArrayList<GameData> gamesWanted = new ArrayList<>();
        games.clearGames();
        Assertions.assertEquals(gamesWanted, service.listGames(auth1));
    }
    @Test
    public void registerNewUserTestSQL() throws Exception {
        SQLUserService service = new SQLUserService(users, auth);
        //create new user 
        UserData user = service.getUser("username");
        Assertions.assertNull(user);

        service.createUser("username", "password", "email");
        service.createAuth("username");
        Assertions.assertEquals(service.getUser("username").getUser(), "username");
    }
    @Test
    public void registerExistingUserSQL() throws Exception {
        SQLUserService service = new SQLUserService(users, auth);
        //create new user 
        UserData user = service.getUser("username");
        Assertions.assertNull(user);

        service.createUser("username", "password", "email");
        service.createAuth("username");
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            service.createUser("username", "samepass", "otheremail");;
        });
        Assertions.assertEquals("Error: already taken", exception.getMessage());
    }
    @Test
    public void loginUserPassSQL() throws Exception {
        SQLUserService service = new SQLUserService(users, auth);
        //create new user 
        UserData user = service.getUser("username");
        Assertions.assertNull(user);

        service.createUser("username", "password", "email");
        service.createAuth("username");
        Assertions.assertDoesNotThrow(() -> service.loginUser("username", "password"));
    }
    @Test
    public void loginUserFailSQL() throws Exception {
        SQLUserService service = new SQLUserService(users, auth);
        //create new user 
        UserData user = service.getUser("username");
        Assertions.assertNull(user);

        service.createUser("username", "password", "email");
        service.createAuth("username");
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            service.loginUser("username", "wrongPassword");
        });
        Assertions.assertEquals("Error: unauthorized", exception.getMessage());
    }
    @Test
    public void logoutPassSQL() throws Exception {
        SQLUserService service = new SQLUserService(users, auth);

        service.createUser("username", "password", "email");
        String data = service.createAuth("username");
        service.logoutUser(data);
        Assertions.assertNull(service.getAuth().getAuth(data));
    }
    @Test
    public void logoutFailSQL() throws Exception {
        SQLUserService service = new SQLUserService(users, auth);

        service.createUser("username", "password", "email");
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            service.logoutUser("username");
        });
        Assertions.assertEquals("Error: unauthorized", exception.getMessage());
    }
}
