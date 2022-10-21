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

    /**
     * Block until we receive a command from any player from this game
     *
     * @param game the game from which we want to retreive the command
     * @return the command sent by another player
     */
    GameCommand receiveGameCommand(Game game);

    /**
     * Sends a command to other players in this game
     *
     * @param game    the game to which we want to send the command
     * @param command the command to send
     */
    void sendGameCommand(Game game, GameCommand command);
}
