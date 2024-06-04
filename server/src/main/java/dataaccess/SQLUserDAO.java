package dataaccess;

import java.sql.SQLException;

import chess.model.AuthData;
import chess.model.UserData;

public class SQLUserDAO implements MemoryUserDAO 
{
    @Override
    public void clearUsers() throws DataAccessException{
        var message = "TRUNCATE userData";
        executeUpdate(message);
    }
    @Override
    public UserData getUser(String username)
    {
        for (UserData user : this.users) {
            if (user.getUser().equals(username)){
                return user;
            }
        } 
        return null;
    }
    @Override
    public UserData getUserPass(String username, String password)
    {
        for (int i = 0; i < this.users.size(); i++) {
            UserData data = this.users.get(i);
            if (data.getUser().equals(username)){
                if(data.getPass().equals(password)) {
                    return data;
                }
                else {
                    return null;
                }
                
            }
        } 
        return null;
    }
    @Override
    public void createUser(String username, String password, String email) throws Exception
    {
        UserData data = new UserData(username, password, email);
        if (getUser(username) != null) {
            throw new Exception("Error: already taken");
        }
        else {
            this.users.add(data);
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
