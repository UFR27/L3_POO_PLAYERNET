package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.model.Game;

import java.util.Collection;

public interface PlayerFacadePlumbing {




    void startGame(Game game);

    void leaveGame(Game game);

    void joinGame(Game game);

    void rawSendGameCommand(Game game, String command);


    String receiveRawGameCommand(Game game);


    boolean isReady();


    void waitGameStarted(Game game);

    int getPlayerCount();


}
