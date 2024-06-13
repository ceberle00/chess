package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import chess.ChessBoard;
import chess.ChessPiece;
import chess.ChessGame.TeamColor;
import chess.ChessPiece.PieceType;

import static ui.EscapeSequences.*;
public class ChessGameplay 
{
    private ChessPiece[][] board;
    private PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    public ChessGameplay(ChessBoard game) {
        this.board = game.getBoard();
    }
    public void main(Boolean isReversed) {
        out.print(ERASE_SCREEN); //resetting back to normal
        if (isReversed) {
            setBoardReversed();
        }
        else {
            setBoardNormal();
        }
    }
    private void setBoardReversed() {
        setHeader(true);
        String[] headers = {"1", "2", "3", "4", "5", "6", "7", "8"};
        for (int i = 7; i >= 0; i--) 
        {
            out.print(SET_BG_COLOR_LIGHT_GREY);
            out.print(SET_TEXT_COLOR_BLACK);
            out.print(headers[i]);
            for (int j =0; j < 8; j++) {
                setBlock(i, j);
            }
        }
        setHeader(true);
        out.print(SET_BG_COLOR_WHITE);
    }
    private void setBlock(int i, int j)
    {
        ChessPiece piece = this.board[i][j];
        if ((i %2 == 0 && j% 2==0) || (i%2 != 0 && j%2 != 0) ) {
            setWhite();
        }
        else {
            setBlack();
        }
        if (piece != null) {
            setPiece(piece);
        }
        else {
            out.print("   ");
        }
    }
    private void setPiece(ChessPiece piece) {
        out.print(" ");
        PieceType type = piece.getPieceType();
        TeamColor color = piece.getTeamColor();
        if (color == TeamColor.BLACK) {
            if (type == PieceType.BISHOP) {
                out.print(BLACK_BISHOP);
            }
            if (type == PieceType.KING) {
                out.print(BLACK_KING);
            }
            if (type == PieceType.QUEEN) {
                out.print(BLACK_QUEEN);
            }
            if (type == PieceType.KNIGHT) {
                out.print(BLACK_KNIGHT);
            }
            if (type == PieceType.ROOK) {
                out.print(BLACK_ROOK);
            }
            if (type == PieceType.PAWN) {
                out.print(BLACK_PAWN);
            }
        }
        else {
            if (type == PieceType.BISHOP) {
                out.print(WHITE_BISHOP);
            }
            if (type == PieceType.KING) {
                out.print(WHITE_KING);
            }
            if (type == PieceType.QUEEN) {
                out.print(WHITE_QUEEN);
            }
            if (type == PieceType.KNIGHT) {
                out.print(WHITE_KNIGHT);
            }
            if (type == PieceType.ROOK) {
                out.print(WHITE_ROOK);
            }
            if (type == PieceType.PAWN) {
                out.print(WHITE_PAWN);
            }
        }
        out.print(" "); //idk about spacing
    }

    private void setBoardNormal() {
        setHeader(false);
        String[] headers = {"1", "2", "3", "4", "5", "6", "7", "8"};
        for (int i = 0; i < 8; i++) 
        {
            out.print(SET_BG_COLOR_LIGHT_GREY);
            out.print(SET_TEXT_COLOR_BLACK);
            out.print(headers[i]);
            for (int j =0; j < 8; j++) {
                setBlock(i, j);
            }
        }
        setHeader(false);
        out.print(SET_BG_COLOR_WHITE);
    }
    
    private void setHeader(Boolean isReversed) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLACK);
        if (isReversed) {
            out.print("    h   g   f   e   d   c   b   a     \n");
        }
        else {
            out.print("    a   b   c   d   e   f   g   h     \n");
        }
    }
    private void setBlack() {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }
    private void setWhite() {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_BLACK);
    }
    //headers will be a-h, then 1-8, how we set then will depend on which board is being used
}
