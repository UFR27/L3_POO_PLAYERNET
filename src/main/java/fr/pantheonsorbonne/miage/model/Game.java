package fr.pantheonsorbonne.miage.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * This class represents a game in the multiplayer environment.
 * Game instances should be retreived from the facade and send back to the facade for interacting with a specific game
 */
public final class Game {
    private String gameId;
    private String gameName;
    private String hostName;
    private Set<String> players;
    private GameState state;

    public Game(String gameId, String gameName, String hostName, HashSet<String> players, GameState state) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.hostName = hostName;
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
        return hostName;
    }

    public Set<String> players() {
        return players;
    }

    public GameState state() {
        return state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, gameName, hostName, players, state);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Game) obj;
        return Objects.equals(this.gameId, that.gameId) &&
                Objects.equals(this.gameName, that.gameName) &&
                Objects.equals(this.hostName, that.hostName) &&
                Objects.equals(this.players, that.players) &&
                Objects.equals(this.state, that.state);
    }

    @Override
    public String toString() {
        return "Game[" +
                "gameId=" + gameId + ", " +
                "gameName=" + gameName + ", " +
                "playerName=" + hostName + ", " +
                "players=" + players + ", " +
                "state=" + state + ']';
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

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
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

    public void setState(GameState state) {
        this.state = state;
    }

    public enum GameState {
        CREATED,
        STARTED,
        STOPED
    }
}
