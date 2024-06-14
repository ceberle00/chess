package server;

import service.*;
import chess.model.requests.*;
import chess.model.results.*;
import spark.*;

import com.google.gson.Gson;



public class UserHandler {

    private SQLUserService service;

    public UserHandler(SQLUserService user) {
        this.service = user;
    }

    public Object registerUser(Request request, Response result) 
    {
        System.out.println("In userhandler");
        try {
            RegisterRequest reg = new Gson().fromJson(request.body(), RegisterRequest.class);
            if (reg.getEmail() == null || reg.getPassword() == null || reg.getUsername() == null) {
                result.status(400);
                RegisterResult res = new RegisterResult("Error: bad response");
                //Exception e = new Exception("Error: bad response");
                return new Gson().toJson(res);
            }
            if (service.getUser(reg.getUsername()) != null) 
            {
                result.status(403);
                RegisterResult res = new RegisterResult("Error: already taken");
                return new Gson().toJson(res);
            }
            System.out.println("After ifs");
            String auth = service.createUser(reg.getUsername(), reg.getPassword(), reg.getEmail()).getAuth();
            System.out.println(auth);
            result.status(200);
            return new Gson().toJson(new RegisterResult(reg.getUsername(), auth));
        }
        catch (Exception e) 
        {
            result.status(500);
            return new Gson().toJson(e);
        }
    }
    public Object loginUser(Request request, Response response) throws Exception{
        try {
            LoginRequest log = new Gson().fromJson(request.body(), LoginRequest.class);
            String auth = service.loginUser(log.getUsername(), log.getPassword());
            response.status(200);
            return new Gson().toJson(new RegisterResult(log.getUsername(), auth));
        }catch(Exception e) {
            response.status(401);
            LoginResult newRes = new LoginResult(e.getMessage());
            return new Gson().toJson(newRes);
        }
    }
    public Object logoutUser(Request request, Response response) throws Exception 
    {
        try {
            String authToken = request.headers("authorization");
            service.logoutUser(authToken);
            response.status(200);
            return new Gson().toJson(null);
        }
        catch(Exception e) {
            LogoutResult log = new LogoutResult(e.getMessage());
            response.status(401);
            return new Gson().toJson(log);
        }
    }

}
