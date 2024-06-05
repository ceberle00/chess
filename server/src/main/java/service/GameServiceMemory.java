package service;

import java.util.Collection;

import chess.ChessGame.TeamColor;
import chess.model.AuthData;
import chess.model.GameData;
import dataaccess.DataAccessException;

public interface GameServiceMemory {
    public AuthData valiAuthData(String authToken) throws DataAccessException;
    public void joinGame(int gameID, TeamColor color, String authToken) throws Exception;
    public Integer createGame(String authToken, String gameName) throws Exception;
    public Collection<GameData> listGames(String authToken) throws DataAccessException;
    
}