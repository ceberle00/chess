package service;

import java.util.ArrayList;

import chess.ChessGame.TeamColor;
import chess.model.AuthData;
import chess.model.GameData;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;


public class GameService {

    private GameDAO game;
    private AuthDAO auth;
    public GameService() {
    }
    public GameDAO getGame() {
        return this.game;
    }
    public GameService(GameDAO game, AuthDAO auth) {
        this.game = game;
        this.auth = auth;
    }
    public AuthData valiAuthData(String authToken) throws DataAccessException
    {
        AuthData data = auth.getAuth(authToken);
        if (data == null) 
        {   
            this.game.setID(null);
            throw new DataAccessException("Error: unauthorized");
        }
        else {
            return data;
        }

    }
    public void joinGame(int gameID, TeamColor color, String authToken) throws Exception
    {
        AuthData auth = valiAuthData(authToken);
        GameData data = this.game.checkGame(gameID);
        if (data == null) {
            throw new Exception("Error: bad request");
        }
        if (color == null) {
            throw new Exception("Error: bad request");
        }
        if (color == TeamColor.BLACK) 
        {
            if (data.blackUsername() == null) {
                //then we can add
                this.game.joinGame(gameID, auth, color);

            }
            else {
                throw new Exception("Error: already taken");
            }
        }
        else if (color == TeamColor.WHITE){
            if (data.whiteUsername() == null) {
                this.game.joinGame(gameID, auth, color);
            }
            else {
                throw new Exception("Error: already taken");
            }
        }
        else {
            throw new Exception("Error: bad request");
        }
    }
    public Integer createGame(String authToken, String gameName) throws DataAccessException
    {
        valiAuthData(authToken);
        if (this.game.getGameName(gameName) != null) 
        {
            throw new DataAccessException("Game name already taken");
        }
        return this.game.createGame(gameName);
        //will pass back gameID later for handlers
    }
    public ArrayList<GameData> listGames(String authToken) throws DataAccessException
    {
        valiAuthData(authToken);
        ArrayList<GameData> games = new ArrayList<>(this.game.getGames().values());
        return games;
    }
}
