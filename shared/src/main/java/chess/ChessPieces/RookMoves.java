package chess.ChessPieces;
import chess.ChessBoard;
import chess.ChessGame.TeamColor;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPiece.PieceType;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class RookMoves {
    private Collection<ChessMove> moves = new ArrayList<>();
    public RookMoves() {

    }
    public Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition, TeamColor color) 
    {
        int rows = myPosition.getRow();
        int column = myPosition.getColumn();
        if (color == TeamColor.BLACK) 
        {
            ChessPosition posKing = new ChessPosition(8, 5);
            ChessPosition posRook1 = new ChessPosition(8, 1);
            ChessPosition posRook2 = new ChessPosition(8, 8);
            if (board.getPiece(posKing) == null || board.getPiece(posRook1) == null) {

            }
            else if (board.getPiece(posKing).getPieceType() == PieceType.KING && board.getPiece(posRook1).getPieceType() == PieceType.ROOK) 
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
            else if (board.getPiece(posKing).getPieceType() == PieceType.KING && board.getPiece(posRook2).getPieceType() == PieceType.ROOK) 
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
            else if (board.getPiece(posKing).getPieceType() == PieceType.KING && board.getPiece(posRook1).getPieceType() == PieceType.ROOK) 
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
                if (board.getPiece(posKing).getPieceType() == PieceType.KING && board.getPiece(posRook2).getPieceType() == PieceType.ROOK) 
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
}
