package chess.chesspieces;

import java.util.ArrayList;
import java.util.Collection;

import chess.ChessBoard;
import chess.ChessGame.TeamColor;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

public class QueenMoves {
    private Collection<ChessMove> moves = new ArrayList<>();
    public QueenMoves() {}
    public Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition, TeamColor color) 
    {
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
}
