package client;

import static org.junit.jupiter.api.Assertions.*;

import org.glassfish.grizzly.utils.Exceptions;
import org.junit.jupiter.api.*;

import chess.model.AuthData;
import chess.model.UserData;
import chess.model.requests.LoginRequest;
import dataaccess.DataAccessException;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLGameDAO;
import dataaccess.SQLUserDAO;
import dataaccess.UserDAO;
import server.Server;
import serverFacade.ServerFacade;
import service.GameService;
import service.SQLGameService;
import service.SQLUserService;
import service.SystemService;


public class ServerFacadeTests {

    private static Server server;

    private static SQLAuthDAO auth = new SQLAuthDAO();
    private static SQLUserDAO user = new SQLUserDAO();
    private static SQLGameDAO games = new SQLGameDAO();

    private static SystemService systemService = new SystemService(auth, games, user);
    private static SQLGameService gameService = new SQLGameService(games, auth);
    private static SQLUserService userService = new SQLUserService(user, auth);

    private UserData userData = new UserData("username", "password", "email");
    private UserData secondUser = new UserData("user", "pass", "mail");

    private static ServerFacade facade;

    @BeforeAll
    public static void init() throws DataAccessException{
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade(port);
        systemService.clear();

    }

    @AfterAll
    static void stopServer() {
        server.stop();
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
        AuthData returnedData = facade.register(userData.username(), userData.password(), userData.email());
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            facade.login("username", "wrongPass");;
        });
        assertEquals(exception.getMessage(), "Error reading response");
    }
}
