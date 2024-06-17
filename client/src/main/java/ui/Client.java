package ui;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import server.ServerFacade;
import server.WebsocketClient;

import java.util.Scanner;
import java.util.Collection;
import chess.*;
import chess.ChessGame.TeamColor;
import chess.ChessPiece.PieceType;
import chess.model.*;
import java.util.ArrayList;


public class Client {
    private PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    private Scanner scanner = new Scanner(System.in);
    private ServerFacade facade;
    private ChessGameplay gameplay;
    private ArrayList<ChessMove> validMoves = new ArrayList<>();
    private WebsocketClient ws;
    private AuthData authToken;
    private int port;
    private ChessPiece pieceMoves;

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
                break;
            case "Login":
                login();
                break;
            case "Quit":
                break;
            case "Help":
                prelogin();
                break;
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
            postLogin();
        } catch (Exception e) {
            out.println("Error:" + e.getMessage());
            prelogin();
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
            postLogin();
        }catch (Exception e ) {
            out.println("Error:" + e.getMessage());
            prelogin();
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
        //String line = "Observe Game";
        switch (line) {
            case "Logout":
                logout();
                break;
            case "Create Game":
                createGame();
                break;
            case "List Games":
                listGames();
                break;
            case "Play Game":
                playGame();
                break;
            case "Observe Game":
                observeGame();
                break;
            default:
                postLogin();
                break;
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
                TeamColor webColor = TeamColor.BLACK;
                if (color.toLowerCase().equals("white")) {
                    webColor = TeamColor.WHITE;
                }
                if (color.toLowerCase().equals("white") && (games.get(gameID-1).whiteUsername() != null )) {
                    webColor = TeamColor.WHITE;
                    out.print("Color already taken");
                    playGame();
                    
                }
                else if ((games.get(gameID-1).blackUsername() != null)){
                    out.print("Color already taken");
                    playGame();
                }
                else {
                    Integer actualID= games.get((gameID-1)).gameID();
                    facade.joinGame(color, actualID, authToken.authToken()); //not sure what to do from here? Maybe just show game
                    GameData currGame = games.get((gameID-1));
                    gameplay = new ChessGameplay(games.get((gameID-1)).game().getBoard());
                    gameplay.main(false); //idk
                    ws = new WebsocketClient(port);
                    ws.connect(authToken.authToken(), actualID, webColor);
                    out.print("Join game worked :)");
                    inGame(currGame);
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
            //int gameID = 1;
            int gameID = Integer.parseInt(scanner.next());
            if (gameID < 0 || gameID > games.size()) {
                out.print("Invalid number, please select a number that was shown\n");
                observeGame();
            }
            GameData game = games.get((gameID-1));
            gameplay = new ChessGameplay(game.game().getBoard()); 
            gameplay.main(false); //idk
            this.ws = new WebsocketClient(port);
            ws.connect(this.authToken.authToken(), game.gameID(), null); //need to figure out how to get the actual gameID game
            inGame(game);
        } catch (Exception e) {
            out.println("Error:" + e.getMessage());
        }
    }
    private void inGame(GameData game) throws Exception 
    {
        out.print("Type a number to see menu options\n");
        out.print("1. Redraw Board\n");
        out.print("2. Leave\n");
        out.print("3. Resign\n");
        out.print("4. Highlight legal moves\n");
        out.print("5. Make move\n");
        String line = scanner.nextLine();
        //String line = "2";
        switch (line) {
            case "1":
                redrawBoard(game);
                break;
            case "2":
                leave(game);
                break;
            case "3":
                resign(game);
                break;
            case "4":
                getValidMoves(game, false);
                break;
            case "5":
                makeMove(game);
                break;
            default:
                inGame(game);
                break;
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
            if (authToken.getUser().equals(game.blackUsername())) {
                out.print("black user");
                //gameplay.main(true);
            }
            else if (authToken.getUser().equals(game.blackUsername())){
                out.print("white user");
            }
            this.ws.leave(authToken.authToken(), game.gameID());
            postLogin();
            
        }catch (IOException e) {
            throw new IOException("unable to leave");
        }
    }
    private void getValidMoves(GameData game, Boolean isMakingMove) throws Exception {
        ChessBoard board = game.game().getBoard();
        gameplay = new ChessGameplay(board);
        gameplay.main(false); //run the board first
        out.print("Select what piece you wish to move in the form of a-h space 1-8\n");
        String line = scanner.next();
        Integer row = scanner.nextInt();
        String[] headers = {"a", "b", "c", "d", "e", "f", "g", "h"};
        Integer column = null;
        for (int i = 0; i < headers.length; i++) {
            if (line.charAt(0) == (headers[i]).charAt(0)) {
                column = (i+1);
                break;
            }
        }
        if (column == null || row > 8 || row < 1) {
            out.print("Invalid piece, try again\n");
            getValidMoves(game, isMakingMove);
        }
        else if(board.getPiece(new ChessPosition(row, column)) == null) {
            out.print("There's no piece there :(\n");
            getValidMoves(game, isMakingMove);
        }
        else {
            ChessPosition pos = new ChessPosition(row, column);
            this.pieceMoves = board.getPiece(pos);
            Collection<ChessMove> moves = pieceMoves.pieceMoves(board, pos);
            gameplay = new ChessGameplay(board);
            gameplay.highlightMoves(moves);
            if (isMakingMove == false) {
                inGame(game);
            }
            this.validMoves = new ArrayList<>(moves);
        }
    }
    private void makeMove(GameData data) throws Exception {
        try {
            getValidMoves(data, true);
            if (!(authToken.getUser().equals(data.blackUsername())) && !(authToken.getUser().equals(data.whiteUsername()))) {
                out.print("You are not a player, and cannot make a move");
                inGame(data);
            }
            else if (authToken.getUser().equals(data.blackUsername()) && data.game().getTeamTurn() != TeamColor.BLACK) {
                out.print("It is not your turn, wait for your opponent to move first");
                inGame(data);
            }
            else if (authToken.getUser().equals(data.whiteUsername()) && data.game().getTeamTurn() != TeamColor.WHITE) {
                out.print("It is not your turn, wait for your opponent to move first");
                inGame(data);
            }
            else {
                out.print("Where would you like to move to? Input the space in the form of a-h space 1-8");
                String line = scanner.next();
                Integer row = scanner.nextInt();
                String[] headers = {"a", "b", "c", "d", "e", "f", "g", "h"};
                Integer column = null;
                for (int i = 0; i < headers.length; i++) {
                    if (line.charAt(0) == (headers[i]).charAt(0)) {
                        column = (i+1);
                        break;
                    }
                }
                if (column == null || row > 8 || row < 1) {
                    out.print("Invalid space, try again\n");
                    makeMove(data);
                }
                else {
                    ChessPosition pos = new ChessPosition(row, column);
                    PieceType promotionType = null;
                    if (this.pieceMoves.getPieceType() == PieceType.PAWN) {
                        if ((authToken.getUser().equals(data.whiteUsername())) && (this.pieceMoves.getChessPosition().getRow() == 7)) {
                            promotionType = getPromotionType(data);
                        }
                        else if((authToken.getUser().equals(data.blackUsername())) && (this.pieceMoves.getChessPosition().getRow() == 1)) {
                            promotionType = getPromotionType(data);
                        }
                    }
                    ChessMove move = new ChessMove(this.pieceMoves.getChessPosition(), pos, promotionType);
                    //check if move is valid
                    if (this.validMoves.contains(move)) {
                        ws.makeMove(this.authToken.authToken(), data.gameID(), move);
                        inGame(data);
                    }
                    else {
                        out.print("Move is not valid, please suggest a valid move");
                        inGame(data);
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    private void resign(GameData game) throws Exception {
        out.print("Are you sure you want to resign? Type yes to forfit the game");
        String line = scanner.nextLine();
        switch(line) {
            case "yes":
                ws.resign(authToken.authToken(), game.gameID());
                inGame(game);
            default:
                inGame(game);
        }
    }
    private PieceType getPromotionType(GameData data) 
    {
        PieceType piece = PieceType.PAWN;
        out.println("Enter a piece type to promote to (EX: QUEEN, ROOK, etc)");
        String type = scanner.next();
        switch (type) {
            case "QUEEN":
                piece = PieceType.QUEEN;
                return piece;
            case "ROOK":
                piece = PieceType.ROOK;
                return piece;
            case "BISHOP":
                piece = PieceType.BISHOP;
                return piece;
            case "KNIGHT":
                piece = PieceType.KNIGHT;
                return piece;
            default:
                out.println("Invalid type");
                getPromotionType(data);
                return null;
        }
        
    }
}
