package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.model.Game;

import java.util.Collection;

/**
 * This is more advanced interface to used for advanced game management.
 * Students are not expected to use this interface for their assignements
 */
public interface InternalFacadePlumbing {


    void startGame(Game game);

    void leaveGame(Game game);

    void joinGame(Game game);

    void rawSendGameCommand(Game game, String command);


    String receiveRawGameCommand(Game game);


    boolean isReady();


    void waitGameStarted(Game game);

    int getPlayerCount();

    Collection<String> receiveLobbyMessages();

    Collection<String> receiveLobbyStatus();

    Collection<Game> getAvailableGamesInLobby();


}
