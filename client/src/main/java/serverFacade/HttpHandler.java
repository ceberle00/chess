package serverFacade;

import java.io.InputStream;
import java.io.InputStreamReader;
import chess.model.requests.LoginRequest;
import chess.model.*;
import com.google.gson.Gson;
import java.net.*;

public class HttpHandler {
    String url = "";
    public HttpHandler(String serverFacadeUrl) {
        this.url = serverFacadeUrl;
    }
    public AuthData register(UserData data) throws Exception {
        URI uri = new URI(url + "/user");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        try (var outputStream = http.getOutputStream()) {
            var jsonBody = new Gson().toJson(data);
            outputStream.write(jsonBody.getBytes());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("error at getOutputStream");
        }

        try (InputStream respBody = http.getInputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(respBody);
            AuthData responseBody = new Gson().fromJson(inputStreamReader, AuthData.class);
            return responseBody;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("Error reading response");
        }
    }
    public AuthData login(LoginRequest request) throws Exception{
        URI uri = new URI(url + "/session");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        try (var outputStream = http.getOutputStream()) {
            var jsonBody = new Gson().toJson(request);
            outputStream.write(jsonBody.getBytes());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("error at getOutputStream");
        }
        try (InputStream respBody = http.getInputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(respBody);
            AuthData responseBody = new Gson().fromJson(inputStreamReader, AuthData.class);
            return responseBody;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("Error reading response");
        }
    }
    public void quit() throws Exception {
        URI uri = new URI(url + "/db");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("DELETE");
    }
}
