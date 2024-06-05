package service.results;

import java.util.Collection;

import chess.model.GameData;

public class ListGamesResult {
    private String message;
    private Collection<GameData> games;
    public ListGamesResult(String m) {
        this.message = m;
    }
    public ListGamesResult(Collection<GameData> g) {
        this.games = g;
    }
}
