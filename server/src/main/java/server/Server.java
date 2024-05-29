package server;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import service.GameService;
import service.SystemService;
import service.UserService;
import spark.*;




public class Server 
{

    private final AuthDAO auth = new AuthDAO();
    private final GameDAO game = new GameDAO();
    private final UserDAO user = new UserDAO();

    private final UserService userService;
    private final SystemService systemService;
    private final GameService gameService;

    private final SystemHandler clearHandler;
    private final UserHandler userHandler;
    private final GameHandler gameHandler;

    public Server() {
        this.userService = new UserService(user, auth);
        this.userHandler = new UserHandler(userService);
        this.systemService = new SystemService(auth, game, user);
        this.clearHandler = new SystemHandler(systemService);
        this.gameService = new GameService(game, auth);
        this.gameHandler = new GameHandler(gameService);
    }
    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");
        
        Spark.delete("/db", (req, res) ->
        (clearHandler.clear(req,  
       res)));
       Spark.post("/user", (req, res) ->
        (userHandler.RegisterUser(req,  
       res)));
       Spark.post("/session", (req, res) ->
        (userHandler.loginUser(req,  
       res)));
       Spark.delete("/session", (req, res) ->
        (userHandler.logoutUser(req,  
       res)));
       Spark.get("/game", (req, res) ->
        (gameHandler.listGames(req,  
       res)));
       Spark.post("/game", (req, res) ->
        (gameHandler.createGame(req,  
       res)));
       Spark.put("/game", (req, res) ->
       (gameHandler.joinGame(req,  
      res)));

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
