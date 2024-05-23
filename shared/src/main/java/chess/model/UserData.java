package chess.model;

//add to this later, not sure what to make it
public record UserData(String username, String password, String email) 
{
    String getUser() {
        return username;
    }
}