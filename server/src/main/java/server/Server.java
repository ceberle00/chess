package server;

import dataaccess.*;
import service.*;
import spark.*;




public class Server 
{

    private SQLAuthDAO auth = new SQLAuthDAO();
    private SQLGameDAO game = new SQLGameDAO();
    private SQLUserDAO user = new SQLUserDAO();

    private SQLUserService userService = new SQLUserService(user, auth);
    private SystemService systemService = new SystemService(auth, game, user);
    private SQLGameService gameService = new SQLGameService(game, auth);

    private SystemHandler clearHandler = new SystemHandler(systemService);
    private UserHandler userHandler = new UserHandler(userService);
    private GameHandler gameHandler = new GameHandler(gameService);
    private DatabaseManager manager = new DatabaseManager();
    
    public Server() {
        this.userService = new SQLUserService(user, auth);
        this.userHandler = new UserHandler(userService);
        this.systemService = new SystemService(auth, game, user);
        this.clearHandler = new SystemHandler(systemService);
        this.gameService = new SQLGameService(game, auth);
        this.gameHandler = new GameHandler(gameService);
        try {
            manager.createDatabase();
        }
        catch (DataAccessException e) {
            System.out.println(e.getMessage());
        }
    }
    public int run(int desiredPort) {
        try {
            manager.createDatabase();
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
        }
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
