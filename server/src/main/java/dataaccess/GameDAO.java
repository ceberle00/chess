package dataaccess;

//may need to seperate these out, idk what interfaces are
import java.util.Vector;
import chess.model.*;
import chess.*;

public class GameDAO implements MemoryGameDAO
{
    //vector of games?
    private Vector<GameData> games = new Vector<>();
    private int gameID = 0;
    
    @Override
    public void clearGames()
    {
        this.games.clear();
    }
    @Override
    public Vector<GameData> getGames() {
        return this.games; //hopefully right, idk if just the vector is okay
    }
    @Override
    public GameData getGameName(String gameName) 
    {
        for (int i = 0; i < this.games.size(); i++) 
        {
            if (this.games.get(i).getGameName() == gameName) {
                return this.games.get(i);
            }
        }
        return null;
    }
    @Override
    public int createGame(String gameName) 
    {
        this.gameID += 1;
        ChessGame game = new ChessGame();
        GameData data = new GameData(this.gameID, gameName, null, null, game);
        this.games.add(data);
        return this.gameID;
        //maybe return gameID
    }
    @Override
    public GameData checkGame(int gameId) 
    {
        return null;
    }
}
