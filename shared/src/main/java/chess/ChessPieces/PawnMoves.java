package chess.chessPieces;

import java.util.ArrayList;
import java.util.Collection;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessGame.TeamColor;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

public class PawnMoves {
    private Collection<ChessMove> moves = new ArrayList<>();
    public PawnMoves() {}
    public Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition, TeamColor color) {
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
                    if (board.getPiece(attack).getTeamColor() == ChessGame.TeamColor.WHITE) {
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
                    if (board.getPiece(backAttack).getTeamColor() == ChessGame.TeamColor.WHITE) {
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
                    if (board.getPiece(attack).getTeamColor() == ChessGame.TeamColor.BLACK) 
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
                    if (board.getPiece(backAttack).getTeamColor() == ChessGame.TeamColor.BLACK) {
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
}
