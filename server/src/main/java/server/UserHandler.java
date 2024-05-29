package server;

import service.RegisterRequest;
import service.RegisterResult;
import service.UserService;
import spark.*;
import com.google.gson.Gson;



public class UserHandler {

    private UserService service;

    public UserHandler(UserService user) {
        this.service = user;
    }

    public Object RegisterUser(Request request, Response result) 
    {
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
                //Exception e = new Exception("Error: already taken");
                RegisterResult res = new RegisterResult("Error: already taken");
                return new Gson().toJson(res);
            }

            service.createUser(reg.getUsername(), reg.getPassword(), reg.getEmail());
            String auth =service.createAuth(reg.getUsername());
            result.status(200);
            return new Gson().toJson(new RegisterResult(reg.getUsername(), auth));
        }
        catch (Exception e) 
        {
            result.status(500);
            return new Gson().toJson(e);
        }
    }
}
