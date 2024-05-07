package chess;
import java.util.Objects;
//this should be done? Hopefully
/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ChessPosition)) {
            return false;
        }
        ChessPosition chessPosition = (ChessPosition) o;
        return row == chessPosition.row && col == chessPosition.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "{" +
            " row='" + getRow() + "'" +
            ", col='" + getColumn() + "'" +
            "}";
    }
    private int row = 0;
    private int col = 0;
    public ChessPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return this.row;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return this.col;
        //throw new RuntimeException("Not implemented");
    }
}
