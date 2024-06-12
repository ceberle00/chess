package dataaccess;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.*;

import chess.ChessGame;
import chess.ChessGame.TeamColor;
import chess.model.AuthData;
import chess.model.GameData;
import chess.model.UserData;

    
public class DataTests {
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
    @Test
    public void createAuthPass() throws DataAccessException {
        String data =this.auth.createAuth("username");
        String b =this.auth.createAuth("username2");
        assertNotNull(this.auth.getAuth(data));
        assertNotNull(this.auth.getAuth(b));
    }
    @Test
    public void createAuthFail() throws DataAccessException {
        try {
            this.auth.createAuth(null);
        }
        catch (DataAccessException accessException) {
            Assertions.assertEquals("Error: unauthorized", accessException.getMessage());
        }
        
    }
    @Test 
    public void deleteAuthSession() throws DataAccessException {
        String data =this.auth.createAuth("username");
        String b =this.auth.createAuth("username2");
        this.auth.deleteSession(data);
        assertNull(this.auth.getAuth(data));
        assertNotNull(this.auth.getAuth(b));
    }
    @Test 
    public void deleteAuthSessionFail() throws DataAccessException {
        String data =this.auth.createAuth("username");
        String b =this.auth.createAuth("username");
        this.auth.deleteSession("username");
        assertNotNull(this.auth.getAuth(data));
        assertNotNull(this.auth.getAuth(b));
    }
    @Test
    public void getUserSQL()throws DataAccessException {
        this.users.createUser("username", "password", "email");
        assertEquals(this.users.getUser("username").getEmail(), "email");
        assertEquals(this.users.getUser("username").getUser(), "username");
    }
    @Test
    public void getUserSQLFail() throws DataAccessException {
        this.users.createUser("username", "password", "email");
        this.users.clearUsers();
        Assertions.assertNull(this.auth.getAuth("username"));
        //Assertions.assertEquals("Error: unauthorized", exception.getMessage());
    }
    @Test
    public void getUserPass() throws DataAccessException {
        this.users.createUser("username", "password", "email");
        UserData data =this.users.getUserPass("username", "password");
        UserData wantedData = new UserData("username", "password", "email");
        assertEquals(data.getUser(), wantedData.getUser());
        assertNotEquals(data.getPass(), wantedData.getPass());
    }
    @Test
    public void getUserPassFail() throws DataAccessException {
        this.users.createUser("username", "password", "email");
        UserData data =this.users.getUserPass("username", "wrong");
        //UserData wantedData = new UserData("username", "password", "email");
        assertNull(data);
    }
    @Test
    public void createUserPass() throws DataAccessException {
        this.users.createUser("username", "password", "email");
        this.users.createUser("username2", "password", "email");
        assertNotNull(this.users.getUser("username"));
        assertNotNull(this.users.getUser("username2"));
    }
    @Test
    public void createUserFail() throws DataAccessException {
        try {
            this.users.createUser("username", "password", "email");
            this.users.createUser("username", "password", "email");
        }
        catch (DataAccessException accessException) {
            Assertions.assertEquals("Error: already taken", accessException.getMessage());
        }
        
    }
    @Test 
    public void creatGamePass() throws DataAccessException
    {
        int id = this.games.createGame("gameName");
        int id2 =this.games.createGame("game2");
        assertEquals(this.games.getGameName("game2").gameID(), id2);
        assertEquals(this.games.getGameName("gameName").gameID(), id);
    }
    @Test 
    public void creatGameFail() throws DataAccessException
    {
        try {
            this.games.createGame("gameName");
            this.games.createGame("gameName");
        }catch (DataAccessException e) {
            Assertions.assertEquals("Error: already taken", e.getMessage());
        }
        
    }
    @Test 
    public void getGamePass() throws DataAccessException
    {
        int id = this.games.createGame("gameName");
        int id2 =this.games.createGame("game2");
        assertEquals(this.games.getGameName("game2").gameID(), id2);
        assertEquals(this.games.getGameName("gameName").gameID(), id);
    }
    @Test 
    public void getGameFail() throws DataAccessException
    {
        try {
            this.games.getGameName(null);
        }catch (DataAccessException e) {
            Assertions.assertEquals("error: not allowed", e.getMessage());
        }
    }
    @Test 
    public void getGamesPass() throws DataAccessException{
        int id = this.games.createGame("gameName");
        GameData game1 = new GameData(id, null, null, "gameName", new ChessGame());
        int id2 =this.games.createGame("game2");
        GameData game2 = new GameData(id2, null, null, "game2", new ChessGame());
        Collection<GameData> games = new ArrayList<>();
        games.add(game1);
        games.add(game2);
        assertEquals(games, this.games.getGames());
    }
    @Test 
    public void getGamesFail() throws DataAccessException{
        int id = this.games.createGame("gameName");
        GameData game1 = new GameData(id, null, null, "gameName", new ChessGame());
        int id2 =this.games.createGame("game2");
        GameData game2 = new GameData(id2, null, null, "game2", new ChessGame());
        Collection<GameData> games = new ArrayList<>();
        this.games.clearGames();
        games.add(game1);
        games.add(game2);
        assertNotEquals(games, this.games.getGames());
    }
    @Test 
    public void checkGamesPass() throws DataAccessException{
        int id = this.games.createGame("gameName");
        GameData game1 = new GameData(id, null, null, "gameName", new ChessGame());
        int id2 =this.games.createGame("game2");
        GameData game2 = new GameData(id2, null, null, "game2", new ChessGame());
        assertEquals(game2, this.games.checkGame(id2));
        assertEquals(game1, this.games.checkGame(id));
    }
    @Test 
    public void checkGamesFail() throws DataAccessException{
        int id = this.games.createGame("gameName");
        new GameData(id, null, null, "gameName", new ChessGame());
        int id2 =this.games.createGame("game2");
        GameData game2 = new GameData(id2, null, null, "game2", new ChessGame());
        this.games.clearGames();
        assertNotEquals(game2, this.games.checkGame(id2));
        assertNull(this.games.checkGame(id2));
    }
    @Test 
    public void joinGamePass() throws DataAccessException{
        int id = this.games.createGame("gameName");
        new GameData(id, null, null, "gameName", new ChessGame());
        AuthData auth = new AuthData("token", "username");
        assertDoesNotThrow(() -> this.games.joinGame(id, auth, TeamColor.BLACK));   
    }
    @Test 
    public void joinGameFail() throws DataAccessException{
        int id = this.games.createGame("gameName");
        new GameData(id, null, null, "gameName", new ChessGame());
        AuthData auth = new AuthData("token", "username");
        AuthData auth2 = new AuthData("token2", "username2");
        this.games.joinGame(id, auth, TeamColor.BLACK);
        assertThrows(DataAccessException.class, () -> this.games.joinGame(id, auth2, TeamColor.BLACK));
        
        
    }
}
