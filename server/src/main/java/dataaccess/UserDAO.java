package dataaccess;

import chess.model.*;
import java.util.Vector;
public class UserDAO implements MemoryUserDAO
{
    private Vector <UserData> users = new Vector<>();

    @Override
    public void clearUsers(){
        this.users.clear();
    }
    @Override
    public UserData getUser(String username)
    {
        for (int i = 0; i < this.users.size(); i++) {
            UserData data = this.users.elementAt(i);
            if (data.getUser() == username){
                return data;
            }
        } 
        return null;
    }
    @Override
    public UserData getUserPass(String username, String password) 
    {
        for (int i = 0; i < this.users.size(); i++) {
            UserData data = this.users.elementAt(i);
            if (data.getUser() == username){
                if(data.getPass() == password) {
                    return data;
                }
                else {
                    return null;
                }
                //else return null
            }
        } 
        return null;
    }
    @Override
    public void createUser(String username, String password, String email) throws DataAccessException
    {
        if (getUser(username) != null) {
            throw new DataAccessException("user already taken");
        }
        else {
            UserData data = new UserData(username, password, email);
            this.users.add(data);
        }
    }
}