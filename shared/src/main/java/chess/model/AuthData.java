package chess.model;

public record AuthData(String authToken, String username) {
    public String getAuth() {
        return authToken;
    }
    public String getUser(){
        return username;
    }
}
