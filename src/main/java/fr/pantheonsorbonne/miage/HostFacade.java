package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.model.Game;

/**
 * This facade interface allows the host to manage the game
 */
public interface HostFacade extends Facade {

    /**
     * This is used to create a new game, an annonce it to other players
     *
     * @param gameName the name of the game (e.g., poker, tictactoe)
     * @return an instance of the game that can be used to interract with the facade.
     */
    Game createNewGame(String gameName);

    /**
     * This method blocks until a certain amount of player has join the game
     *
     * @param i the minimum amount of player to join the game
     */
    void waitForExtraPlayerCount(int i);
}
