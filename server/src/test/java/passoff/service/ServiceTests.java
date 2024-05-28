package passoff.service;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import service.GameService;
import service.SystemService;
import org.junit.jupiter.api.*;

import chess.ChessGame.TeamColor;
import chess.model.GameData;

public class ServiceTests {

    private UserDAO user = new UserDAO();
    private AuthDAO auto = new AuthDAO();
    private GameDAO game = new GameDAO();
    @Test
    public void clearTest() throws Exception
    {
        user.createUser("username", "password", "email");
        user.createUser("username2", "password2", "newEmail");

        String auth1=auto.createAuth("username");
        String auth2=auto.createAuth("username2");

        int id = game.createGame("newGame");

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
    public void joinGameTestYes() throws Exception
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
    public void joinTestFail() throws Exception 
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
}
