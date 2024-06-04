package dataaccess;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import com.google.gson.Gson;
import javax.xml.crypto.Data;
import java.util.Collection;
import chess.ChessGame;
import chess.ChessGame.TeamColor;
import chess.model.AuthData;
import chess.model.GameData;
import chess.model.UserData;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SQLGameDAO implements MemoryGameDAO {
    private Integer gameID = 0;
    @Override
    public void clearGames() throws DataAccessException
    {
        var message = "TRUNCATE gameData";
        executeUpdate(message);
    }
    @Override
    public void setID(Integer value) {
        this.gameID = value;
    }
    
    @Override
    public Collection<GameData> getGames() throws DataAccessException{
        ArrayList<GameData> result = new ArrayList<>();
        try (var conn = DatabaseManager.getConnection()) {
            String statement = "SELECT chessGame FROM gameData";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        result.add(readGame(rs));
                    }
                }
            }
            return (result);
        }
        catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
    private GameData readGame(ResultSet rs) throws SQLException {
        var gameJson = rs.getString("chessGame");
        ChessGame game = new Gson().fromJson(gameJson, ChessGame.class);
        Integer id = rs.getInt("gameID");
        String bUser = rs.getString("blackUsername");
        String wUser = rs.getString("whiteUsername");
        String gameName = rs.getString("gameName");
        return new GameData(id, bUser, wUser, gameName, game);
    }
    @Override
    public GameData getGameName(String gameName) throws DataAccessException
    {
        try (var conn = DatabaseManager.getConnection()) {
            String statement = "SELECT * FROM gameData WHERE gameName=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, gameName);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Integer gameId = rs.getInt("gameID");
                        String whiteUser = rs.getString("whiteUsername");
                        String blkUser = rs.getString("blackUsername");
                        var gameJson = rs.getString("chessGame");
                        ChessGame game = new Gson().fromJson(gameJson, ChessGame.class);
                        return new GameData(gameId, whiteUser, blkUser, gameName, game);

                    }
                    else {
                        return null;
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
    @Override
    public Integer createGame(String gameName) throws DataAccessException
    {

        if (this.gameID == null) {
            this.gameID = 0;
        }
        this.gameID += 1;
        Integer newValue = this.gameID;
        ChessGame game = new ChessGame();
        var Statement = "INSERT INTO userData (gameID, whiteUsername, blackUsername, gameName, chessGame) VALUES (?,?,?, ?,?)";
        executeUpdate(Statement, newValue, NULL, NULL, gameName, game);
        return newValue;
    }
    @Override
    public GameData checkGame(int gameId) throws DataAccessException
    {
        try (var conn = DatabaseManager.getConnection()) {
            String statement = "SELECT * FROM gameData WHERE gameID=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readGame(rs);
                    }
                    else {
                        return null;
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
    @Override
    public void joinGame(int gameID, AuthData data, TeamColor color) throws DataAccessException
    {
        GameData tempGame = checkGame(gameID);
        if (color == TeamColor.BLACK && tempGame.blackUsername() == null) 
        {
            //GameData newGame = new GameData(gameID, tempGame.whiteUsername(), data.getUser(), tempGame.gameName(), tempGame.game());
            var Statement = "UPDATE gameData SET blackUsername = ? WHERE gameID = ?";
            executeUpdate(Statement, data.username(), gameID);
        }
        else if (color == TeamColor.WHITE && tempGame.whiteUsername() == null)
        {
            var Statement = "UPDATE gameData SET whiteUsername = ? WHERE gameID = ?";
            executeUpdate(Statement, data.username(), gameID);
        }

    }
    private void executeUpdate(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    if (param instanceof String p) ps.setString(i+1, p);
                    else if (param instanceof Integer p) ps.setInt(i +1, p);
                    else if (param instanceof AuthData p) ps.setString(i+1, p.toString());
                    else if (param == null) ps.setNull(i+1, NULL);
                }
                ps.executeUpdate();

                var rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(String.format("unable to update database: %s, %s", statement, e.getMessage()));
        }
    }
}
