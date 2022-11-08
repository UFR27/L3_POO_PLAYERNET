package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.Facade;
import fr.pantheonsorbonne.miage.HostFacade;
import fr.pantheonsorbonne.miage.PlayerFacade;
import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;


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
    Game createNewGame(String Monopoly);

    // PlayerFacade playerFacade = (PlayerFacade) Facade.getFacade();

    // HostFacade hostFacade = (HostFacade) Facade.getFacade();

    // hostFacade.waitReady();

    // System.out.println("Saisir le numero de player");


    // while (true) {
    //     Game game = hostFacade.createNewGame("Monopoly");
    //     //wait for another player to join
    //     hostFacade.waitForExtraPlayerCount(2); // 
    //     //play the game using the player facade
    //     playTheGame(playerFacade, game);
    // }

    // private static void playTheGame(PlayerFacade playerFacade, Game game) throws FullBoardException {
    //     protected List<Space> board = new ArrayList<>();

    // public void createBoard(){

    //     Color marron = new Color("marron",60,50);
    //     Color bleuClair = new Color("bleuClair",100,50);
    //     Color rose = new Color("rose", 0, 0);
    //     Color orange = new Color("orange", 0, 0);
    //     Color rouge = new Color("rouge", 0, 0);
    //     Color jaune = new Color("jaune", 0, 0);
    //     Color vert = new Color("vert", 0, 0);
    //     Color bleu = new Color("bleu", 0, 0);

    //     //Create a standard monopoly board
    //     board.add(new Space("Depart",0));
    //     board.add(new SpaceCity("Boulevard de Bellvile",1,marron,60));
    //     //caisse de communauté?
    //     board.add(new SpaceCity("Rue Lecourbe",3,marron,60));
    //     // Impot ?
    //     board.add(new SpaceStation("Gare Montparnasse", 5));
    //     board.add(new SpaceCity("Rue de Vaugirard",6,bleuClair,100));
    //     board.add(new SpaceChance("Chance", 7));
    //     board.add(new SpaceCity("Rue de Courcelles",8,bleuClair,100));
    //     board.add(new SpaceCity("Avenue de la République",9,bleuClair,120));
    //     //prison
    //     board.add(new SpaceCity("Boulevard de la Villette",11,rose,140));
    //     // compagnie ?
    //     board.add(new SpaceCity("Avenue de Neuilly",13,rose,140));
    //     board.add(new SpaceCity("Rue de Paradis",14,rose,160));
    //     board.add(new SpaceStation("Gare de Lyon", 15));
    //     board.add(new SpaceCity("Avenue de Mozart",16,orange,180));
    //     //caisse de communauté
    //     board.add(new SpaceCity("Boulevard Saint-Michel",18,orange,180));
    //     board.add(new SpaceCity("Place Pigalle",19,orange,200));
    //     //parc
    //     board.add(new SpaceCity("Avenue Matignon",21,rouge,220));
    //     board.add(new SpaceChance("Chance", 22));
    //     board.add(new SpaceCity("Boulevard Malesherbes",23,rouge,220));
    //     board.add(new SpaceCity("Avenue Henri-Martin",24,rouge,240));
    //     board.add(new SpaceStation("Gare du Nord", 25));
    //     board.add(new SpaceCity("Faubourg Saint-Honoré",26,jaune,260));
    //     board.add(new SpaceCity("Place de la Bourse",27,jaune,260));
    //     // compagnie ?
    //     board.add(new SpaceCity("Rue de la Fayette",29,jaune,280));
    //     //allez en prison
    //     board.add(new SpaceCity("Avenue de Breteuil",31,vert,300));
    //     board.add(new SpaceCity("Avenue Foch",32,vert,300));
    //     //caisse de communauté? 
    //     board.add(new SpaceCity("Boulevard des Capucines",34,vert,320));
    //     board.add(new SpaceStation("Gare Saint-Lazare", 35));
    //     board.add(new SpaceChance("Chance", 36));
    //     board.add(new SpaceCity("Avenue des Champs-Elysées",37,bleu,350));
    //     board.add(new SpaceCity("Rue de la Paix",38,bleu,400));
    // }
    
    // }
}
