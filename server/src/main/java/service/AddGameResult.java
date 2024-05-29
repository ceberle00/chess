package service;

public class AddGameResult {
    private Integer gameID;
    private String message;
    public AddGameResult(Integer v) {
        this.gameID = v;
    }
    public AddGameResult(String m) {
        this.message = m;
    }

}
