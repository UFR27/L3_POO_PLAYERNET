package fr.pantheonsorbonne.miage;

import java.util.Objects;

public class LobbyInfo {
    @Override
    public String toString() {
        return "LobbyInfo{" +
                "gameName='" + gameName + '\'' +
                ", playerName='" + playerName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LobbyInfo)) return false;
        LobbyInfo lobbyInfo = (LobbyInfo) o;
        return Objects.equals(getGameName(), lobbyInfo.getGameName()) && Objects.equals(getPlayerName(), lobbyInfo.getPlayerName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGameName(), getPlayerName());
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

    private String gameName;
    private String playerName;


    public LobbyInfo() {
    }

    public LobbyInfo(String gameName, String playerName) {
        this.gameName = gameName;
        this.playerName = playerName;
    }
}
