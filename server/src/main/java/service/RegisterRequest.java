package service;

public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    public RegisterRequest(String u, String pass, String mail) {
        this.email=mail;
        this.password=pass;
        this.username=u;
    }

    public RegisterRequest() {
    }
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
