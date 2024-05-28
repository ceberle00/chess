package service;

import chess.model.UserData;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;

public class UserService {
    private UserDAO user;
    private AuthDAO auth;

    public UserService(UserDAO u, AuthDAO a) {
        this.auth = a;
        this.user = u;
    }
    public UserData getUser(String userName) 
    {
        return this.user.getUser(userName);
    }
    public void createUser(String username, String password, String email) throws DataAccessException
    {
        this.user.createUser(username, password, email);
    }
    public void createAuth(String username) {
        this.auth.createAuth(username);
    }
}
