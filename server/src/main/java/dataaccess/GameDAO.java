package dataaccess;

//may need to seperate these out, idk what interfaces are
import java.util.*;
import chess.model.*;
import chess.*;
import chess.ChessGame.TeamColor;

public class GameDAO
{
    //vector of games?
    private Map<Integer, GameData> games = new HashMap<Integer, GameData>(); //maybe make into map
    private Integer gameID = 0;
    
    public void clearGames()
    {
        this.games.clear();
    }
    public void setID(Integer value) {
        this.gameID = value;
    }
    public Collection<GameData> getGames() {
        return this.games.values(); //hopefully right, idk if just the vector is okay
    }
    public GameData getGameName(String gameName) 
    {
        for (Map.Entry<Integer,GameData> entry : this.games.entrySet()) 
        {
            if (entry.getValue().gameName().equals(gameName)) {
                return entry.getValue();
            }
        }
        return null;
    }
    public Integer createGame(String gameName) 
    {
        if (this.gameID == null) {
            this.gameID = 0;
        }
        this.gameID += 1;
        Integer newValue = this.gameID;
        ChessGame game = new ChessGame();
        GameData data = new GameData(newValue, null, null, gameName, game);
        this.games.put(newValue, data);
        System.out.println(newValue);
        return newValue;
    }
    public GameData checkGame(int gameId) 
    {
        return this.games.get(gameId);
    }
    public void joinGame(int gameID, AuthData data, TeamColor color) 
    {
        GameData tempGame = this.games.get(gameID);
        if (color == TeamColor.BLACK) 
        {
            GameData newGame = new GameData(gameID, tempGame.whiteUsername(), data.getUser(), tempGame.gameName(), tempGame.game());
            this.games.put(gameID, newGame);
        }
        else {
            GameData newGame = new GameData(gameID, data.getUser(), tempGame.blackUsername(), tempGame.gameName(), tempGame.game());
            this.games.put(gameID, newGame);
        }

    }
}
