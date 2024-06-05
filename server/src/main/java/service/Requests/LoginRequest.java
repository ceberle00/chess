package service.requests;

public class LoginRequest {
    private String username;
    private String password;
    
    public LoginRequest(String user, String pass) {
        this.password = pass;
        this.username = user;
    }


    
    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

   
}
