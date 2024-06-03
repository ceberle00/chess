package service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;

public class SystemService 
{
    private AuthDAO auth;
    private GameDAO game;
    private UserDAO user;
    
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
    public void clear() 
    {
        //issue these are null, already clear
        this.auth.clearAuth();
        this.game.clearGames();
        this.user.clearUsers();
        
    }
}
