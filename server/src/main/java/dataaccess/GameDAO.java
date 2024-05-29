package dataaccess;

//may need to seperate these out, idk what interfaces are
import java.util.*;
import chess.model.*;
import chess.*;
import chess.ChessGame.TeamColor;

public class GameDAO implements MemoryGameDAO
{
    //vector of games?
    private Map<Integer, GameData> games = new HashMap<Integer, GameData>(); //maybe make into map
    private int gameID = 0;
    
    @Override
    public void clearGames()
    {
        this.games.clear();
    }
    @Override
    public Map<Integer, GameData> getGames() {
        return this.games; //hopefully right, idk if just the vector is okay
    }
    @Override
    public GameData getGameName(String gameName) 
    {
        for (Map.Entry<Integer,GameData> entry : this.games.entrySet()) 
        {
            if (entry.getValue().Gamename().equals(gameName)) {
                return entry.getValue();
            }
        }
        return null;
    }
    @Override
    public int createGame(String gameName) 
    {
        this.gameID += 1;
        ChessGame game = new ChessGame();
        GameData data = new GameData(this.gameID, null, null, gameName, game);
        this.games.put(this.gameID, data);
        return this.gameID;
    }
    @Override
    public GameData checkGame(int gameId) 
    {
        return this.games.get(gameId);
    }
    @Override
    public void joinGame(int gameID, AuthData data, TeamColor color) 
    {
        GameData tempGame = this.games.get(gameID);
        if (color == TeamColor.BLACK) 
        {
            GameData newGame = new GameData(gameID, tempGame.whiteUsername(), data.getUser(), tempGame.Gamename(), tempGame.game());
            this.games.put(gameID, newGame);
        }
        else {
            GameData newGame = new GameData(gameID, data.getUser(), tempGame.blackUsername(), tempGame.Gamename(), tempGame.game());
            this.games.put(gameID, newGame);
        }

    }
}
