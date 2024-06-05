package service;

import java.util.ArrayList;
import java.util.Collection;

import chess.ChessGame.TeamColor;
import chess.model.AuthData;
import chess.model.GameData;
import dataaccess.*;

public class SQLGameService{
    
    private SQLGameDAO game = new SQLGameDAO();
    private SQLAuthDAO auth = new SQLAuthDAO();

    public SQLGameService() {
    }
    public SQLGameDAO getGame() {
        System.out.println("In get Game");
        return this.game;
    }
    public SQLGameService(SQLGameDAO game, SQLAuthDAO auth) {
        this.game = game;
        this.auth = auth;
    }
    public AuthData valiAuthData(String authToken) throws DataAccessException
    {
        if (auth == null) {
            throw new DataAccessException("Error: unauthorized");
        }
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
    public Integer createGame(String authToken, String gameName) throws Exception
    {
        if (this.game.getGameName(gameName) != null) 
        {
            throw new Exception("Error: already taken");
        }
        return this.game.createGame(gameName);
    }
    public Collection<GameData> listGames(String authToken) throws DataAccessException
    {
        valiAuthData(authToken);
        Collection<GameData> games = new ArrayList<>(this.game.getGames());
        return games;
    }
}