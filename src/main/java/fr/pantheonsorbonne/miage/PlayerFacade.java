package fr.pantheonsorbonne.miage;

import java.util.Collection;

public interface PlayerFacade {

    void joinLobby(String playerName);

    void leaveLobby();

    void sendLobbyMessage(String text);

    Collection<String> receiveLobbyMessages();

    Collection<String> receiveLobbyStatus();

    Collection<Game> getAvailableGamesInLobby();

    Game createNewGameInLobby(String gameName);

    void startGame(Game game);

    void leaveGame(Game game);

    void joinGame(Game game);

    void sendGameCommand(Game game, String command);

    Collection<String> receiveGameCommands(Game game);


    boolean isReady();

    void waitReady();

    void waitGameStart(Game game);

    int getPlayerCount();
}
