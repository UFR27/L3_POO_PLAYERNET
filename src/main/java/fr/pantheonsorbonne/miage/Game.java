package fr.pantheonsorbonne.miage;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class Game {
    private  String gameId;
    private  String gameName;
    private  String playerName;
    private Set<String> players;
    private GameState state;

    public Game(String gameId, String gameName, String playerName, HashSet<String> players, GameState state) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.playerName = playerName;
        this.players = players;
        this.state = state;
    }

    public Game() {
    }

    public String gameId() {
        return gameId;
    }

    public String gameName() {
        return gameName;
    }

    public String playerName() {
        return playerName;
    }

    public Set<String> players() {
        return players;
    }

    public GameState state() {
        return state;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Game) obj;
        return Objects.equals(this.gameId, that.gameId) &&
                Objects.equals(this.gameName, that.gameName) &&
                Objects.equals(this.playerName, that.playerName) &&
                Objects.equals(this.players, that.players) &&
                Objects.equals(this.state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, gameName, playerName, players, state);
    }

    @Override
    public String toString() {
        return "Game[" +
                "gameId=" + gameId + ", " +
                "gameName=" + gameName + ", " +
                "playerName=" + playerName + ", " +
                "players=" + players + ", " +
                "state=" + state + ']';
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Set<String> getPlayers() {
        return players;
    }

    public void setPlayers(Set<String> players) {
        this.players = players;
    }

    public GameState getState() {
        return state;
    }

    public enum GameState {
        CREATED,
        STARTED,
        STOPED
    }
}
