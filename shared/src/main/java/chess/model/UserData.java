package chess.model;

//add to this later, not sure what to make it
public record UserData(String username, String password, String email) 
{
    public String getUser() {
        return username;
    }
    public String getPass() {
        return password;
    }
    public String getEmail() {
        return email;
    }
}
