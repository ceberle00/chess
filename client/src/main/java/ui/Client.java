package ui;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import server.ServerFacade;
import static ui.EscapeSequences.SET_BG_COLOR_WHITE;
import static ui.EscapeSequences.SET_TEXT_COLOR_BLACK;
import java.util.Scanner;
import chess.*;
import chess.model.AuthData;
import chess.model.*;
import java.util.ArrayList;

public class Client {
    private PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    private Scanner scanner = new Scanner(System.in);
    private ServerFacade facade;
    ChessGameplay gameplay;
    private AuthData authToken;
    public Client(int port) {
        this.facade = new ServerFacade(port);
    }
    public void run() {
        initial();
    }
    private void initial()
    {
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(SET_BG_COLOR_WHITE);
        out.print("Welcome to 240 Chess, type \'help\' to get started :)");
        //String input = scanner.nextLine();
        String input = "help";
        if (input.toLowerCase().equals("help")) 
        {
            prelogin();
            //call another function
        }
        
    }
    private void prelogin() {
        out.print("Options (type one of the words to proceed): \n");
        out.print("Register:\n");
        out.print("Login:\n");
        out.print("Help\n");
        out.print("Quit\n");
        //String line = scanner.nextLine();
        String line = "Register";
        switch (line) {
            case "Register":
                register();
                break;
            case "Login":
                login();
                break;
            case "Quit":
                break;
            default:
                initial();
        }
    }
    private void register() {
        out.println("Enter username, password, and email separated by spaces :)");
        //String username = scanner.next();
        //String password = scanner.next();
        //String email = scanner.next();
        String username = "user";
        String password = "password";
        String email = "email";
        try {
            out.println("In register try");
            authToken = facade.register(username, password, email);
            postLogin(); //not sure if I need to actually log in
            //then log in
        } catch (Exception e) {
            out.println("Error:" + e.getMessage());
        }
    }
    private void login()
    {
        out.println("Enter username and password seperated by space");
        String user = scanner.next();
        String pass = scanner.next();
        try {
            authToken = facade.login(user, pass);
            postLogin();
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
        String line = scanner.nextLine();
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
                out.print("Game " + i + "{");
                out.print("Game name: " + game.gameName() + ",");
                out.print("Black username: " + game.blackUsername());
                out.print("White username:" + game.whiteUsername());
                out.print("Game: " + game.game() + "}\n");
            }
            postLogin();
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
                out.print("Game " + i + "{");
                out.print("Game name: " + game.gameName() + ",");
                out.print("Black username: " + game.blackUsername());
                out.print("White username:" + game.whiteUsername());
                out.print("Game: " + game.game() + "}\n");
            }
            out.print("Type in the number of the game you'd like to join followed by the color you'd like to be (seperated by spaces)\n");
            int gameID = Integer.parseInt(scanner.next());
            String color = scanner.next();
            if (gameID < 0 || gameID > games.size()-1) {
                out.print("Invalid number, please select a number that was shown\n");
                playGame();
            }
            if (color.toLowerCase() != "white" && color.toLowerCase() != "black") {
                out.print("Invalid color :(\n");
                playGame();
            }
            Integer actualID= games.get(gameID).gameID();
            facade.joinGame(color, actualID, authToken.authToken()); //not sure what to do from here? Maybe just show game
            gameplay = new ChessGameplay(games.get(gameID).game().getBoard());
            gameplay.main(false); //idk
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
                out.print("Game " + i + "{");
                out.print("Game name: " + game.gameName() + ",");
                out.print("Black username: " + game.blackUsername());
                out.print("White username:" + game.whiteUsername());
                out.print("Game: " + game.game() + "}\n");
            }
            out.print("Type in the number of the game you'd like to observe\n");
            int gameID = Integer.parseInt(scanner.next());
            if (gameID < 0 || gameID > games.size()-1) {
                out.print("Invalid number, please select a number that was shown\n");
                playGame();
            }
            ChessGame game = games.get(gameID).game();
            gameplay = new ChessGameplay(game.getBoard());
            gameplay.main(false); //idk
            
        } catch (Exception e) {
            out.println("Error:" + e.getMessage());
        }
    }
}
