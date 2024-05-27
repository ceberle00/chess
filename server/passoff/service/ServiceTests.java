import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import service.SystemService;

public class ServiceTests {

    public void clearTest() throws Exception
    {
        UserDAO user = new UserDAO();
        AuthDAO auto = new AuthDAO();
        GameDAO game = new GameDAO();

        user.createUser("username", "password", "email");
        user.createUser("username2", "password2", "newEmail");

        String auth1=auto.createAuth("username");
        String auth2=auto.createAuth("username2");

        int id = game.createGame("newGame");

        SystemService service = new SystemService(auto, game, user);
        service.clear();
        //now check
        Assertion.assertNull(user.getUser("username"));
        Assertion.assertNull(user.getUser("username2"));
        Assertion.assertNull(auto.getAuth(auth1));
        Assertion.assertNull(auto.getAuth(auth2));
        Assertion.assertNull(game.getGameName("newGame"));

    }
}
