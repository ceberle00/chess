package chess.model.requests;

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
    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

}
