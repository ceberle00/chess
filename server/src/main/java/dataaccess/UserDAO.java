package dataaccess;

import chess.model.*;
import java.util.Vector;
public class UserDAO 
{
    private Vector <UserData> users = new Vector<>();

    public void clearUsers(){
        this.users.clear();
    }
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
    public void createUser(String username, String password, String email) 
    {
        UserData data = new UserData(username, password, email);
        this.users.add(data);
    }
}