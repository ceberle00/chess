package service.Results;

public class LoginResult {
    private String message;
    private String username;
    private String auth;
    
    public LoginResult(String user, String a) {
        this.auth=a;
        this.username= user;
    }
    public LoginResult(String mes) {
        this.message = mes;
    }
}
