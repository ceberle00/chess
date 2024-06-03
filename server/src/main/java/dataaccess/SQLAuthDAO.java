package dataaccess;

import chess.model.AuthData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.*;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class SQLAuthDAO implements MemoryAuthDAO
{
    private Vector<AuthData> auth = new Vector<>();

    @Override
    public void clearAuth() throws DataAccessException
    {
        String message = "TRUNCATE authData";
        executeUpdate(message);
    }
    @Override
    public AuthData getAuth(String authData) 
    {
        String message = "";
        for (int i = 0; i < this.auth.size(); i++) 
        {
            if (this.auth.elementAt(i).getAuth().equals(authData)) {
                return this.auth.elementAt(i);
            }
        }
        return null;
    }
    @Override
    public String createAuth(String username) {
        String authValue = UUID.randomUUID().toString();
        AuthData data = new AuthData(authValue, username);
        this.auth.add(data);
        return authValue;
    }
    @Override
    public void deleteSession(String authToken) 
    {
        for (int i = 0; i < this.auth.size(); i++) 
        {
            if (this.auth.elementAt(i).getAuth().equals(authToken)) {
                this.auth.remove(i);
                i = i-1;
            }
        }
    }

    private void executeUpdate(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param instanceof Integer p) ps.setInt(i + 1, p);
                    else if (param instanceof AuthData p) ps.setString(i + 1, p.toString());
                    else if (param == null) ps.setNull(i + 1, NULL);
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
