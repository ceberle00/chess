package serverFacade;

import java.util.ArrayList;

import org.glassfish.grizzly.utils.Exceptions;

import chess.model.*;
import chess.model.requests.*;
public class ServerFacade {
    //add part for port number
    private HttpHandler handler;
    public ServerFacade(int serverPort) 
    {
        String urlString = "http://localhost:";
        urlString += String.valueOf(serverPort);
        handler = new HttpHandler(urlString);
    }
    //pre-login
    public AuthData login(String username, String password) throws Exception{
        LoginRequest request = new LoginRequest(username, password);
        return handler.login(request);
    }
    public AuthData register(String username, String password, String email) throws Exception {
        UserData data = new UserData(username, password, email);
        return handler.register(data);
        //return null;
    }
    public void quit() throws Exception {
        handler.quit();
    }
    public void logout(String authToken) throws Exception
    {
        handler.logout(authToken);
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
