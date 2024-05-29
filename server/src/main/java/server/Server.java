package server;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
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

    private final SystemHandler clearHandler;
    private final UserHandler userHandler;

    public Server() {
        this.userService = new UserService(user, auth);
        this.userHandler = new UserHandler(userService);
        this.systemService = new SystemService(auth, game, user);
        this.clearHandler = new SystemHandler(systemService);
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
       

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
