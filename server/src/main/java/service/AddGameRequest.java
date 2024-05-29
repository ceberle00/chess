package service;

public class AddGameRequest {
    private String gameName;

    public AddGameRequest(String game) {
        this.gameName = game;
    }
    public String getName() {
        return this.gameName;
    }
}
