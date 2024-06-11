package ui;

import chess.ChessBoard;

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
