package chess.model;

public record AuthData(String authToken, String username) {
    String getAuth() {
        return authToken;
    }
    String getUser(){
        return username;
    }
}
