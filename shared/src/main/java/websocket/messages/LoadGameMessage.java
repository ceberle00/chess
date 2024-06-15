package websocket.messages;

import chess.ChessGame;

public class LoadGameMessage extends ServerMessage {
    private final String ErrorMessage; //instructions said could be anything, not sure if right
    private final ChessGame game;
    public LoadGameMessage(String error, ChessGame game) {
        super(ServerMessageType.LOAD_GAME);
        this.ErrorMessage = error;
        this.game = game;
    }
    public String getMessage() {
        return this.ErrorMessage;
    }
}
