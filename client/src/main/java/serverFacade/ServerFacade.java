package serverFacade;

import java.util.ArrayList;
import chess.model.*;
import chess.model.requests.*;

public class ServerFacade {

    //pre-login
    public AuthData login(String username, String password) {
        LoginRequest request = new LoginRequest(username, password);
        return null;
    }
    public AuthData register(String username, String password, String email) {
        UserData data = new UserData(username, password, email);

        return null;
    }
    public void quit() {

    }
    public void help()
    {

    }
    //post login, help and quit included in both
    public void logout(String authToken) 
    {

    }
    public ArrayList<GameData> listGames() {
        return null;
    }
    public GameData createGame(String name) {
        return null;
    }
    public void joinGame(String color, Integer gameID, String authToken) {
        
    }

}
