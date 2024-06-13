package server;

import java.io.InputStream;
import java.io.InputStreamReader;

import chess.model.requests.AddGameRequest;
import chess.model.requests.JoinGameRequest;
import chess.model.requests.LoginRequest;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import chess.model.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.net.*;
import java.util.Collection;



public class HttpHandler {
    String url = "";
    public HttpHandler(String serverFacadeUrl) {
        this.url = serverFacadeUrl;
    }
    public AuthData register(UserData data) throws Exception {
        URI uri = new URI(url + "/user");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("POST");
        http.setConnectTimeout(10000); //setting connection time longer
        http.setReadTimeout(10000);
        http.setDoOutput(true);

        http.connect();
        try (var outputStream = http.getOutputStream()) {
            var jsonBody = new Gson().toJson(data);
            System.out.print(jsonBody);
            outputStream.write(jsonBody.getBytes());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("error at getOutputStream");
        }
        var statusCode = http.getResponseCode();
        var statusMessage = http.getResponseMessage();
        System.out.println(statusCode + statusMessage);
        if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
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
        else {
            InputStream respBody = http.getErrorStream();
            InputStreamReader inputStreamReader = new InputStreamReader(respBody);
            AuthData responseBody = new Gson().fromJson(inputStreamReader, AuthData.class);
            return responseBody;
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
        try (InputStream responseBody = http.getInputStream();) {
            new InputStreamReader(responseBody);
            //String error = reader.toString();
            //throw new Exception("Logout failed?");
        }
    }
    public Collection<GameData> listGames(String authToken) throws Exception
    {
        URI uri = new URI(url + "/game");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("GET");
        http.setDoOutput(true);
        http.setRequestProperty("Authorization", authToken);
        

        try (InputStream respBody = http.getInputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(respBody);
            Type collectionType = new TypeToken<Collection<GameData>>(){}.getType();
            StringBuilder sb = new StringBuilder();
            char[] buffer = new char[1024];
            int read;
            while ((read = inputStreamReader.read(buffer, 0, buffer.length)) != -1) {
                sb.append(buffer, 0, read);
            }
            String jsonResponse = sb.toString();
            JsonObject jsonObject = new Gson().fromJson(jsonResponse, JsonObject.class);
            Collection<GameData> games = new Gson().fromJson(jsonObject.get("games"), collectionType);
            return games;
            //Collection<GameData> responseBody = new Gson().fromJson(inputStreamReader, collectionType);
            //return responseBody;
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
        http.setRequestProperty("Authorization", authToken);
        http.setDoOutput(true);
        try(var requestBody = http.getOutputStream()) { //handle output
            var jsonBody = new Gson().toJson(request);
            requestBody.write(jsonBody.getBytes());
        }
        catch (Exception ioException) {
            System.out.print(ioException.getMessage());
            throw new Exception("RequestBody failed");
        }
        try (InputStream respBody = http.getInputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(respBody);
            GameData responseBody = new Gson().fromJson(inputStreamReader, GameData.class);
            return responseBody.gameID();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
    public void joinGame(String authToken, JoinGameRequest request) throws Exception 
    {
        URI uri = new URI(url + "/game");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("PUT");
        http.setRequestProperty("Authorization", authToken);
        http.setDoOutput(true);
        try(var requestBody = http.getOutputStream()) { //handle output
            var jsonBody = new Gson().toJson(request);
            requestBody.write(jsonBody.getBytes());
        }
        catch (Exception ioException) {
            System.out.print(ioException.getMessage());
            throw new Exception("RequestBody failed");
        }
        //var statusCode = http.getResponseCode();
        try (InputStream respBody = http.getInputStream()) {
            //InputStreamReader inputStreamReader = new InputStreamReader(respBody);
            //do nothing? Should just be fine idk
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
}
