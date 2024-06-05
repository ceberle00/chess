package server;

import dataaccess.*;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import service.GameService;
import service.SystemService;
import service.*;
import spark.*;




public class Server 
{

    //EDIT, WILL HAVE ERRORS

    private SQLAuthDAO auth = new SQLAuthDAO();
    private SQLGameDAO game = new SQLGameDAO();
    private SQLUserDAO user = new SQLUserDAO();

    private SQLUserService userService;
    private SystemService systemService;
    private SQLGameService gameService;

    private SystemHandler clearHandler;
    private UserHandler userHandler;
    private GameHandler gameHandler;

    public Server() {
        this.userService = new SQLUserService(user, auth);
        this.userHandler = new UserHandler(userService);
        this.systemService = new SystemService(auth, game, user);
        this.clearHandler = new SystemHandler(systemService);
        this.gameService = new SQLGameService(game, auth);
        this.gameHandler = new GameHandler(gameService);
    }
    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");
        
        Spark.delete("/db", (req, res) ->
        (clearHandler.clear(req,  
       res)));
       Spark.post("/user", (req, res) ->
        (userHandler.registerUser(req,  
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
