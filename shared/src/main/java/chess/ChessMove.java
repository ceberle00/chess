package chess;
import java.util.Objects;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
//add something for castling? Maybe?
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
    private ChessPosition castleStart; //may need two? 
    private ChessPosition castleEnd;
    private boolean isCastle = false;
    
    public ChessMove(ChessPosition startPosition, ChessPosition endPosition, ChessPiece.PieceType promotionPiece) {
        this.start = startPosition;
        this.end = endPosition;     
        this.type = promotionPiece;  
    }

    public void setCastle(ChessPosition pos, ChessPosition end) {
        this.castleStart = pos;
        this.castleEnd = end;
        this.isCastle = true;
    }
    public ChessPosition getCastleStart() {
        return this.castleStart;
    }
    public ChessPosition getCastleEnd() {
        return this.castleEnd;
    }
    public boolean getIsCastle() {
        return this.isCastle;
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
