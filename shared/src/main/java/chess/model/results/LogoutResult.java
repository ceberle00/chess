package chess.model.results;

public class LogoutResult {
    private String message;
    public LogoutResult(String m) {
        this.message = m;
    }
    public String getMessage() {
        return this.message;
    }
}
