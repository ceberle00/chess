package dataaccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import chess.model.UserData;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class SQLUserDAO implements MemoryUserDAO 
{
    @Override
    public void clearUsers() throws DataAccessException{
        var message = "TRUNCATE userData";
        executeUpdate(message);
    }
    @Override
    public UserData getUser(String username) throws DataAccessException
    {
        try (var conn = DatabaseManager.getConnection()) {
            String statement = "SELECT * FROM userData WHERE username=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String user = rs.getString("username");
                        String password = rs.getString("password");
                        String email = rs.getString("email");
                        return new UserData(user, password, email);
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
    public UserData getUserPass(String username, String password) throws DataAccessException
    {
        try (var conn = DatabaseManager.getConnection()) {
            String statement = "SELECT * FROM userData WHERE username=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String storedHashedPassword = rs.getString("password");
                        if (BCrypt.checkpw(password, storedHashedPassword)) {
                            String user = rs.getString("username");
                            String pass = rs.getString("password");
                            String email = rs.getString("email");
                            return new UserData(user, pass, email);
                        }
                        else {
                            return null;
                        }
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
    public void createUser(String username, String password, String email) throws DataAccessException
    {
        if (getUser(username) != null) {
            throw new DataAccessException("Error: already taken");
        }
        var Statement = "INSERT INTO userData (username, password, email) VALUES (?,?,?)";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        executeUpdate(Statement, username, hashedPassword, email);
    }
    private void executeUpdate(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    if (param instanceof String p) ps.setString(i+1, p);
                    else if (param instanceof Integer p) ps.setInt(i +1, p);
                    else if (param instanceof UserData p) ps.setString(i+1, p.toString());
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
