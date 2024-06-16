package ui;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import server.ServerFacade;
import server.WebsocketClient;

import java.util.Scanner;
import chess.*;
import chess.model.AuthData;
import chess.model.*;
import java.util.ArrayList;


public class Client {
    private PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    private Scanner scanner = new Scanner(System.in);
    private ServerFacade facade;
    private ChessGameplay gameplay;
    WebsocketClient ws;
    private AuthData authToken;
    private int port;

    public Client(int port) {
        this.facade = new ServerFacade(port);
        this.port = port;
    }
    public void run() {
        initial();
    }
    private void initial()
    {
        //out.print(SET_TEXT_COLOR_BLACK);
        //out.print(SET_BG_COLOR_WHITE);
        out.print("Welcome to 240 Chess, type \'help\' to get started :)");
        //String input = scanner.nextLine();
        String input = "help";
        if (input.toLowerCase().equals("help")) 
        {
            prelogin();
        }
        
    }
    private void prelogin() {
        out.print("Options (type one of the words to proceed): \n");
        out.print("Register:\n");
        out.print("Login:\n");
        out.print("Help\n");
        out.print("Quit\n");
        String line = scanner.next();
        //String line = "Login";
        switch (line) {
            case "Register":
                register();
                postLogin();
            case "Login":
                login();
                postLogin();
            case "Quit":
                break;
            case "Help":
                prelogin();
            default:
                //initial();
        }
    }
    private void register() {
        out.println("Enter username, password, and email separated by spaces :)");
        String username = scanner.next();
        String password = scanner.next();
        String email = scanner.next();
        try {
            out.println("In register try");
            authToken = facade.register(username, password, email);
        } catch (Exception e) {
            out.println("Error:" + e.getMessage());
        }
    }
    private void login()
    {
        out.println("Enter username and password seperated by space");
        //String user = scanner.next();
        //String pass = scanner.next();
        String user = "aleberle";
        String pass = "9lucy9";
        try {
            authToken = facade.login(user, pass);
            out.print(authToken.authToken());
            out.print(authToken.getUser());
        }catch (Exception e ) {
            out.println("Error:" + e.getMessage());
        }
    }
    private void postLogin() {
        out.print("Options (type one of the words to proceed): \n");
        out.print("Help\n");
        out.print("Logout\n");
        out.print("Create Game\n");
        out.print("List Games\n");
        out.print("Play Game\n");
        out.print("Observe Game\n");
        //String line = scanner.nextLine();
        String line = "Observe Game";
        switch (line) {
            case "Logout":
                logout();
            case "Create Game":
                createGame();
            case "List Games":
                listGames();
            case "Play Game":
                playGame();
            case "Observe Game":
                observeGame();
            default:
                postLogin();
        }
    }
    private void logout() {
        out.print("Logging out");
        try {
            facade.logout(authToken.authToken());
            this.authToken = null;
            initial();
        }catch (Exception e) 
        {
            out.println("Error:" + e.getMessage());
        }
    }
    private void createGame() {
        out.print("What do you want your game to be called?\n");
        String gameName = scanner.nextLine();
        try{
            facade.createGame(authToken.authToken(), gameName);
            postLogin();
        }catch(Exception e) {
            out.println("Error:" + e.getMessage());
        }
    }
    private void listGames() {
        out.print("Games on server:\n");
        try {
            ArrayList<GameData> games = (ArrayList<GameData>) facade.listGames(authToken.authToken());
            for (int i = 0; i < games.size(); i++) 
            {
                GameData game = games.get(i);
                out.print("Game " + (i+1) + "{");
                out.print("Game name: " + game.gameName() + ",");
                out.print("Black username: " + game.blackUsername());
                out.print("White username:" + game.whiteUsername());
                out.print("Game: " + game.game() + "}\n");
            }
            out.print("Type \'a\' to go back to the menu");
            String line = scanner.next();
            if (line.equals("a")) {
                postLogin();
            }
            //maybe have a hit any button to go back to main?
        }catch (Exception e) {
            out.println("Error:" + e.getMessage());
        }
    }
    private void playGame() {
        try {
            out.print("Games:");
            ArrayList<GameData> games = (ArrayList<GameData>) facade.listGames(authToken.authToken());
            for (int i = 0; i < games.size(); i++) 
            {
                GameData game = games.get(i);
                out.print("Game " + (i+1) + " {");
                out.print("Game name: " + game.gameName() + ",");
                out.print(" Black username: " + game.blackUsername());
                out.print(" White username:" + game.whiteUsername());
                out.print(" Game: " + game.game() + "}\n");
            }
            out.print("Type in the number of the game you'd like to join followed by the color you'd like to be (seperated by spaces)\n");
            int gameID = Integer.parseInt(scanner.next());
            String color = scanner.next();
            System.out.print(color);
            if (gameID < 0 || gameID > games.size()) {
                out.print("Invalid number, please select a number that was shown\n");
                //playGame();
            }
            if (color.toLowerCase().equals("white") || color.toLowerCase().equals("black")) {
                Integer actualID= games.get(gameID-1).gameID();
                facade.joinGame(color, actualID, authToken.authToken()); //not sure what to do from here? Maybe just show game
                GameData currGame = games.get(gameID);
                gameplay = new ChessGameplay(games.get(gameID).game().getBoard());
                gameplay.main(false); //idk
                out.print("Hit the \'a\' key to go back to the menu:\n");
                String line = scanner.next();
                if (line != null) {
                    postLogin();
                }
            }
            else {
                out.print("Invalid color :(\n");
                playGame();
            }
        } catch (Exception e) {
            out.println("Error:" + e.getMessage());
        }
    }
    private void observeGame()
    {
        try {
            out.print("Games:");
            ArrayList<GameData> games = (ArrayList<GameData>) facade.listGames(authToken.authToken());
            for (int i = 0; i < games.size(); i++) 
            {
                GameData game = games.get(i);
                out.print("Game " + (i+1) + "{");
                out.print("Game name: " + game.gameName() + ",");
                out.print("Black username: " + game.blackUsername());
                out.print("White username:" + game.whiteUsername());
                out.print("Game: " + game.game() + "}\n");
            }
            out.print("Type in the number of the game you'd like to observe\n");
            int gameID = 1;
            //int gameID = Integer.parseInt(scanner.next());
            if (gameID < 0 || gameID > games.size()) {
                out.print("Invalid number, please select a number that was shown\n");
                //playGame();
                observeGame();
            }
            out.print("before game");
            GameData game = games.get((gameID-1));
            gameplay = new ChessGameplay(game.game().getBoard()); //pass in game id to inGame
            gameplay.main(false); //idk
            this.ws = new WebsocketClient(port);
            ws.connect(this.authToken.authToken(), game.gameID()); //need to figure out how to get the actual gameID game
            inGame(game);
            /*out.print("Hit the \'a\' key to go back to the menu:\n");
            String line = scanner.next();
            if (line != null) {
                postLogin();
            }*/
        } catch (Exception e) {
            out.println("Error:" + e.getMessage());
        }
    }
    private void inGame(GameData game) throws Exception {
        out.print("Type a number to see menu options\n");
        out.print("1. Redraw Board\n");
        out.print("2. Leave\n");
        out.print("3. Resign\n");
        out.print("4. Highlight legal moves\n");
        out.print("5. Make move\n");
        //String line = scanner.nextLine();
        String line = "1";
        switch (line) {
            case "1":
                redrawBoard(game);
            case "2":
                leave(game);;
            case "3":
                listGames();
            case "4":
                playGame();
            case "5":
                observeGame();
            default:
                inGame(game);
        }
    }
    private void redrawBoard(GameData game) 
    {
        gameplay = new ChessGameplay(game.game().getBoard());
        if (authToken.getUser().equals(game.blackUsername())) {
            gameplay.main(true);
        }
        else {
            gameplay.main(false);
        }
    }
    private void leave(GameData game) throws Exception {
        try {
            this.ws.leave(authToken.authToken(), game.gameID());
            postLogin();
            
        }catch (IOException e) {
            throw new IOException("unable to leave");
        }
    }
}
