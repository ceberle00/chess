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
    public void createAuth(String username) {
        String auth = UUID.randomUUID().toString();
        AuthData data = new AuthData(auth, username);
        this.auth.add(data);
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
