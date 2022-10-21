package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;

/**
 * This interface allows a player to join a game and interract with other players in a game
 */
public interface PlayerFacade extends Facade {

    /**
     * Join a game by its name
     *
     * @param gameName the name of the
     * @return
     */
    Game autoJoinGame(String gameName);


}
