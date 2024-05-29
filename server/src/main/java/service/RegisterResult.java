package service;

public class RegisterResult 
{

    private String authToken;
    private String username;
    private String message;
    public RegisterResult(String user, String auth) {
        this.authToken = auth;
        this.username = user;
    }
    public RegisterResult(String message) {
        this.message = message;
    }
    public String getAuthToken() {
        return this.authToken;
    }


    public String getUsername() {
        return this.username;
    }
    
}
