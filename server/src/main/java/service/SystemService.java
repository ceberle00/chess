package service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;

public class SystemService 
{
    private AuthDAO auth;
    private GameDAO game;
    private UserDAO user;
    public SystemService(AuthDAO auth, GameDAO game, UserDAO user) {
        this.auth = auth;
        this.game = game;
        this.user = user;
    }
    public void clear() 
    {
        this.auth.clearAuth();
        this.game.clearGames();
        this.user.clearUsers();
    }
}
