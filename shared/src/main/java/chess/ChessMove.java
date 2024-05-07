package chess;
import java.util.Objects;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove {

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ChessMove)) {
            return false;
        }
        ChessMove chessMove = (ChessMove) o;
        return Objects.equals(start, chessMove.start) && Objects.equals(end, chessMove.end) && Objects.equals(type, chessMove.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, type);
    }

    @Override
    public String toString() {
        return "{" +
            " start='" + getStartPosition() + "'" +
            ", end='" + getEndPosition() + "'" +
            ", type='" + getPromotionPiece() + "'" +
            "}";
    }

    private ChessPosition start = new ChessPosition(0, 0);
    private ChessPosition end = new ChessPosition(0, 0);
    private ChessPiece.PieceType type;
    public ChessMove(ChessPosition startPosition, ChessPosition endPosition, ChessPiece.PieceType promotionPiece) {
        this.start = startPosition;
        this.end = endPosition;     
        this.type = promotionPiece;  
    }

    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() {
        return this.start;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() {
        return this.end;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPiece() {
        return this.type;
        //throw new RuntimeException("Not implemented");
    }
}
