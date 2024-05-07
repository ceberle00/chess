package chess;
//import java.util.Objects;

import chess.ChessGame.TeamColor;
import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    public ChessBoard(ChessPiece[][] board) {
        this.board = board;
    }

    public ChessPiece[][] getBoard() {
        return this.board;
    }

    public void setBoard(ChessPiece[][] board) {
        this.board = board;
    }

    public ChessBoard board(ChessPiece[][] board) {
        setBoard(board);
        return this;
    }


    private ChessPiece [][] board = new ChessPiece[8][8];
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ChessBoard that = (ChessBoard) object;
        return Arrays.deepEquals(board, that.board);
    
    }

    /*@Override
    public int hashCode() {
        return super.hashCode()(board);
    }*/

    @Override
    public String toString() {
        return "{" +
            " board='" + board[6][0].toString() + "'" + board[6][1].toString() +
            board[6][2].toString() + board[6][3].toString() + board[6][4].toString() + board[6][5].toString() +
            board[6][6].toString() + board[6][7].toString() +
            "}";
    }

    
    public ChessBoard() {
        
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        if (position.getRow() < 1 || position.getRow() > 8) {
            throw new RuntimeException("Invalid position");
        }
        if (position.getColumn() < 1 || position.getColumn() > 8) {
            throw new RuntimeException("Invalid position");
        }
        board[position.getRow()-1][position.getColumn()-1] = piece;
        //throw new RuntimeException();
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    //later fix to have errors if position isn't valid
    public ChessPiece getPiece(ChessPosition position) 
    {
        
        return board[position.getRow()-1][position.getColumn()-1]; //added minus one 
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    //create a bunch of pieces and stuff
    public void resetBoard() 
    {
        for (int i = 0; i < 8;i++) 
        {
            //handle pawns, should add all of them
            ChessPiece blkPiece = new ChessPiece(TeamColor.BLACK, ChessPiece.PieceType.PAWN);
            ChessPiece whitPiece = new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.PAWN);
            this.board[1][i] = whitPiece; //not sure about the numbers
            this.board[6][i] = blkPiece;
        }
        //kings
        ChessPiece whtKing = new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.KING);
        ChessPiece blkKing = new ChessPiece(TeamColor.BLACK, ChessPiece.PieceType.KING);
        this.board[0][4] = whtKing;
        this.board[7][4] = blkKing;
        //queens
        ChessPiece whtQ = new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        ChessPiece blkQ = new ChessPiece(TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        this.board[0][3] = whtQ;
        this.board[7][3] = blkQ;
        //knights
        ChessPiece knightWht = new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        ChessPiece knightWht2 = new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        this.board[0][1] = knightWht;
        this.board[0][6] = knightWht2;

        ChessPiece knightBlk = new ChessPiece(TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        ChessPiece knightBlk2 = new ChessPiece(TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        this.board[7][1] = knightBlk;
        this.board[7][6] = knightBlk2;
        //rooks
        ChessPiece rookWht = new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        ChessPiece rookWht2 = new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        this.board[0][0] = rookWht;
        this.board[0][7] = rookWht2;

        ChessPiece rookBlk = new ChessPiece(TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        ChessPiece rookBlk2 = new ChessPiece(TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        this.board[7][0] = rookBlk;
        this.board[7][7] = rookBlk2;

        //bishops
        ChessPiece bWht = new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        ChessPiece bWht2 = new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        this.board[0][2] = bWht;
        this.board[0][5] = bWht2;

        ChessPiece bBlk = new ChessPiece(TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        ChessPiece bBlk2 = new ChessPiece(TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        this.board[7][2] = bBlk;
        this.board[7][5] = bBlk2;
        //throw new RuntimeException("Not implemented");
    }
}
