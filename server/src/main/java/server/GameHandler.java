package server;
import chess.model.*;
import spark.*;
import com.google.gson.Gson;
import service.GameService;
import service.ListGamesResult;

import java.util.ArrayList;

public class GameHandler {
    private GameService service;

    public GameHandler(GameService service) {
        this.service = service;
    }

    public Object listGames(Request request, Response response) throws Exception{
        boolean which = false;
        try {
            String authString = request.headers("authorization");
            service.valiAuthData(authString);
            ArrayList<GameData> listGames = service.listGames(authString);
            ListGamesResult res = new ListGamesResult(listGames);
            response.status(200);
            return new Gson().toJson(res);

        }catch (Exception e) {
            ListGamesResult res = new ListGamesResult(e.getMessage());
            if (which) {
                response.status(401);
            }
            else {
                response.status(500);
            }
            return new Gson().toJson(res);
        }
    }
}
