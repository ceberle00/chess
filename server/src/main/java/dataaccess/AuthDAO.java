package dataaccess;

import chess.model.*;
import java.util.Vector;
import java.util.UUID;

public class AuthDAO
{
    private Vector<AuthData> auth = new Vector<>();

    
    public void clearAuth()
    {
        this.auth.clear();
    }
    public AuthData getAuth(String authData) 
    {
        for (int i = 0; i < this.auth.size(); i++) 
        {
            if (this.auth.elementAt(i).getAuth().equals(authData)) {
                return this.auth.elementAt(i);
            }
        }
        return null;
    }
    public String createAuth(String username) {
        String authValue = UUID.randomUUID().toString();
        AuthData data = new AuthData(authValue, username);
        this.auth.add(data);
        return authValue;
    }
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
}
