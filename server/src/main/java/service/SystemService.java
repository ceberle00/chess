package service;

import dataaccess.*;


public class SystemService 
{
    private AuthDAO auth = new AuthDAO();
    private GameDAO game = new GameDAO();
    private UserDAO user = new UserDAO();
    private SQLAuthDAO auto = new SQLAuthDAO();
    private SQLGameDAO games = new SQLGameDAO();
    private SQLUserDAO users = new SQLUserDAO();
    
    public AuthDAO getAuth() {
        return this.auth;
    }


    public GameDAO getGame() {
        return this.game;
    }


    public UserDAO getUser() {
        return this.user;
    }
    public SystemService(AuthDAO auth, GameDAO game, UserDAO user) {
        this.auth = auth;
        this.game = game;
        this.user = user;
    }
    public SystemService(SQLAuthDAO auth, SQLGameDAO game, SQLUserDAO user) {
        this.auto = auth;
        this.games = game;
        this.users = user;
    }
    public void clear() throws DataAccessException
    {
        //issue these are null, already clear
        this.auth.clearAuth();
        this.game.clearGames();
        this.user.clearUsers();
        this.auto.clearAuth();
        this.games.clearGames();
        this.users.clearUsers();
        
    }
}
