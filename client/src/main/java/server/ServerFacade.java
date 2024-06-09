package server;
import java.util.ArrayList;

import chess.model.*;
public class ServerFacade {

    //pre-login
    public AuthData login(String username, String password) {
        return null;
    }
    public AuthData register(String username, String password, String email) {
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
