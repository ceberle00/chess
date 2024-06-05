package chess;

import java.util.*;

import com.google.gson.annotations.SerializedName;

import chess.ChessPiece.PieceType;
/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    //maybe will need to change once variables added
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    private boolean isCastle = false; //using this maybe?
    private TeamColor turn = TeamColor.WHITE;
    private ChessBoard board;
    private ChessMove castleMove;

    @SerializedName("blackKing")
    private ChessPosition blackKing;
    @SerializedName("whiteKing")
    private ChessPosition whiteKing;
    public ChessGame() {
        this.board = new ChessBoard();
        this.board.resetBoard();
        //set kings for easy access
        ChessPosition white = new ChessPosition(1, 4); //should be the default board, unless otherwise specificed
        ChessPosition black = new ChessPosition(7, 4);
        blackKing = black;
        whiteKing = white;


    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() 
    {
        return this.turn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.turn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    //
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        Collection<ChessMove> validMoves = new ArrayList<>();
        ChessPiece piece = this.board.getPiece(startPosition);
        //maybe first find where king is?
        if (piece != null) {
            moves = piece.pieceMoves(this.board, startPosition);
        }
        else {
            return null;
        }
        ChessPiece king = findPiece(PieceType.KING, piece.getTeamColor()); //get your own king?
        //case for if king is null i guess
        //issue for moves regarding the king
        for (ChessMove move : moves) 
        {
            ChessPosition end = move.getEndPosition();
            ChessPosition kingPos;
            if (king == null) {
                kingPos = null;
            }
            else {
                kingPos = king.getChessPosition();
            }
            if (move.getIsCastle()) 
            {
 
                this.isCastle = true;
                this.castleMove = move;
                if (!(isCheck(end, kingPos, startPosition)) && this.board.getPiece(startPosition).getMoved() == false && this.board.getPiece(move.getCastleStart()).getMoved() == false) 
                {
                    validMoves.add(move);
                }
            }
            else {
                if (!(isCheck(end, kingPos, startPosition)))
                {
                validMoves.add(move);
                }
            }
            this.isCastle = false;
        }
       //maybe check start position to see if king is nearby//moving will affect it?
        //iterate through moves, check each one to see if it leaves the king open?
        return validMoves; //check if any moves leave king open
    }

    //add things to handle castling
    private boolean isCheck(ChessPosition endPosition, ChessPosition kingPosition, ChessPosition start) 
    {
        if (kingPosition == null) 
        {
            return false;
        }
        if (kingPosition.equals(start)) {
            kingPosition = new ChessPosition(endPosition.getRow(), endPosition.getColumn());
        }
        ChessBoard boardCopy = this.board.clone();
        //resetting 
        if (this.isCastle) 
        {
            boardCopy.addPiece(this.castleMove.getCastleEnd(), this.board.getPiece(this.castleMove.getCastleStart()));
            boardCopy.addPiece(this.castleMove.getCastleStart(), null);
            boardCopy.addPiece(endPosition, board.getPiece(start));
            boardCopy.addPiece(start, null);
            if (kingPosition == endPosition) {
                //do nothing
            }
            else 
            {
                kingPosition = this.castleMove.getCastleEnd();
            }
        }
        else {
            if (start != null) {
                boardCopy.addPiece(endPosition, board.getPiece(start));
                boardCopy.addPiece(start, null);
            }
            else {
                boardCopy.addPiece(endPosition, board.getPiece(endPosition));
            }
        }
        //should be right? Check with copying
        //weird thing with two diff colors????
        TeamColor color = boardCopy.getPiece(endPosition).getTeamColor();
        //TeamColor kingColor = this.board.getPiece(kingPosition).getTeamColor();
        for (int i = 1; i < 9; i++) {
            for (int j =1; j <9; j++) 
            {
                ChessPosition pos = new ChessPosition(i, j);
                ChessPiece piece = boardCopy.getPiece(pos);
                if (piece != null && color != piece.getTeamColor()) {
                    Collection<ChessMove> moves = piece.pieceMoves(boardCopy, pos); //problem here?
                    for (ChessMove move : moves) {
                        if (move.getEndPosition().getColumn() == kingPosition.getColumn() && move.getEndPosition().getRow() == kingPosition.getRow()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    //maybe do the check here?
    public void makeMove(ChessMove move) throws InvalidMoveException 
    {
        if (move == null) {
            throw new InvalidMoveException();
        }
        ChessPosition starPosition = move.getStartPosition();
        if (starPosition == null || move.getEndPosition() == null) {
            throw new InvalidMoveException();
        }
        Collection<ChessMove> moves = validMoves(starPosition);
        if (moves == null) {
            throw new InvalidMoveException("no valid moves available");
        }
        if (this.board.getPiece(starPosition).getTeamColor() != this.getTeamTurn()) {
            throw new InvalidMoveException("not your turn");
        }
        if (moves.contains(move)) 
        {
            ChessPiece piece = board.getPiece(move.getStartPosition());
            if (move.getPromotionPiece() != null) {
                piece.setPieceType(move.getPromotionPiece());
            }
            TeamColor next = TeamColor.BLACK;
            if (piece.getTeamColor() == TeamColor.BLACK) {
                next = TeamColor.WHITE;
            }
            if (move.getIsCastle()) 
            {
                ChessPiece piece2 = this.board.getPiece(move.getCastleStart());
                this.board.addPiece(move.getCastleEnd(), piece2);
                this.board.addPiece(move.getCastleStart(), null);
                this.board.addPiece(move.getEndPosition(), piece);
                this.board.addPiece(move.getCastleStart(), null);
                this.board.getPiece(move.getCastleEnd()).setMoved(true);
                this.board.getPiece(move.getEndPosition()).setMoved(true);
            }
            else {
                if (piece.getPieceType() == PieceType.KING ) 
                {
                    if (piece.getTeamColor() == TeamColor.BLACK) 
                    {
                        ChessPosition posRook1 = new ChessPosition(8, 1);
                        ChessPosition posRook2 = new ChessPosition(8, 8);
                        if (move.getEndPosition().equals(new ChessPosition(8, 3)) && board.getPiece(posRook1) != null && board.getPiece(posRook1).getMoved() == false) {
                            if (board.getPiece(posRook1).getPieceType() == PieceType.ROOK) 
                            {
                                //maybe also check for middle I guess, although if it's in moves it should be fine
                                ChessPosition newRook = new ChessPosition(8, 4);
                                this.board.addPiece(move.getEndPosition(), piece);
                                this.board.addPiece(starPosition, null);
                                this.board.addPiece(newRook, board.getPiece(posRook1));
                                this.board.addPiece(posRook1, null);
                                this.board.getPiece(newRook).setMoved(true);
                                this.board.getPiece(move.getEndPosition()).setMoved(true);
                            }
                        }
                        else if (move.getEndPosition().equals(new ChessPosition(8, 7)) && board.getPiece(posRook2) != null && board.getPiece(posRook2).getMoved() == false) {
                            if (board.getPiece(posRook2).getPieceType() == PieceType.ROOK) 
                            {
                                //maybe also check for middle I guess, although if it's in moves it should be fine
                                ChessPosition newRook = new ChessPosition(8, 6);
                                this.board.addPiece(move.getEndPosition(), piece);
                                this.board.addPiece(starPosition, null);
                                this.board.addPiece(newRook, board.getPiece(posRook2));
                                this.board.addPiece(posRook2, null);
                                this.board.getPiece(newRook).setMoved(true);
                                this.board.getPiece(move.getEndPosition()).setMoved(true);
                            }
                        }
                        else {
                            this.board.addPiece(move.getEndPosition(), piece);
                            this.board.addPiece(starPosition, null);
                            this.board.getPiece(move.getEndPosition()).setMoved(true);
                        }
                    }
                    else {
                        ChessPosition posRook1 = new ChessPosition(1, 1);
                        ChessPosition posRook2 = new ChessPosition(1, 8);
                        if (move.getEndPosition().equals(new ChessPosition(1, 3)) && board.getPiece(posRook1) != null && board.getPiece(posRook1).getMoved() == false) {
                            if (board.getPiece(posRook1).getPieceType() == PieceType.ROOK) 
                            {
                                ChessPosition newRook = new ChessPosition(1, 4);
                                this.board.addPiece(move.getEndPosition(), piece);
                                this.board.addPiece(starPosition, null);
                                this.board.addPiece(newRook, board.getPiece(posRook1));
                                this.board.addPiece(posRook1, null);
                                this.board.getPiece(newRook).setMoved(true);
                                this.board.getPiece(move.getEndPosition()).setMoved(true);
                            }
                        }
                        else if (move.getEndPosition().equals(new ChessPosition(1, 7)) && board.getPiece(posRook2) != null && board.getPiece(posRook2).getMoved() == false) {
                            if (board.getPiece(posRook2).getPieceType() == PieceType.ROOK) 
                            {
                                ChessPosition newRook = new ChessPosition(1, 6);
                                this.board.addPiece(move.getEndPosition(), piece);
                                this.board.addPiece(starPosition, null);
                                this.board.addPiece(newRook, board.getPiece(posRook2));
                                this.board.addPiece(posRook2, null);
                                this.board.getPiece(newRook).setMoved(true);
                                this.board.getPiece(move.getEndPosition()).setMoved(true);
                            }
                        }
                        else {
                            this.board.addPiece(move.getEndPosition(), piece);
                            this.board.addPiece(starPosition, null);
                            this.board.getPiece(move.getEndPosition()).setMoved(true);
                        }
                    }
                    //check every place they could end? 
                    
                }
                
                else {
                    this.board.addPiece(starPosition, null);
                    this.board.addPiece(move.getEndPosition(), piece); 
                    this.board.getPiece(move.getEndPosition()).setMoved(true);
                }
            }
            
            setTeamTurn(next);
        }
        else {
            throw new InvalidMoveException();
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) 
    {
        //iterate through board??
        ChessPiece piece = findPiece(PieceType.KING, teamColor);
        //should have position now
        ChessPosition kingPosition= piece.getChessPosition(); //checking the king? 
        //maybe just check king?
        boolean check = isCheck(kingPosition, kingPosition, null);
        return check;
        //from position, check if any pieces are in range
        
        //check for team's king, then see if it could be attacked? How to find king?
    }

    public ChessPiece findPiece(PieceType type, TeamColor color) {
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPosition pos = new ChessPosition(i, j);
                ChessPiece piece = this.board.getPiece(pos);
                if (piece != null && piece.getPieceType() == type && piece.getTeamColor() == color) {
                    piece.setPosition(pos);
                    return piece; // Found the piece
                }
            }
        }
        return null; // Piece not found
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) 
    {
        ChessPiece king = findPiece(PieceType.KING, teamColor);
        boolean isCheck = isCheck(king.getChessPosition(), king.getChessPosition(), null);
        if (isCheck == false) {
            return false;
        }
        else 
        {
            for (int i = 1; i < 9; i++) {
                for (int j = 1; j < 9; j++)
                {
                    ChessPosition pos = new ChessPosition(i, j);
                    ChessPiece piece = this.board.getPiece(pos);
                    if (piece != null && piece.getTeamColor() == teamColor) 
                    {
                        Collection<ChessMove> moves = validMoves(pos);
                        if (moves != null && moves.size() != 0) {
                            return false;
                        }
                    }
                }
            }
        }
        //king in check, can't get out
        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) 
    {
        ChessPiece king = findPiece(PieceType.KING, teamColor);
        boolean isCheck = isCheck(king.getChessPosition(), king.getChessPosition(), null);
        if (isCheck == true) {
            return false;
        }
        if (getTeamTurn() != teamColor) {
            return false;
        }
        else 
        {
            for (int i = 1; i < 9; i++) {
                for (int j = 1; j < 9; j++)
                {
                    ChessPosition pos = new ChessPosition(i, j);
                    ChessPiece piece = this.board.getPiece(pos);
                    if (piece != null && piece.getTeamColor() == teamColor) 
                    {
                        Collection<ChessMove> moves = validMoves(pos);
                        if (moves.size() != 0) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) 
    {
        this.board = board;
        //iterate through board, find kings, set them 
        //this.blackKing.clear();
        //this.whiteKing.clear();

    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return this.board;
    }
    //HERE STOPPED

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ChessGame)) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return isCastle == chessGame.isCastle && Objects.equals(turn, chessGame.turn) && Objects.equals(board, chessGame.board) && Objects.equals(castleMove, chessGame.castleMove) && Objects.equals(blackKing, chessGame.blackKing) && Objects.equals(whiteKing, chessGame.whiteKing);
    }
    
}