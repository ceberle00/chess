package dataaccess;

import chess.model.*;

import java.util.ArrayList;

public class UserDAO
{
    private ArrayList <UserData> users;

    public UserDAO() {
        this.users = new ArrayList<>();
    }
    
    public void clearUsers(){
        this.users.clear();
    }
    public UserData getUser(String username)
    {
        for (UserData user : this.users) {
            if (user.getUser().equals(username)){
                return user;
            }
        } 
        return null;
    }
    
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