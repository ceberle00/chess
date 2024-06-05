package dataaccess;

import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.Gson;
import java.util.Collection;
import chess.ChessGame;
import chess.ChessGame.TeamColor;
import chess.model.AuthData;
import chess.model.GameData;

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
            String statement = "SELECT * FROM gameData";
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
        try {
            Integer id = rs.getInt("gameID");
            String wUser = rs.getString("whiteUsername");
            String bUser = rs.getString("blackUsername");
            String gameName = rs.getString("gameName");
            String jsonGame = rs.getString("games");
            ChessGame game = new Gson().fromJson(jsonGame, ChessGame.class);
            return new GameData(id, wUser, bUser, gameName, game);
        }
        catch (SQLException e) {
            System.err.println("Error deserializing ChessGame: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
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
                        var gameJson = rs.getString("games");
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
        String jsonGame = new Gson().toJson(game);
        var statement = "INSERT INTO gameData (gameID, whiteUsername, blackUsername, gameName, games) VALUES (?,?,?,?,?)";
        executeUpdate(statement, newValue, null, null, gameName, jsonGame);
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
            System.err.println("Error deserializing ChessGame: " + e.getMessage());
            throw new DataAccessException(e.getMessage());
        }
    }
    @Override
    public void joinGame(int gameID, AuthData data, TeamColor color) throws DataAccessException
    {
        GameData tempGame = checkGame(gameID);
        if (color == TeamColor.BLACK && tempGame.blackUsername() == null) 
        {
            var statement = "UPDATE gameData SET blackUsername = ? WHERE gameID = ?";
            executeUpdate(statement, data.username(), gameID);
        }
        else if (color == TeamColor.WHITE && tempGame.whiteUsername() == null)
        {
            var statement = "UPDATE gameData SET whiteUsername = ? WHERE gameID = ?";
            executeUpdate(statement, data.username(), gameID);
        }

    }
    private void executeUpdate(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param instanceof Integer p) ps.setInt(i + 1, p);
                    else if (param == null) ps.setNull(i + 1, NULL);
                    else ps.setString(i + 1, param.toString()); // Treat all other objects as strings
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