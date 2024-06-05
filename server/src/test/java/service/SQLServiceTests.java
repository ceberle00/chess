package service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import chess.ChessGame.TeamColor;
import chess.model.AuthData;
import chess.model.GameData;
import chess.model.UserData;
import dataaccess.*;

public class SQLServiceTests {
    private SQLAuthDAO auto = new SQLAuthDAO();
    private SQLGameDAO game = new SQLGameDAO();
    private SQLUserDAO user = new SQLUserDAO();

    @BeforeEach
    public void setUp() throws DataAccessException 
    {
        this.user.clearUsers();
        this.auto.clearAuth();
        this.game.clearGames();
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
        //Assertions.assertNull(game.getGameName("newGame"));

    }
    @Test 
    public void authCheckPass() throws Exception {
        user.createUser("username", "password", "email");
        String auth1=auto.createAuth("username");
        game.createGame("newGame");
        SQLGameService gs = new SQLGameService(game, auto);
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
        SQLGameService service = new SQLGameService(game, auto);
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

        SQLGameService service = new SQLGameService(game, auto);
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
    public void joinGameTestFail() throws Exception {
        user.createUser("username", "password", "email");
        String auth1=auto.createAuth("username");
        int id = game.createGame("newGame");

        SQLGameService service = new SQLGameService(game, auto);
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
        SQLGameService service = new SQLGameService(game, auto);
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
        SQLGameService service = new SQLGameService(game, auto);
        ArrayList<GameData> gamesWanted = new ArrayList<>();
        game.clearGames();
        Assertions.assertEquals(gamesWanted, service.listGames(auth1));
    }
    @Test
    public void registerNewUserTest() throws Exception {
        SQLUserService service = new SQLUserService(user, auto);
        //create new user 
        UserData user = service.getUser("username");
        Assertions.assertNull(user);

        service.createUser("username", "password", "email");
        service.createAuth("username");
        Assertions.assertEquals(service.getUser("username").getUser(), "username");
    }
    @Test
    public void registerExistingUser() throws Exception {
        SQLUserService service = new SQLUserService(user, auto);
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
        SQLUserService service = new SQLUserService(user, auto);
        //create new user 
        UserData user = service.getUser("username");
        Assertions.assertNull(user);

        service.createUser("username", "password", "email");
        service.createAuth("username");
        Assertions.assertDoesNotThrow(() -> service.loginUser("username", "password"));
    }
    @Test
    public void loginUserFail() throws Exception {
        SQLUserService service = new SQLUserService(user, auto);
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
        SQLUserService service = new SQLUserService(user, auto);

        service.createUser("username", "password", "email");
        String data = service.createAuth("username");
        service.logoutUser(data);
        Assertions.assertNull(service.getAuth().getAuth(data));
    }
    @Test
    public void logoutFail() throws Exception {
        SQLUserService service = new SQLUserService(user, auto);

        service.createUser("username", "password", "email");
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            service.logoutUser("username");
        });
        Assertions.assertEquals("Error: unauthorized", exception.getMessage());
    }
}