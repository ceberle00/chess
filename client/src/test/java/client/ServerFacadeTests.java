package client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.util.Collection;
import java.util.ArrayList;
import chess.ChessGame;
import chess.model.AuthData;
import chess.model.GameData;
import chess.model.UserData;
import dataaccess.DataAccessException;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLGameDAO;
import dataaccess.SQLUserDAO;
import server.Server;
import serverFacade.ServerFacade;
import service.SystemService;



public class ServerFacadeTests {

    private static Server server;

    private static SQLAuthDAO auth = new SQLAuthDAO();
    private static SQLUserDAO user = new SQLUserDAO();
    private static SQLGameDAO games = new SQLGameDAO();

    private static SystemService systemService = new SystemService(auth, games, user);

    private UserData userData = new UserData("username", "password", "email");
    private UserData secondUser = new UserData("user", "pass", "mail");

    private static ServerFacade facade;

    //think this'll just be the services from the diagram
    @BeforeAll
    public static void init() throws DataAccessException{
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade(port);
        systemService.clear();

    }
    @BeforeEach
    public void setUp() throws DataAccessException {
        auth = new SQLAuthDAO();
        user = new SQLUserDAO();
        games = new SQLGameDAO();
        systemService = new SystemService(auth, games, user);
        systemService.clear(); // Clear state before each test
    }
    @AfterAll
    static void stopServer() {
        server.stop();
    }
    @Test 
    public void clearTest() throws Exception
    {
        AuthData returnedData = facade.register(userData.username(), userData.password(), userData.email());
        AuthData help = facade.register(secondUser.username(), secondUser.password(), secondUser.email());
        systemService.clear();
        assertNull(user.getUser("username"));
        assertNull(user.getUser("user"));
    }
    @Test
    public void registerUserTestPass() throws Exception {
        AuthData returnedData = facade.register(userData.username(), userData.password(), userData.email());
        assertEquals(returnedData.username(), userData.username());
        Assertions.assertNotNull(returnedData.authToken());
        Assertions.assertEquals(userData.email(), user.getUser(returnedData.username()).email());
    }
    @Test
    public void registerUserTestFail() throws Exception {
        facade.register(userData.username(), userData.password(), userData.email());
        //AuthData result = facade.register(userData.username(), userData.password(), userData.email());
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            facade.register(userData.username(), userData.password(), userData.email());;
        });
        assertEquals(exception.getMessage(), "Error reading response");
    }
    @Test
    public void loginPassTest() throws Exception {
        AuthData returnedData = facade.register(userData.username(), userData.password(), userData.email());
        //LoginRequest loginRequest = new LoginRequest("username", "password");
        AuthData loginResult = facade.login("username", "password");
        assertEquals(returnedData.username(), loginResult.getUser());
        assertEquals(loginResult.getUser(), userData.username());
        assertNotNull(loginResult);
    }
    @Test
    public void loginFailTest() throws Exception {
        facade.register(userData.username(), userData.password(), userData.email());
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            facade.login("username", "wrongPass");;
        });
        assertEquals(exception.getMessage(), "Error reading response");
    }
    @Test
    public void logoutPassTest() throws Exception 
    {
        facade.register("username", "password", "email");
        AuthData data = facade.login("username", "password");
        assertNotNull(data);
        facade.logout(data.authToken());
        assertNull(auth.getAuth(data.authToken()));
    }
    @Test
    public void logoutFailTest() throws Exception 
    {
        facade.register("username", "password", "email");
        AuthData data = facade.login("username", "password");
        assertNotNull(data);
       Assertions.assertThrows(Exception.class, () -> {
            facade.logout("not authToken");;
        });
    }
    @Test 
    public void createGamePass() throws Exception{
        AuthData data = facade.register("username", "password", "email");
        facade.login("username", "password");
        Integer gameData = facade.createGame(data.getAuth(), "name");
        Integer id2 = facade.createGame(data.getAuth(), "cool name");
        GameData game1 = new GameData(gameData, null, null, "name", new ChessGame());
        GameData game2 = new GameData(id2, null, null, "cool name", new ChessGame());
        assertEquals(games.checkGame(gameData), game1);
        assertEquals(games.checkGame(id2), game2);
    }
    @Test 
    public void createGameFail() throws Exception{
        AuthData data = facade.register("username", "password", "email");
        facade.login("username", "password");
        Integer gameData = facade.createGame(data.getAuth(), "name");
        assertNotNull(gameData);
        Assertions.assertThrows(Exception.class, () -> {
            facade.createGame(data.getAuth(), "name");;
        });
    }
    @Test 
    public void listGamesPass() throws Exception {
        AuthData data = facade.register("username", "password", "email");
        facade.login("username", "password");
        Integer gameData = facade.createGame(data.getAuth(), "name");
        Integer id2 = facade.createGame(data.getAuth(), "cool name");
        GameData game1 = new GameData(gameData, null, null, "name", new ChessGame());
        GameData game2 = new GameData(id2, null, null, "cool name", new ChessGame());
        Collection<GameData> gamesWanted = new ArrayList<>();
        gamesWanted.add(game1);
        gamesWanted.add(game2);
        Collection<GameData> actualGames = facade.listGames(data.authToken());
        Assertions.assertEquals(gamesWanted, actualGames);
    }
    @Test 
    public void listGamesFail() throws Exception {
        AuthData data = facade.register("username", "password", "email");
        facade.login("username", "password");
        Integer gameData = facade.createGame(data.getAuth(), "name");
        Integer id2 = facade.createGame(data.getAuth(), "cool name");
        GameData game1 = new GameData(gameData, null, null, "name", new ChessGame());
        GameData game2 = new GameData(id2, null, null, "cool name", new ChessGame());
        Collection<GameData> gamesWanted = new ArrayList<>();
        gamesWanted.add(game1);
        gamesWanted.add(game2);
        games.clearGames();
        Collection<GameData> actualGames = facade.listGames(data.authToken());
        Assertions.assertNotEquals(gamesWanted, actualGames);
    }
    @Test 
    public void joinGamePass() throws Exception{
        AuthData data = facade.register("username", "password", "email");
        facade.login("username", "password");
        Integer gameData = facade.createGame(data.getAuth(), "name");
        GameData game1 = new GameData(gameData, null, null, "name", new ChessGame());
        assertEquals(games.checkGame(gameData), game1);
        facade.joinGame("Black", gameData, data.getAuth());
        assertEquals(games.checkGame(gameData).blackUsername(), data.getUser());
    }
    @Test 
    public void joinGameFail() throws Exception{
        AuthData data = facade.register("username", "password", "email");
        AuthData second = facade.register("user", "pass", "mail");
        facade.login("username", "password");
        facade.login("user", "pass");
        Integer gameData = facade.createGame(data.getAuth(), "name");
        GameData game1 = new GameData(gameData, null, null, "name", new ChessGame());
        assertEquals(games.checkGame(gameData), game1);
        facade.joinGame("Black", gameData, data.getAuth());
        assertEquals(games.checkGame(gameData).blackUsername(), data.getUser());
        Assertions.assertThrows(Exception.class, () -> {
            facade.joinGame("Black", gameData, second.getAuth());;
        });
    }
}
