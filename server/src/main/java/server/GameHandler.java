package server;
import chess.model.*;
import spark.*;
import com.google.gson.Gson;
import java.util.Map;
import service.AddGameRequest;
import service.AddGameResult;
import service.GameService;
import service.JoinGameRequest;
import service.ListGamesResult;

import java.util.ArrayList;

public class GameHandler {

    private GameService service;

    public GameHandler(GameService service) {
        this.service = service;
    }

    public Object listGames(Request request, Response response) throws Exception{
        boolean failedAuth = false;
        try {
            String authString = request.headers("authorization");
            failedAuth = true;
            service.valiAuthData(authString);
            ArrayList<GameData> listGames = service.listGames(authString);
            ListGamesResult res = new ListGamesResult(listGames);
            response.status(200);
            return new Gson().toJson(res);

        }catch (Exception e) {
            ListGamesResult res = new ListGamesResult(e.getMessage());
            if (failedAuth) {
                response.status(401);
            }
            else {
                response.status(500);
            }
            return new Gson().toJson(res);
        }
    }
    public Object createGame(Request request, Response response) throws Exception {
        boolean failedAuth = false;
        boolean failedPast = false;
        try {
            AddGameRequest req = new Gson().fromJson(request.body(), AddGameRequest.class);
            String authString = request.headers("authorization");
            failedAuth = true;
            service.valiAuthData(authString);
            failedPast = true;
            Integer id = service.createGame(authString, req.getName());
            AddGameResult res = new AddGameResult(id);
            response.status(200);
            return new Gson().toJson(res);

        }catch (Exception e) {
            AddGameResult res = new AddGameResult(e.getMessage());
            if (failedAuth) {
                if (failedPast) {
                    response.status(400);
                }
                else {
                    response.status(401);
                }
            }
            else {
                response.status(500);
            }
            return new Gson().toJson(res);
        }
    }
    public Object joinGame(Request request, Response response) throws Exception {
        try {
            String authString = request.headers("authorization");
            service.valiAuthData(authString);
            //String requestBody = request.body();
            //System.out.println("Received request body: " + requestBody);
            JoinGameRequest joinRequest = new Gson().fromJson(request.body(), JoinGameRequest.class);
            service.joinGame(joinRequest.getId(), joinRequest.getColor(), authString);
            response.status(200);
            return new Gson().toJson(null);

        }catch (Exception e) {
            if (e.getMessage().equals("Error: already taken")) {
                response.status(403);
            }
            else if (e.getMessage().equals("Error: bad request")) {
                response.status(400);
            }
            else if (e.getMessage().equals("Error: unauthorized")) {
                response.status(401);
            }
            else {
                response.status(500);
            }
            return new Gson().toJson(Map.of("message", e.getMessage()));
        }
    }
}
