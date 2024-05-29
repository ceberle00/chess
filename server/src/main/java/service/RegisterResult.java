package service;

public class RegisterResult 
{

    private String authToken;
    private String username;

    public RegisterResult(String user, String auth) {
        this.authToken = auth;
        this.username = user;
    }
}
