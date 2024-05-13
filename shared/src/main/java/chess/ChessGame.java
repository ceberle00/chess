package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.*; 
/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ChessGame)) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return Objects.equals(this, chessGame);
    }

    //maybe will need to change once variables added
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "{" +
            "}";
    }
    private TeamColor turn = TeamColor.WHITE;
    private ChessBoard board;
    private Map<ChessPiece, ChessPosition> blackKing = new HashMap<>();
    private Map<ChessPiece, ChessPosition> whiteKing = new HashMap<>();
    public ChessGame() {
        this.board.resetBoard();
        //set kings for easy access
        ChessPosition white = new ChessPosition(1, 4); //should be the default board, unless otherwise specificed
        ChessPosition black = new ChessPosition(7, 4);
        ChessPiece wh = this.board.getPiece(white);
        ChessPiece bl = this.board.getPiece(black);
        blackKing.put(bl, black);
        whiteKing.put(wh, white);


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
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        ChessPiece piece = board.getPiece(startPosition);
        moves = piece.pieceMoves(board, startPosition);
        int row = startPosition.getRow();
        int column = startPosition.getColumn();
        
        //just checking for one piece tho?
        //maybe check start position to see if king is nearby//moving will affect it?
        //iterate through moves, check each one to see if it leaves the king open?
        return moves; //check if any moves leave king open
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
        ChessPosition starPosition = move.getStartPosition();
        Collection<ChessMove> moves = validMoves(starPosition);
        moves.size();
        //check if that move is in moves? maybe?
        //not sure how the different objects will work with "contains"
        if (moves.contains(move)) {
            ChessPiece piece = board.getPiece(move.getStartPosition());
            this.board.addPiece(starPosition, null);
            this.board.addPiece(move.getEndPosition(), piece);
            //not sure if this will work lol
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
        //check for team's king, then see if it could be attacked? How to find king?
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
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
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) 
    {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return this.board;
    }
}
