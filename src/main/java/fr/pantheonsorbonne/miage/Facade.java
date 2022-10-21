package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;

/**
 * This is the main entry point for multiplayer game.
 * <p>
 * The facade is a singleton, that should be retreived using the .getFacade() method.
 * Since an asynchrous initialization is required, user should use the waitReady() method before using any other facacde method
 */
public interface Facade {

    /**
     * Allows retreiving a Facade Instance
     *
     * @return the facade instance
     */
    static FacadeImpl getFacade() {
        return FacadeImpl.getSingleton();
    }

    /**
     * this function blocks until the facade is ready to use.
     */
    void waitReady();

    /**
     * To join the lobby, user should  create a name
     *
     * @param playerName
     */
    void createNewPlayer(String playerName);

    /**
     * Send a game command to a specific player
     *
     * @param game       the game to use to send command
     * @param playerName the name of the player to receive the command
     * @param command    the game command
     */
    void sendGameCommandToPlayer(Game game, String playerName, GameCommand command);

    /**
     * Block until we receive a command.
     * <p>
     * We will not receive commands that are not for us, only command sent to all players or to just us
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
    void sendGameCommandToAll(Game game, GameCommand command);


}
