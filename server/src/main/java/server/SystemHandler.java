package server;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import service.SystemService;
import spark.*;
import com.google.gson.Gson;

public class SystemHandler 
{
    private SystemService service;
    public SystemHandler(SystemService system) {
        this.service = system;
    }
    public Object clear(Request req, Response res) {
        try
        {
            service.clear();
            res.status(200);
            return new Gson().toJson(null);
        }
        catch(Exception e) {
            res.status(500);
            return new Gson().toJson(e.getMessage());
        }
    }
}
