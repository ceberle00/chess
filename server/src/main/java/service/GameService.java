package service;

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
        if (data == null) {
            throw new DataAccessException("authorization failed");
        }
        else {
            return data;
        }

    }
    public void joinGame(int gameID, String gameName, TeamColor color, String authToken) throws DataAccessException
    {
        AuthData auth = valiAuthData(authToken);
        GameData data = this.game.checkGame(gameID);
        if (data == null) {
            throw new DataAccessException("game does not exist");
        }
        if (color == TeamColor.BLACK) 
        {
            if (data.blackUsername() == null) {
                //then we can add
                this.game.joinGame(gameID, auth, color);

            }
            else {
                throw new DataAccessException("color already taken");
            }
        }
        else if (color == TeamColor.WHITE){
            if (data.whiteUsername() == null) {
                this.game.joinGame(gameID, auth, color);
            }
            else {
                throw new DataAccessException("color already taken");
            }
        }
        else {
            throw new DataAccessException("Cannot join null color");
        }
    }
}
