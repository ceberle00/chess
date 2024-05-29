package dataaccess;

import chess.model.*;

import java.util.ArrayList;
import java.util.Vector;
public class UserDAO implements MemoryUserDAO
{
    private ArrayList <UserData> users;

    public UserDAO() {
        this.users = new ArrayList<>();
    }
    @Override
    public void clearUsers(){
        this.users.clear();
    }
    @Override
    public UserData getUser(String username)
    {
        for (UserData user : this.users) {
            if (user.getUser() == username){
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
            if (data.getUser() == username){
                if(data.getPass() == password) {
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
}