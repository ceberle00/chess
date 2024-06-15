package chess.model.results;

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
    public String getUser() {
        return this.username;
    }
    public String getAuth() {
        return this.auth;
    }
    public String getMessage() {
        return this.message;
    }
}
