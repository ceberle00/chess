package websocket.messages;

import chess.model.GameData;

public class LoadGameMessage extends ServerMessage {
    private final String errorMessage; //instructions said could be anything, not sure if right
    private final GameData game;
    public LoadGameMessage(String error, GameData game) {
        super(ServerMessageType.LOAD_GAME);
        this.errorMessage = error;
        this.game = game;
    }
    public String getMessage() {
        return this.errorMessage;
    }
    public GameData getGame() {
        return game;
    }
}
