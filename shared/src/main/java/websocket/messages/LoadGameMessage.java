package websocket.messages;

import chess.ChessGame;
import chess.model.GameData;

public class LoadGameMessage extends ServerMessage {
    private final String ErrorMessage; //instructions said could be anything, not sure if right
    private final GameData game;
    public LoadGameMessage(String error, GameData game) {
        super(ServerMessageType.LOAD_GAME);
        this.ErrorMessage = error;
        this.game = game;
    }
    public String getMessage() {
        return this.ErrorMessage;
    }
    public GameData getGame() {
        return game;
    }
}
