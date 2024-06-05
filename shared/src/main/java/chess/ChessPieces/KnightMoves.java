package chess.chessPieces;

import java.util.ArrayList;
import java.util.Collection;

import chess.ChessBoard;
import chess.ChessGame.TeamColor;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

public class KnightMoves {
    private Collection<ChessMove> moves = new ArrayList<>();
    public KnightMoves() {}
    public Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition, TeamColor color) {
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
}
