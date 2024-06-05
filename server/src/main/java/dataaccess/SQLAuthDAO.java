package dataaccess;

import chess.model.AuthData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class SQLAuthDAO implements MemoryAuthDAO
{
    @Override
    public void clearAuth() throws DataAccessException
    {
        var message = "TRUNCATE authdata";
        executeUpdate(message);
    }
    @Override
    public AuthData getAuth(String authData) throws DataAccessException
    {
        try (var conn = DatabaseManager.getConnection()) {
            String statement = "SELECT * FROM authdata WHERE token=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, authData);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String token = rs.getString("token");
                        String username = rs.getString("username");
                        return new AuthData(token, username);
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
    public String createAuth(String user) throws DataAccessException{
        String authValue = UUID.randomUUID().toString();
        AuthData data = new AuthData(authValue, user);
        var statement = "INSERT INTO authdata (token, username) VALUES (?, ?)";
        executeUpdate(statement, data.getAuth(), data.getUser());
        return authValue;
    }
    @Override
    public void deleteSession(String authToken) throws DataAccessException
    {
        var statement = "DELETE FROM authdata WHERE token= ?";
        executeUpdate(statement, authToken);
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
