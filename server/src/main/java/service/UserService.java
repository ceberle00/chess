package service;

import chess.model.AuthData;
import chess.model.UserData;
import dataaccess.AuthDAO;
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
    public void createUser(String username, String password, String email) throws Exception
    {
        this.user.createUser(username, password, email);
    }
    public String createAuth(String username) {
        return this.auth.createAuth(username);
    }
    public String loginUser(String username, String password) throws Exception
    {
        UserData user = getUser(username); 
        if (user == null) {throw new Exception("Error: unauthorized");}
        UserData actualUser = this.user.getUserPass(username, password);
        if (actualUser == null) {throw new Exception("Error: unauthorized");}
        return createAuth(username);
    }
    public void logoutUser(String authToken) throws Exception
    {
        AuthData data = auth.getAuth(authToken);
        if (data == null) {
            throw new Exception("Error: unauthorized");
        }
        this.auth.deleteSession(data.authToken());
    }
    public AuthDAO getAuth() {
        return this.auth;
    }
}
