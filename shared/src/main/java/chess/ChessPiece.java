package chess;

import java.util.Collection;
import java.util.ArrayList;

import chess.ChessGame.TeamColor;
import chess.chesspieces.BishopMoves;
import chess.chesspieces.KingMoves;
import chess.chesspieces.KnightMoves;
import chess.chesspieces.PawnMoves;
import chess.chesspieces.QueenMoves;
import chess.chesspieces.RookMoves;

import java.util.Objects;

import com.google.gson.annotations.SerializedName;

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
    public String toString() 
    {
        return "{" +
            " pieceType='" + getPieceType() + "'" +
            ", color='" + getTeamColor() + "'" +
            "}";
    }


    private ChessPosition pos;
    @SerializedName("pieceType")
    private PieceType pieceType;
    @SerializedName("color")
    private TeamColor color;
    private boolean moved = false;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceType = type;
        this.color = pieceColor;
    }
    public void setPosition(ChessPosition position) {
        this.pos = position;
    }
    public void setMoved(boolean value) {
        this.moved = value;
    }
    public boolean getMoved() {
        return this.moved;
    }
    public ChessPosition getChessPosition() {
        return this.pos;
    }
    public void setPieceType(PieceType type) {
        this.pieceType = type;
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
        Collection<ChessMove> moves = new ArrayList<>();
        if (board.getPiece(myPosition) == null) {
            throw new RuntimeException("No piece in this position");
        }
        if (this.pieceType == PieceType.BISHOP) {
            BishopMoves bishop = new BishopMoves();
            moves = bishop.getMoves(board, myPosition, this.color);
        }
        else if (this.pieceType == PieceType.KING) {
            KingMoves king = new KingMoves();
            moves = king.getMoves(board, myPosition, this.color);
        }
        else if (this.pieceType == PieceType.KNIGHT) {
            KnightMoves knight = new KnightMoves();
            moves = knight.getMoves(board, myPosition, this.color);
        }
        else if (this.pieceType == PieceType.PAWN) {
            //can only move forward, first check what color the pawn is
            PawnMoves pawn = new PawnMoves();
            moves = pawn.getMoves(board, myPosition, this.color);
        }
        else if (this.pieceType == PieceType.QUEEN) {
            QueenMoves queen = new QueenMoves();
            moves = queen.getMoves(board, myPosition, this.color);
        }
        else if (this.pieceType == PieceType.ROOK) {
            RookMoves rook = new RookMoves();
            moves = rook.getMoves(board, myPosition, this.color);
        }
        return moves;
    }
}
