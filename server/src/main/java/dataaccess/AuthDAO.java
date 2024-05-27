package dataaccess;

import chess.model.*;
import java.util.Vector;
import java.util.UUID;

public class AuthDAO implements MemoryAuthDAO
{
    private Vector<AuthData> auth = new Vector<>();

    @Override
    public void clearAuth()
    {
        this.auth.clear();
    }
    @Override
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
            if (this.auth.elementAt(i).getAuth() == authToken) {
                this.auth.remove(i);
                i = i-1;
            }
        }
    }
}
