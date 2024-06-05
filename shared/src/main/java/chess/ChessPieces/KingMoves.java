package chess.chesspieces;

import java.util.ArrayList;
import java.util.Collection;

import chess.ChessBoard;
import chess.ChessGame.TeamColor;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPiece.PieceType;
import chess.ChessPosition;

public class KingMoves {
    private Collection<ChessMove> moves = new ArrayList<>();
    public KingMoves() {}
    public Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition, TeamColor color) {
        int column = myPosition.getColumn();
        int rows = myPosition.getRow();
        if (color == TeamColor.BLACK) 
            {
                ChessPosition posKing = new ChessPosition(8, 5);
                ChessPosition posRook1 = new ChessPosition(8, 1);
                ChessPosition posRook2 = new ChessPosition(8, 8);
                if (board.getPiece(posKing) == null || board.getPiece(posRook1) == null) {

                }
                else if (board.getPiece(posKing).getPieceType() == PieceType.KING && board.getPiece(posRook1).getPieceType() == PieceType.ROOK) 
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
                else if (board.getPiece(posKing).getPieceType() == PieceType.KING && board.getPiece(posRook2).getPieceType() == PieceType.ROOK) 
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
            else if (board.getPiece(posKing).getPieceType() == PieceType.KING && board.getPiece(posRook1).getPieceType() == PieceType.ROOK) 
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
                if (board.getPiece(posKing).getPieceType() == PieceType.KING && board.getPiece(posRook2).getPieceType() == PieceType.ROOK) 
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
}
