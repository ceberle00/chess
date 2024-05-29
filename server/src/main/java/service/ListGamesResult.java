package service;

import java.util.ArrayList;


import chess.model.GameData;

public class ListGamesResult {
    private String message;
    private ArrayList<GameData> games;
    public ListGamesResult(String m) {
        this.message = m;
    }
    public ListGamesResult(ArrayList<GameData> g) {
        this.games = g;
    }
}
