package chess;

import java.util.Collection;

import chess.ChessGame.TeamColor;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ChessPiece)) {
            return false;
        }
        ChessPiece chessPiece = (ChessPiece) o;
        return Objects.equals(pieceType, chessPiece.pieceType) && Objects.equals(color, chessPiece.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceType, color);
    }

    @Override
    public String toString() {
        return "{" +
            " pieceType='" + getPieceType() + "'" +
            ", color='" + getTeamColor() + "'" +
            "}";
    }

    private PieceType pieceType;
    private TeamColor color;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceType = type;
        this.color = pieceColor;
    }
    
    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return this.color;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return this.pieceType;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        if (board.getPiece(myPosition) == null) {
            throw new RuntimeException("No piece in this position");
        }
        int column = myPosition.getColumn();
        int rows = myPosition.getRow();
        if (this.pieceType == PieceType.BISHOP) {
            //

        }
        throw new RuntimeException("Not implemented");
    }
}
