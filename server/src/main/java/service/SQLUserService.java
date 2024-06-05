package service;

import chess.model.AuthData;
import chess.model.UserData;
import dataaccess.*;

public class SQLUserService implements UserServiceMemory{
    private SQLUserDAO user;
    private SQLAuthDAO auth;

    public SQLUserService(SQLUserDAO u, SQLAuthDAO a) {
        this.auth = a;
        this.user = u;
    }
    @Override
    public UserData getUser(String userName) throws DataAccessException
    {
        return this.user.getUser(userName);
    }
    @Override
    public void createUser(String username, String password, String email) throws Exception
    {
        this.user.createUser(username, password, email);
    }
    @Override
    public String createAuth(String username) throws DataAccessException {
        return this.auth.createAuth(username);
    }
    @Override
    public String loginUser(String username, String password) throws Exception
    {
        UserData user = getUser(username); 
        if (user == null) {throw new Exception("Error: unauthorized");}
        UserData actualUser = this.user.getUserPass(username, password);
        if (actualUser == null) {throw new Exception("Error: unauthorized");}
        return createAuth(username);
    }
    @Override
    public void logoutUser(String authToken) throws Exception
    {
        AuthData data = auth.getAuth(authToken);
        if (data == null) {
            throw new Exception("Error: unauthorized");
        }
        this.auth.deleteSession(data.authToken());
    }
    public SQLAuthDAO getAuth() {
        return this.auth;
    }
}
