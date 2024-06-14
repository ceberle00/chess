package client;
import chess.*;
import ui.*;
import server.Server;
public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        Server server = new Server();
        var port = server.run(8080);
        Client client = new Client(port);
        client.run();
    }
}