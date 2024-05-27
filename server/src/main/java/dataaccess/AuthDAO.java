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
            if (this.auth.elementAt(i).getAuth() == authData) {
                return this.auth.elementAt(i);
            }
        }
        return null;
    }
    public void createAuth(String username) {
        String auth = UUID.randomUUID().toString();
        AuthData data = new AuthData(auth, username);
        this.auth.add(data);
    }
    public void deleteSession(String authToken) 
    {
        for (int i = 0; i < this.auth.size(); i++) 
        {
            if (this.auth.elementAt(i).getAuth() == authToken) {
                this.auth.remove(i);
                i = i-1;
            }
        }
    }
}
