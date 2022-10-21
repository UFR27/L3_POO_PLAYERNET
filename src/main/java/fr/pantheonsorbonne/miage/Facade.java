package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;

import java.util.Collection;

/**
 * This is the main entry point for multiplayer game.
 * <p>
 * The facade is a singleton, that should be retreived using the .getFacade() method.
 * Since an asynchrous initialization is required, user should use the waitReady() method before using any other facacde method
 */
public interface Facade {

    /**
     * Allows retreiving a Facade Instance
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
     * @param playerName
     */
    void createNewPlayer(String playerName);




}
