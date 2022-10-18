package fr.pantheonsorbonne.miage;

import java.util.Collection;

public class Player2 {
    public static void main(String[] args) throws Exception {
        PlayerFacade facade = PlayerFacadeImpl.getInstance();
        facade.waitReady();
        facade.joinLobby("Player2");
        Collection<Game> availableChifoumiGames;
        while (true) {
            for (Game game : facade.getAvailableGamesInLobby()) {
                if (game.gameName().equals("chifoumi")) {
                    facade.joinGame(game);
                    facade.waitGameStart(game);
                    var commands = facade.receiveGameCommands(game);
                    while (commands.size() == 0) {
                        Thread.sleep(100);
                        commands = facade.receiveGameCommands(game);

                    }
                    for (String command : commands) {
                        System.out.println("received command " + command);
                        if (command.equals("endGame")) {
                            System.out.println("game ended");
                            System.exit(0);
                        } else {
                            System.out.println("received " + command);
                        }
                    }

                    break;
                }
            }
        }


    }

}
