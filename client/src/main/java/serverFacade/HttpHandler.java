package serverFacade;

import java.io.InputStream;
import java.io.InputStreamReader;

import chess.model.requests.AddGameRequest;
import chess.model.requests.LoginRequest;
import chess.*;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import chess.model.*;
import com.google.gson.Gson;
import java.net.*;
import java.util.Collection;

import org.glassfish.grizzly.utils.Exceptions;


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
    public void logout(String authToken) throws Exception {
        URI uri = new URI(url + "/session");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("DELETE");
        http.setRequestProperty("Authorization", authToken);
        if (http.getResponseCode() != HttpURLConnection.HTTP_OK) {
            InputStream responseBody = http.getInputStream();
            InputStreamReader reader = new InputStreamReader(responseBody);
            String error = reader.toString();
            throw new Exception("Logout failed?");
        }
    }
    public Collection<GameData> listGames(String authToken) throws Exception
    {
        URI uri = new URI(url + "/game");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("GET");
        http.setDoOutput(true);
        try (var outputStream = http.getOutputStream()) {
            var jsonBody = new Gson().toJson(authToken);
            outputStream.write(jsonBody.getBytes());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("error at getOutputStream");
        }

        try (InputStream respBody = http.getInputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(respBody);
            Type collectionType = new TypeToken<Collection<GameData>>(){}.getType();
            Collection<GameData> responseBody = new Gson().fromJson(inputStreamReader, collectionType);
            return responseBody;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("Error reading response");
        }
    }
    public Integer createGame(String authToken, AddGameRequest request) throws Exception {
        URI uri = new URI(url + "/game");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        try(var requestBody = http.getOutputStream()) { //handle output
            String reqData = new Gson().toJson(new GameData(0, null, null, request.getName(), null));
            requestBody.write(authToken.getBytes());
            requestBody.write(reqData.getBytes());
        }
        catch (Exception ioException) {
            System.out.print(ioException.getMessage());
            throw new Exception("RequestBody failed");
        }
        try (InputStream respBody = http.getInputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(respBody);
            Integer responseBody = new Gson().fromJson(inputStreamReader, Integer.class);
            return responseBody;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("Error reading response");
        }
    }
}
