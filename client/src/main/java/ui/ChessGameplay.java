package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;

public class ChessGameplay {
    //make a chessboard? teehee
    private ChessBoard board = new ChessBoard();
    public ChessGameplay() {
    }
    private void createBoard() 
    {
        this.board.resetBoard();
    }
}
