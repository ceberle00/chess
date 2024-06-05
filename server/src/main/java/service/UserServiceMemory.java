package service;

import chess.model.UserData;
import dataaccess.DataAccessException;

public interface UserServiceMemory {
    public UserData getUser(String userName) throws DataAccessException;
    public void createUser(String username, String password, String email) throws Exception;
    public String createAuth(String username) throws DataAccessException;
    public String loginUser(String username, String password) throws Exception;
    public void logoutUser(String authToken) throws Exception;
}
