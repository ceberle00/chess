package websocket.commands;

import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand
{
    private final Integer gameID;
    private final ChessMove move;

    public MakeMoveCommand(String authToken, int gameID, ChessMove move) 
    {
        super(authToken, gameID);
        commandType = UserGameCommand.CommandType.MAKE_MOVE;
        this.gameID = gameID;
        this.move = move;
    }

    public Integer getGameID() {
        return gameID;
    }

    public ChessMove getMove() {
        return move;
    }
}
