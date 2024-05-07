package chess;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ChessBoard)) {
            return false;
        }
        ChessBoard chessBoard = (ChessBoard) o;
        return Objects.equals(board, chessBoard.board);
    }

    /*@Override
    public int hashCode() {
        return super.hashCode()(board);
    }*/

    @Override
    public String toString() {
        return "{" +
            " board='" + getBoard() + "'" +
            "}";
    }

    private ChessPiece [][] board = new ChessPiece[8][8];
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
        board[position.getRow()][position.getColumn()] = piece;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    //later fix to have errors if position isn't valid
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow()][position.getColumn()];
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    //create a bunch of pieces and stuff
    public void resetBoard() 
    {
        throw new RuntimeException("Not implemented");
    }
}