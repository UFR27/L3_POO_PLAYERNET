package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;

import java.util.Collection;

public interface PlayerFacadePorcelain {

    static PlayerFacadePorcelain getFacade(){
        return PlayerFacadeImpl.getSingleton();
    }

    void waitReady();

    void createNewPlayer(String playerName);

    Game createNewGame(String gameName);

    void waitForPlayerCount(int i);

    GameCommand receiveGameCommand(Game game);

    void sendGameCommand(Game game, GameCommand command);

    Game autoJoinGame(String gameName);

    void leaveLobby();

    void sendLobbyMessage(String text);

    Collection<String> receiveLobbyMessages();

    Collection<String> receiveLobbyStatus();

    Collection<Game> getAvailableGamesInLobby();
}
