package chess;

import java.util.Collection;
import java.util.ArrayList;

import chess.ChessGame.TeamColor;
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
    //need to check for pieces color, if enemy then can grab it, else blocked
    public Collection <ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves) {
        int column = myPosition.getColumn();
        int rows = myPosition.getRow();
        for (int i = column+1, j = rows+1; i<9 && j<9; i++,j++ )  //maybe try less then 8
            {
                ChessPosition pos = new ChessPosition(j, i);
                //if there isn't a 
                if (board.getPiece(pos) == null)
                {
                    //create new move
                    ChessMove move = new ChessMove(myPosition, pos, null); //maybe switch piecetype to null?
                    moves.add(move);
                }
                else {
                    ChessPiece pieceBlocked = board.getPiece(pos);
                    ChessPiece currPiece = board.getPiece(myPosition);
                    if (currPiece.getTeamColor() == pieceBlocked.getTeamColor()) {
                        break;
                    }
                    else {
                        ChessMove move = new ChessMove(myPosition, pos, null); //checking this
                        moves.add(move);
                        break;
                    }
                    //add the move, then break?
                    //for now not adding move
                }
            }
            for (int i = column+1, j = rows-1; i<9 && j>0; i++,j-- ) 
            {
                ChessPosition pos = new ChessPosition(j, i);
                //if there isn't a 
                if (board.getPiece(pos) == null)
                {
                    //create new move
                    ChessMove move = new ChessMove(myPosition, pos, null); //maybe switch piecetype to null?
                    moves.add(move);
                }
                else {
                    ChessPiece pieceBlocked = board.getPiece(pos);
                    ChessPiece currPiece = board.getPiece(myPosition);
                    if (currPiece.getTeamColor() == pieceBlocked.getTeamColor()) {
                        break;
                    }
                    else {
                        ChessMove move = new ChessMove(myPosition, pos, null); //checking this
                        moves.add(move);
                        break;
                    }
                }
            }
            for (int i = column-1, j = rows+1; i>0 && j<9;i--,j++ ) 
            {
                ChessPosition pos = new ChessPosition(j, i);
                //if there isn't a 
                if (board.getPiece(pos) == null)
                {
                    //create new move
                    ChessMove move = new ChessMove(myPosition, pos, null); //maybe switch piecetype to null?
                    moves.add(move);
                }
                else {
                    ChessPiece pieceBlocked = board.getPiece(pos);
                    ChessPiece currPiece = board.getPiece(myPosition);
                    if (currPiece.getTeamColor() == pieceBlocked.getTeamColor()) {
                        break;
                    }
                    else {
                        ChessMove move = new ChessMove(myPosition, pos, null); //checking this
                        moves.add(move);
                        break;
                    }
                }
            }
            for (int i = column-1, j = rows-1; i>0 && j>0; i--,j-- ) 
            {
                ChessPosition pos = new ChessPosition(j, i);
                //if there isn't a 
                if (board.getPiece(pos) == null)
                {
                    //create new move
                    ChessMove move = new ChessMove(myPosition, pos, null); //maybe switch piecetype to null?
                    moves.add(move);
                }
                else {
                    ChessPiece pieceBlocked = board.getPiece(pos);
                    ChessPiece currPiece = board.getPiece(myPosition);
                    if (currPiece.getTeamColor() == pieceBlocked.getTeamColor()) {
                        break;
                    }
                    else {
                        ChessMove move = new ChessMove(myPosition, pos, null); //checking this
                        moves.add(move);
                        break;
                    }
                }
            }
        return moves;
    } 
    public Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves) {
        int column = myPosition.getColumn();
        int rows = myPosition.getRow();
        if (this.color == TeamColor.BLACK) 
            {
                ChessPosition posKing = new ChessPosition(8, 5);
                ChessPosition posRook1 = new ChessPosition(8, 1);
                ChessPosition posRook2 = new ChessPosition(8, 8);
                if (board.getPiece(posKing) == null || board.getPiece(posRook1) == null) {

                }
                else if (board.getPiece(posKing).pieceType == PieceType.KING && board.getPiece(posRook1).pieceType == PieceType.ROOK) 
                {
                    //check if pieces in between them are empty 
                    if (board.getPiece(new ChessPosition(8, 4)) == null && board.getPiece(new ChessPosition(8, 3))== null && board.getPiece(new ChessPosition(8, 2))==null) {
                        ChessPosition newKing = new ChessPosition(8, 3);
                        ChessPosition newRook = new ChessPosition(8, 4);
                        ChessMove newMove = new ChessMove(myPosition, newKing, null);
                        newMove.setCastle(posRook1, newRook);
                        moves.add(newMove);
                    }
                    //later make sure they're the right color
                }
                if (board.getPiece(posKing) == null || board.getPiece(posRook2) == null) {

                }
                else if (board.getPiece(posKing).pieceType == PieceType.KING && board.getPiece(posRook2).pieceType == PieceType.ROOK) 
                {
                    if (board.getPiece(new ChessPosition(8, 6)) == null && board.getPiece(new ChessPosition(8, 7)) == null) {
                        ChessPosition newKing = new ChessPosition(8, 7);
                        ChessPosition newRook = new ChessPosition(8, 6);
                        ChessMove newMove = new ChessMove(myPosition, newKing, null);
                        newMove.setCastle(posRook2, newRook);
                        moves.add(newMove);
                    }
                    //later make sure they're the right color
                }
                //maybe just add one move, with a castle thing added, then we can manually move the other piece
                //next to it
            }
        else 
        {
            ChessPosition posKing = new ChessPosition(1, 5);
            ChessPosition posRook1 = new ChessPosition(1, 1);
            ChessPosition posRook2 = new ChessPosition(1, 8);
            if (board.getPiece(posKing) == null || board.getPiece(posRook1) == null) {

            }
            else if (board.getPiece(posKing).pieceType == PieceType.KING && board.getPiece(posRook1).pieceType == PieceType.ROOK) 
            {
                if (board.getPiece(new ChessPosition(1, 4)) == null && board.getPiece(new ChessPosition(1, 3)) == null && board.getPiece(new ChessPosition(1, 2)) == null) {
                    ChessPosition newKing = new ChessPosition(1, 3);
                    ChessPosition newRook = new ChessPosition(1, 4);
                    ChessMove newMove = new ChessMove(myPosition, newKing, null);
                    newMove.setCastle(posRook1, newRook);
                    moves.add(newMove);
                }
                //later make sure they're the right color

            }
            if (board.getPiece(posKing) != null && board.getPiece(posRook2) != null) {
                //do nothing
                if (board.getPiece(posKing).pieceType == PieceType.KING && board.getPiece(posRook2).pieceType == PieceType.ROOK) 
                {
                    if (board.getPiece(new ChessPosition(1, 6)) == null && board.getPiece(new ChessPosition(1, 7)) == null) {
                        ChessPosition newKing = new ChessPosition(1, 7);
                        ChessPosition newRook = new ChessPosition(1, 6);
                        ChessMove newMove = new ChessMove(myPosition, newKing, null);
                        newMove.setCastle(posRook2, newRook);
                        moves.add(newMove);
                    }
                    //later make sure they're the right color
                }
            }
        }
        //check every one piece move
        int rows1 = rows+1;
        int minusRows = rows-1;
        int addCols = column+1;
        int minusCols = column-1;
        //may be doubling?? 
        if ((rows1) < 9) {  //here all the moves in the next row{
            if ((minusCols) > 0) 
            {
                ChessPosition pos = new ChessPosition(rows1, minusCols);
                ChessPiece pieceBlocked = board.getPiece(pos);
                if (pieceBlocked == null) {
                    ChessMove move = new ChessMove(myPosition, pos, null);
                    moves.add(move);
                }
                else {
                    ChessPiece currPiece = board.getPiece(myPosition);
                    if (currPiece.getTeamColor() == pieceBlocked.getTeamColor()) {
                        //do nothing
                    }
                    else {
                        ChessMove move = new ChessMove(myPosition, pos, null); //checking this
                        moves.add(move);
                    }
                }
            }
            if ((addCols) < 9) 
            {
                ChessPosition pos = new ChessPosition(rows1, addCols);
                ChessPiece pieceBlocked = board.getPiece(pos);
                if (pieceBlocked == null) {
                    ChessMove move = new ChessMove(myPosition, pos, null);
                    moves.add(move);
                }
                else {
                    ChessPiece currPiece = board.getPiece(myPosition);
                    if (currPiece.getTeamColor() == pieceBlocked.getTeamColor()) {
                        //do nothing
                    }
                    else {
                        ChessMove move = new ChessMove(myPosition, pos, null); //checking this
                        moves.add(move);
                    }
                }
            }
            ChessPosition pos = new ChessPosition(rows1, column);
            ChessPiece pieceBlocked = board.getPiece(pos);
            if (pieceBlocked == null) {
                ChessMove move = new ChessMove(myPosition, pos, null);
                moves.add(move);
            }
            else {
                ChessPiece currPiece = board.getPiece(myPosition);
                if (currPiece.getTeamColor() == pieceBlocked.getTeamColor()) {
                    //do nothing
                }
                else {
                    ChessMove move = new ChessMove(myPosition, pos, null); //checking this
                    moves.add(move);
                }
            }
        }
        if (addCols < 9) {
            ChessPosition pos = new ChessPosition(rows, addCols);
            ChessPiece pieceBlocked = board.getPiece(pos);
            if (pieceBlocked == null) {
                ChessMove move = new ChessMove(myPosition, pos, null);
                moves.add(move);
            }
            else {
                ChessPiece currPiece = board.getPiece(myPosition);
                if (currPiece.getTeamColor() == pieceBlocked.getTeamColor()) {
                    //do nothing
                }
                else {
                    ChessMove move = new ChessMove(myPosition, pos, null); //checking this
                    moves.add(move);
                }
            }
        }
        if ((minusRows) > 0) {  //here all the moves in the next row{
            if ((minusCols) > 0) 
            {
                ChessPosition pos = new ChessPosition(minusRows, minusCols);
                ChessPiece pieceBlocked = board.getPiece(pos);
                if (pieceBlocked == null) {
                    ChessMove move = new ChessMove(myPosition, pos, null);
                    moves.add(move);
                }
                else {
                    ChessPiece currPiece = board.getPiece(myPosition);
                    if (currPiece.getTeamColor() == pieceBlocked.getTeamColor()) {
                        //do nothing
                    }
                    else {
                        ChessMove move = new ChessMove(myPosition, pos, null); //checking this
                        moves.add(move);
                    }
                }
            }
            if ((addCols) < 9) 
            {
                ChessPosition pos = new ChessPosition(minusRows, addCols);
                ChessPiece pieceBlocked = board.getPiece(pos);
                if (pieceBlocked == null) {
                    ChessMove move = new ChessMove(myPosition, pos, null);
                    moves.add(move);
                }
                else {
                    ChessPiece currPiece = board.getPiece(myPosition);
                    if (currPiece.getTeamColor() == pieceBlocked.getTeamColor()) {
                        //do nothing
                    }
                    else {
                        ChessMove move = new ChessMove(myPosition, pos, null); //checking this
                        moves.add(move);
                    }
                }
            }
            ChessPosition pos = new ChessPosition(minusRows, column);
            ChessPiece pieceBlocked = board.getPiece(pos);
            if (pieceBlocked == null) {
                ChessMove move = new ChessMove(myPosition, pos, null);
                moves.add(move);
            }
            else {
                ChessPiece currPiece = board.getPiece(myPosition);
                if (currPiece.getTeamColor() == pieceBlocked.getTeamColor()) {
                    //do nothing
                }
                else {
                    ChessMove move = new ChessMove(myPosition, pos, null); //checking this
                    moves.add(move);
                }
            }
        }
        if (minusCols > 0) {
            ChessPosition pos = new ChessPosition(rows, minusCols);
            ChessPiece pieceBlocked = board.getPiece(pos);
            if (pieceBlocked == null) {
                ChessMove move = new ChessMove(myPosition, pos, null);
                moves.add(move);
            }
            else {
                ChessPiece currPiece = board.getPiece(myPosition);
                if (currPiece.getTeamColor() == pieceBlocked.getTeamColor()) {
                    //do nothing
                }
                else {
                    ChessMove move = new ChessMove(myPosition, pos, null); //checking this
                    moves.add(move);
                }
            }
        }
        return moves;
    }
    public Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves) {
        int rows = myPosition.getRow();
        int column= myPosition.getColumn();
        int rows2 = rows+2;
        int col2 = column+2;
        int colMinus = column-2;
        int rowMinus = rows-2;
        if (rows2 < 9) 
        {
            if (column-1 > 0) {
                ChessPosition pos = new ChessPosition(rows2, column-1);
                ChessPiece pieceBlocked = board.getPiece(pos);
                if (pieceBlocked == null) {
                    ChessMove move = new ChessMove(myPosition, pos, null);
                    moves.add(move);
                }
                else {
                    ChessPiece currPiece = board.getPiece(myPosition);
                    if (currPiece.getTeamColor() == pieceBlocked.getTeamColor()) {
                        //do nothing
                    }
                    else {
                        ChessMove move = new ChessMove(myPosition, pos, null); //checking this
                        moves.add(move);
                    }
                }
            }
            if (column+1 < 9) 
            {
                ChessPosition pos = new ChessPosition(rows2, column+1);
                ChessPiece pieceBlocked = board.getPiece(pos);
                if (pieceBlocked == null) {
                    ChessMove move = new ChessMove(myPosition, pos, null);
                    moves.add(move);
                }
                else {
                    ChessPiece currPiece = board.getPiece(myPosition);
                    if (currPiece.getTeamColor() == pieceBlocked.getTeamColor()) {
                        //do nothing
                    }
                    else {
                        ChessMove move = new ChessMove(myPosition, pos, null); //checking this
                        moves.add(move);
                    }
                }
            }
        }
        if (rowMinus > 0) 
        {
            if (column-1 > 0) {
                ChessPosition pos = new ChessPosition(rowMinus, column-1);
                ChessPiece pieceBlocked = board.getPiece(pos);
                if (pieceBlocked == null) {
                    ChessMove move = new ChessMove(myPosition, pos, null);
                    moves.add(move);
                }
                else {
                    ChessPiece currPiece = board.getPiece(myPosition);
                    if (currPiece.getTeamColor() == pieceBlocked.getTeamColor()) {
                        //do nothing
                    }
                    else {
                        ChessMove move = new ChessMove(myPosition, pos, null); //checking this
                        moves.add(move);
                    }
                }
            }
            if (column+1 < 9) 
            {
                ChessPosition pos = new ChessPosition(rowMinus, column+1);
                ChessPiece pieceBlocked = board.getPiece(pos);
                if (pieceBlocked == null) {
                    ChessMove move = new ChessMove(myPosition, pos, null);
                    moves.add(move);
                }
                else {
                    ChessPiece currPiece = board.getPiece(myPosition);
                    if (currPiece.getTeamColor() == pieceBlocked.getTeamColor()) {
                        //do nothing
                    }
                    else {
                        ChessMove move = new ChessMove(myPosition, pos, null); //checking this
                        moves.add(move);
                    }
                }
            }
        }    
        if (colMinus > 0) {
            if (rows-1 > 0) {
                ChessPosition pos = new ChessPosition(rows-1, colMinus);
                ChessPiece pieceBlocked = board.getPiece(pos);
                if (pieceBlocked == null) {
                    ChessMove move = new ChessMove(myPosition, pos, null);
                    moves.add(move);
                }
                else {
                    ChessPiece currPiece = board.getPiece(myPosition);
                    if (currPiece.getTeamColor() == pieceBlocked.getTeamColor()) {
                        //do nothing
                    }
                    else {
                        ChessMove move = new ChessMove(myPosition, pos, null); //checking this
                        moves.add(move);
                    }
                }
            }
            if (rows+1 < 9) 
            {
                ChessPosition pos = new ChessPosition(rows+1, colMinus);
                ChessPiece pieceBlocked = board.getPiece(pos);
                if (pieceBlocked == null) {
                    ChessMove move = new ChessMove(myPosition, pos, null);
                    moves.add(move);
                }
                else {
                    ChessPiece currPiece = board.getPiece(myPosition);
                    if (currPiece.getTeamColor() == pieceBlocked.getTeamColor()) {
                        //do nothing
                    }
                    else {
                        ChessMove move = new ChessMove(myPosition, pos, null); //checking this
                        moves.add(move);
                    }
                }
            }
        }
        if (col2 < 9) {
                if (rows-1 > 0) {
                    ChessPosition pos = new ChessPosition(rows-1, col2);
                    ChessPiece pieceBlocked = board.getPiece(pos);
                    if (pieceBlocked == null) {
                        ChessMove move = new ChessMove(myPosition, pos, null);
                        moves.add(move);
                    }
                    else {
                        ChessPiece currPiece = board.getPiece(myPosition);
                        if (currPiece.getTeamColor() == pieceBlocked.getTeamColor()) {
                            //do nothing
                        }
                        else {
                            ChessMove move = new ChessMove(myPosition, pos, null); //checking this
                            moves.add(move);
                        }
                    }
                }
                if (rows+1 < 9) 
                {
                    ChessPosition pos = new ChessPosition(rows+1, col2);
                    ChessPiece pieceBlocked = board.getPiece(pos);
                    if (pieceBlocked == null) {
                        ChessMove move = new ChessMove(myPosition, pos, null);
                        moves.add(move);
                    }
                    else {
                        ChessPiece currPiece = board.getPiece(myPosition);
                        if (currPiece.getTeamColor() == pieceBlocked.getTeamColor()) {
                            //do nothing
                        }
                        else {
                            ChessMove move = new ChessMove(myPosition, pos, null); //checking this
                            moves.add(move);
                        }
                    }
                }
            }
        return moves;
    }
    public Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves) {
        int rows = myPosition.getRow();
        int column = myPosition.getColumn();
        ChessPiece currPiece = board.getPiece(myPosition);
        if (currPiece.getTeamColor() == ChessGame.TeamColor.BLACK) 
        {
            //row would be 7
            ChessPosition pos = new ChessPosition(rows-1, column);
            if (myPosition.getRow() == 7) 
            {
                ChessPosition pos2 = new ChessPosition(rows-2, column);
                if (board.getPiece(pos2) == null) {
                    if (board.getPiece(pos) == null) {
                        ChessMove move = new ChessMove(myPosition, pos2, null);
                        moves.add(move);
                    }
                }
            }
            if (board.getPiece(pos) == null) {
                if (pos.getRow() == 1) {
                    ChessMove move = new ChessMove(myPosition, pos, ChessPiece.PieceType.BISHOP);
                    ChessMove move2 = new ChessMove(myPosition, pos, ChessPiece.PieceType.QUEEN);
                    ChessMove move3 = new ChessMove(myPosition, pos, ChessPiece.PieceType.KNIGHT);
                    ChessMove move4 = new ChessMove(myPosition, pos, ChessPiece.PieceType.ROOK);
                    moves.add(move4);
                    moves.add(move);
                    moves.add(move2);
                    moves.add(move3);
                }
                else {
                    ChessMove move = new ChessMove(myPosition, pos, null);
                    moves.add(move);
                }
            }
            ChessPosition attack = new ChessPosition(rows-1, column+1);
            ChessPosition backAttack = new ChessPosition(rows-1, column-1);
            if (rows-1 > 0 && column+1 < 9) {
                if (board.getPiece(attack) != null) 
                {
                    if (board.getPiece(attack).color == ChessGame.TeamColor.WHITE) {
                        if (attack.getRow() == 1) {
                            ChessMove move = new ChessMove(myPosition, attack, ChessPiece.PieceType.BISHOP);
                            ChessMove move2 = new ChessMove(myPosition, attack, ChessPiece.PieceType.QUEEN);
                            ChessMove move3 = new ChessMove(myPosition, attack, ChessPiece.PieceType.KNIGHT);
                            ChessMove move4 = new ChessMove(myPosition, attack, ChessPiece.PieceType.ROOK);
                            moves.add(move4);
                            moves.add(move);
                            moves.add(move2);
                            moves.add(move3);
                        }
                        else {
                            ChessMove move = new ChessMove(myPosition, attack, null);
                            moves.add(move);
                        }
                    }
                }
            }
            
            if (rows-1 > 0 && column-1 >0) 
            {
                if (board.getPiece(backAttack) != null) 
                {
                    if (board.getPiece(backAttack).color == ChessGame.TeamColor.WHITE) {
                        if (backAttack.getRow() == 1) {
                            ChessMove move = new ChessMove(myPosition, backAttack, ChessPiece.PieceType.BISHOP);
                            ChessMove move2 = new ChessMove(myPosition, backAttack, ChessPiece.PieceType.QUEEN);
                            ChessMove move3 = new ChessMove(myPosition, backAttack, ChessPiece.PieceType.KNIGHT);
                            ChessMove move4 = new ChessMove(myPosition, backAttack, ChessPiece.PieceType.ROOK);
                            moves.add(move4);
                            moves.add(move);
                            moves.add(move2);
                            moves.add(move3);
                        }
                        else {
                            ChessMove move = new ChessMove(myPosition, backAttack, null);
                            moves.add(move);
                        }
                    }
                }
            }
            
        } 
        else {
            ChessPosition pos = new ChessPosition(rows+1, column);
            if (rows == 2) 
            {
                ChessPosition pos2 = new ChessPosition(rows+2, column);
                if (board.getPiece(pos2) == null) {
                    if (board.getPiece(pos) == null) {
                        ChessMove move = new ChessMove(myPosition, pos2, null);
                        moves.add(move);
                    }
                }
            }
            if (board.getPiece(pos) == null) {
                if (pos.getRow() == 8) {
                    ChessMove move = new ChessMove(myPosition, pos, ChessPiece.PieceType.BISHOP);
                    ChessMove move2 = new ChessMove(myPosition, pos, ChessPiece.PieceType.QUEEN);
                    ChessMove move3 = new ChessMove(myPosition, pos, ChessPiece.PieceType.KNIGHT);
                    ChessMove move4 = new ChessMove(myPosition, pos, ChessPiece.PieceType.ROOK);
                    moves.add(move4);
                    moves.add(move);
                    moves.add(move2);
                    moves.add(move3);
                }
                else {
                    ChessMove move = new ChessMove(myPosition, pos, null);
                    moves.add(move);
                }
            }
            ChessPosition attack = new ChessPosition(rows+1, column+1);
            ChessPosition backAttack = new ChessPosition(rows+1, column-1); //making sure it isn't trapped on one side
            if (column+1 < 9 && rows+1 < 9) {
                if (board.getPiece(attack) != null) 
                {
                    if (board.getPiece(attack).color == ChessGame.TeamColor.BLACK) 
                    {
                        if (attack.getRow() == 8) {
                            ChessMove move = new ChessMove(myPosition, attack, ChessPiece.PieceType.BISHOP);
                            ChessMove move2 = new ChessMove(myPosition, attack, ChessPiece.PieceType.QUEEN);
                            ChessMove move3 = new ChessMove(myPosition, attack, ChessPiece.PieceType.KNIGHT);
                            ChessMove move4 = new ChessMove(myPosition, attack, ChessPiece.PieceType.ROOK);
                            moves.add(move4);
                            moves.add(move);
                            moves.add(move2);
                            moves.add(move3);
                        }
                        else {
                            ChessMove move = new ChessMove(myPosition, attack, null);
                            moves.add(move);
                        }
                    }
                }
            }
            if (column-1 > 0 && rows+1 < 9) 
            {
                if (board.getPiece(backAttack) != null) 
                {
                    if (board.getPiece(backAttack).color == ChessGame.TeamColor.BLACK) {
                        if (backAttack.getRow() == 8) {
                            ChessMove move = new ChessMove(myPosition, backAttack, ChessPiece.PieceType.BISHOP);
                            ChessMove move2 = new ChessMove(myPosition, backAttack, ChessPiece.PieceType.QUEEN);
                            ChessMove move3 = new ChessMove(myPosition, backAttack, ChessPiece.PieceType.KNIGHT);
                            ChessMove move4 = new ChessMove(myPosition, backAttack, ChessPiece.PieceType.ROOK);
                            moves.add(move4);
                            moves.add(move);
                            moves.add(move2);
                            moves.add(move3);
                        }
                        else {
                            ChessMove move = new ChessMove(myPosition, backAttack, null);
                            moves.add(move);
                        }
                    }
                }
            }
        }
        return moves;
    }
    public Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves) {
        int rows = myPosition.getRow();
        int column = myPosition.getColumn();
        for (int i = column+1, j=rows+1; i < 9 && j < 9; i++, j++) {
            ChessPosition pos = new ChessPosition(j, i);
            //if there isn't a 
            if (board.getPiece(pos) == null)
            {
                //create new move
                ChessMove move = new ChessMove(myPosition, pos, null); //maybe switch piecetype to null?
                moves.add(move);
            }
            else {
                ChessPiece pieceBlocked = board.getPiece(pos);
                ChessPiece currPiece = board.getPiece(myPosition);
                if (currPiece.getTeamColor() == pieceBlocked.getTeamColor()) {
                    break;
                }
                else {
                    ChessMove move = new ChessMove(myPosition, pos, null); //checking this
                    moves.add(move);
                    break;
                }
                //add the move, then break?
                //for now not adding move
            }
        } 
        for (int i = column+1, j = rows-1; i<9 && j>0; i++,j-- ) {
            ChessPosition pos = new ChessPosition(j, i);
            //if there isn't a 
            if (board.getPiece(pos) == null)
            {
                //create new move
                ChessMove move = new ChessMove(myPosition, pos, null); //maybe switch piecetype to null?
                moves.add(move);
            }
            else {
                ChessPiece pieceBlocked = board.getPiece(pos);
                ChessPiece currPiece = board.getPiece(myPosition);
                if (currPiece.getTeamColor() == pieceBlocked.getTeamColor()) {
                    break;
                }
                else {
                    ChessMove move = new ChessMove(myPosition, pos, null); //checking this
                    moves.add(move);
                    break;
                }
            }
        }
        for (int i = column-1, j = rows+1; i>0 && j<9;i--,j++ ) {
            ChessPosition pos = new ChessPosition(j, i);
            //if there isn't a 
            if (board.getPiece(pos) == null)
            {
                //create new move
                ChessMove move = new ChessMove(myPosition, pos, null); //maybe switch piecetype to null?
                moves.add(move);
            }
            else {
                ChessPiece pieceBlocked = board.getPiece(pos);
                ChessPiece currPiece = board.getPiece(myPosition);
                if (currPiece.getTeamColor() == pieceBlocked.getTeamColor()) {
                    break;
                }
                else {
                    ChessMove move = new ChessMove(myPosition, pos, null); //checking this
                    moves.add(move);
                    break;
                }
            }
        }
        for (int i = column-1, j = rows-1; i>0 && j>0; i--,j-- ) {
            ChessPosition pos = new ChessPosition(j, i);
            //if there isn't a 
            if (board.getPiece(pos) == null)
            {
                //create new move
                ChessMove move = new ChessMove(myPosition, pos, null); //maybe switch piecetype to null?
                moves.add(move);
            }
            else {
                ChessPiece pieceBlocked = board.getPiece(pos);
                ChessPiece currPiece = board.getPiece(myPosition);
                if (currPiece.getTeamColor() == pieceBlocked.getTeamColor()) {
                    break;
                }
                else {
                    ChessMove move = new ChessMove(myPosition, pos, null); //checking this
                    moves.add(move);
                    break;
                }
            }
        }
        for (int i = column+1; i < 9; i++) {
            ChessPosition pos = new ChessPosition(rows, i);
            //if there isn't a 
            if (board.getPiece(pos) == null)
            {
                //create new move
                ChessMove move = new ChessMove(myPosition, pos, null); //maybe switch piecetype to null?
                moves.add(move);
            }
            else {
                ChessPiece pieceBlocked = board.getPiece(pos);
                ChessPiece currPiece = board.getPiece(myPosition);
                if (currPiece.getTeamColor() == pieceBlocked.getTeamColor()) {
                    break;
                }
                else {
                    ChessMove move = new ChessMove(myPosition, pos, null); //checking this
                    moves.add(move);
                    break;
                }
                //add the move, then break?
                //for now not adding move
            }
        } 
        for (int i = column-1; i > 0; i--) {

            ChessPosition pos = new ChessPosition(rows, i);
            //if there isn't a 
            if (board.getPiece(pos) == null)
            {
                //create new move
                ChessMove move = new ChessMove(myPosition, pos, null); //maybe switch piecetype to null?
                moves.add(move);
            }
            else {
                ChessPiece pieceBlocked = board.getPiece(pos);
                ChessPiece currPiece = board.getPiece(myPosition);
                if (currPiece.getTeamColor() == pieceBlocked.getTeamColor()) {
                    break;
                }
                else {
                    ChessMove move = new ChessMove(myPosition, pos, null); //checking this
                    moves.add(move);
                    break;
                }
                //add the move, then break?
                //for now not adding move
            }
        } 
        for (int i = rows+1; i < 9; i++) {
            ChessPosition pos = new ChessPosition(i, column);
            //if there isn't a 
            if (board.getPiece(pos) == null)
            {
                //create new move
                ChessMove move = new ChessMove(myPosition, pos, null); //maybe switch piecetype to null?
                moves.add(move);
            }
            else {
                ChessPiece pieceBlocked = board.getPiece(pos);
                ChessPiece currPiece = board.getPiece(myPosition);
                if (currPiece.getTeamColor() == pieceBlocked.getTeamColor()) {
                    break;
                }
                else {
                    ChessMove move = new ChessMove(myPosition, pos, null); //checking this
                    moves.add(move);
                    break;
                }
                //add the move, then break?
                //for now not adding move
            }
        } 
        for (int i = rows-1; i > 0; i--) {
            ChessPosition pos = new ChessPosition(i, column);
            //if there isn't a 
            if (board.getPiece(pos) == null)
            {
                //create new move
                ChessMove move = new ChessMove(myPosition, pos, null); //maybe switch piecetype to null?
                moves.add(move);
            }
            else {
                ChessPiece pieceBlocked = board.getPiece(pos);
                ChessPiece currPiece = board.getPiece(myPosition);
                if (currPiece.getTeamColor() == pieceBlocked.getTeamColor()) {
                    break;
                }
                else {
                    ChessMove move = new ChessMove(myPosition, pos, null); //checking this
                    moves.add(move);
                    break;
                }
                //add the move, then break?
                //for now not adding move
            }
        }
        return moves;
    }
    public Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves) {
        int rows = myPosition.getRow();
        int column = myPosition.getColumn();
        if (this.color == TeamColor.BLACK) 
        {
            ChessPosition posKing = new ChessPosition(8, 5);
            ChessPosition posRook1 = new ChessPosition(8, 1);
            ChessPosition posRook2 = new ChessPosition(8, 8);
            if (board.getPiece(posKing) == null || board.getPiece(posRook1) == null) {

            }
            else if (board.getPiece(posKing).pieceType == PieceType.KING && board.getPiece(posRook1).pieceType == PieceType.ROOK) 
            {
                //later make sure they're the right color
                if (board.getPiece(new ChessPosition(8, 4)) == null && board.getPiece(new ChessPosition(8, 3))== null && board.getPiece(new ChessPosition(8, 2))==null) {
                    ChessPosition newKing = new ChessPosition(8, 3);
                    ChessPosition newRook = new ChessPosition(8, 4);
                    ChessMove newMove = new ChessMove(myPosition, newKing, null);
                    newMove.setCastle(posRook1, newRook);
                    moves.add(newMove);
                }
            }
            if (board.getPiece(posKing) == null || board.getPiece(posRook2) == null) {

            }
            else if (board.getPiece(posKing).pieceType == PieceType.KING && board.getPiece(posRook2).pieceType == PieceType.ROOK) 
            {
                //later make sure they're the right color
                if (board.getPiece(new ChessPosition(8, 6)) == null && board.getPiece(new ChessPosition(8, 7)) == null) {
                    ChessPosition newKing = new ChessPosition(8, 7);
                    ChessPosition newRook = new ChessPosition(8, 6);
                    ChessMove newMove = new ChessMove(myPosition, newKing, null);
                    newMove.setCastle(posRook2, newRook);
                    moves.add(newMove);
                }
            }
            //maybe just add one move, with a castle thing added, then we can manually move the other piece
            //next to it
        }
        else 
        {
            ChessPosition posKing = new ChessPosition(1, 5);
            ChessPosition posRook1 = new ChessPosition(1, 1);
            ChessPosition posRook2 = new ChessPosition(1, 8);
            if (board.getPiece(posKing) == null || board.getPiece(posRook1) == null) {

            }
            else if (board.getPiece(posKing).pieceType == PieceType.KING && board.getPiece(posRook1).pieceType == PieceType.ROOK) 
            {
                //later make sure they're the right color
                if (board.getPiece(new ChessPosition(1, 4)) == null && board.getPiece(new ChessPosition(1, 3)) == null && board.getPiece(new ChessPosition(1, 2)) == null) {
                    ChessPosition newKing = new ChessPosition(1, 3);
                    ChessPosition newRook = new ChessPosition(1, 4);
                    ChessMove newMove = new ChessMove(myPosition, newKing, null);
                    newMove.setCastle(posRook1, newRook);
                    moves.add(newMove);
                }
            }
            if (board.getPiece(posKing) != null && board.getPiece(posRook2) != null) {
                //do nothing
                if (board.getPiece(posKing).pieceType == PieceType.KING && board.getPiece(posRook2).pieceType == PieceType.ROOK) 
                {
                    //later make sure they're the right color
                    if (board.getPiece(new ChessPosition(1, 6)) == null && board.getPiece(new ChessPosition(1, 7)) == null) {
                        ChessPosition newKing = new ChessPosition(1, 7);
                        ChessPosition newRook = new ChessPosition(1, 6);
                        ChessMove newMove = new ChessMove(myPosition, newKing, null);
                        newMove.setCastle(posRook2, newRook);
                        moves.add(newMove);
                    }
                }
            }
        }
        for (int i = column+1; i < 9; i++) {
            ChessPosition pos = new ChessPosition(rows, i);
            //if there isn't a 
            if (board.getPiece(pos) == null)
            {
                //create new move
                ChessMove move = new ChessMove(myPosition, pos, null); //maybe switch piecetype to null?
                moves.add(move);
            }
            else {
                ChessPiece pieceBlocked = board.getPiece(pos);
                ChessPiece currPiece = board.getPiece(myPosition);
                if (currPiece.getTeamColor() == pieceBlocked.getTeamColor()) {
                    break;
                }
                else {
                    ChessMove move = new ChessMove(myPosition, pos, null); //checking this
                    moves.add(move);
                    break;
                }
                //add the move, then break?
                //for now not adding move
            }
        } 
        for (int i = column-1; i > 0; i--) {
            ChessPosition pos = new ChessPosition(rows, i);
            //if there isn't a 
            if (board.getPiece(pos) == null)
            {
                //create new move
                ChessMove move = new ChessMove(myPosition, pos, null); //maybe switch piecetype to null?
                moves.add(move);
            }
            else {
                ChessPiece pieceBlocked = board.getPiece(pos);
                ChessPiece currPiece = board.getPiece(myPosition);
                if (currPiece.getTeamColor() == pieceBlocked.getTeamColor()) {
                    break;
                }
                else {
                    ChessMove move = new ChessMove(myPosition, pos, null); //checking this
                    moves.add(move);
                    break;
                }
                //add the move, then break?
                //for now not adding move
            }
        } 
        for (int i = rows+1; i < 9; i++) {
            ChessPosition pos = new ChessPosition(i, column);
            //if there isn't a 
            if (board.getPiece(pos) == null)
            {
                //create new move
                ChessMove move = new ChessMove(myPosition, pos, null); //maybe switch piecetype to null?
                moves.add(move);
            }
            else {
                ChessPiece pieceBlocked = board.getPiece(pos);
                ChessPiece currPiece = board.getPiece(myPosition);
                if (currPiece.getTeamColor() == pieceBlocked.getTeamColor()) {
                    break;
                }
                else {
                    ChessMove move = new ChessMove(myPosition, pos, null); //checking this
                    moves.add(move);
                    break;
                }
                //add the move, then break?
                //for now not adding move
            }
        } 
        for (int i = rows-1; i > 0; i--) {
            ChessPosition pos = new ChessPosition(i, column);
            //if there isn't a 
            if (board.getPiece(pos) == null)
            {
                //create new move
                ChessMove move = new ChessMove(myPosition, pos, null); //maybe switch piecetype to null?
                moves.add(move);
            }
            else {
                ChessPiece pieceBlocked = board.getPiece(pos);
                ChessPiece currPiece = board.getPiece(myPosition);
                if (currPiece.getTeamColor() == pieceBlocked.getTeamColor()) {
                    break;
                }
                else {
                    ChessMove move = new ChessMove(myPosition, pos, null); //checking this
                    moves.add(move);
                    break;
                }
                //add the move, then break?
                //for now not adding move
            }
        }
        return moves;
    }
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        if (board.getPiece(myPosition) == null) {
            throw new RuntimeException("No piece in this position");
        }
        if (this.pieceType == PieceType.BISHOP) {
            moves = bishopMoves(board, myPosition, moves);
        }
        else if (this.pieceType == PieceType.KING) {
            moves = kingMoves(board, myPosition, moves);
        }
        else if (this.pieceType == PieceType.KNIGHT) {
            moves = knightMoves(board, myPosition, moves);
        }
        else if (this.pieceType == PieceType.PAWN) {
            //can only move forward, first check what color the pawn is
            moves = pawnMoves(board, myPosition, moves);
        }
        else if (this.pieceType == PieceType.QUEEN) {
            moves = queenMoves(board, myPosition, moves);
        }
        else if (this.pieceType == PieceType.ROOK) {
            moves = rookMoves(board, myPosition, moves);
        }
        return moves;
    }
}
