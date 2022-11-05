package monopoly;

import java.util.ArrayList;
import java.util.List;

import monopoly.elements.Color;
import monopoly.elements.Space;
import monopoly.elements.SpaceChance;
import monopoly.elements.SpaceCity;
import monopoly.elements.SpaceStation;

public abstract class AbstractMonopoly implements monopoly {
    protected List<Space> board = new ArrayList<>();

    public void createBoard(){

        Color marron = new Color("marron",60,50);
        Color bleuClair = new Color("bleuClair",100,50);
        Color rose = new Color("rose", 0, 0);
        Color orange = new Color("orange", 0, 0);
        Color rouge = new Color("rouge", 0, 0);
        Color jaune = new Color("jaune", 0, 0);
        Color vert = new Color("vert", 0, 0);
        Color bleu = new Color("bleu", 0, 0);

        //Create a standard monopoly board
        board.add(new Space("Depart",0));
        board.add(new SpaceCity("Boulevard de Bellvile",1,marron,60));
        //caisse de communauté?
        board.add(new SpaceCity("Rue Lecourbe",3,marron,60));
        // Impot ?
        board.add(new SpaceStation("Gare Montparnasse", 5));
        board.add(new SpaceCity("Rue de Vaugirard",6,bleuClair,100));
        board.add(new SpaceChance("Chance", 7));
        board.add(new SpaceCity("Rue de Courcelles",8,bleuClair,100));
        board.add(new SpaceCity("Avenue de la République",9,bleuClair,120));
        //prison
        board.add(new SpaceCity("Boulevard de la Villette",11,rose,140));
        // compagnie ?
        board.add(new SpaceCity("Avenue de Neuilly",13,rose,140));
        board.add(new SpaceCity("Rue de Paradis",14,rose,160));
        board.add(new SpaceStation("Gare de Lyon", 15));
        board.add(new SpaceCity("Avenue de Mozart",16,orange,180));
        //caisse de communauté
        board.add(new SpaceCity("Boulevard Saint-Michel",18,orange,180));
        board.add(new SpaceCity("Place Pigalle",19,orange,200));
        //parc
        board.add(new SpaceCity("Avenue Matignon",21,rouge,220));
        board.add(new SpaceChance("Chance", 22));
        board.add(new SpaceCity("Boulevard Malesherbes",23,rouge,220));
        board.add(new SpaceCity("Avenue Henri-Martin",24,rouge,240));
        board.add(new SpaceStation("Gare du Nord", 25));
        board.add(new SpaceCity("Faubourg Saint-Honoré",26,jaune,260));
        board.add(new SpaceCity("Place de la Bourse",27,jaune,260));
        // compagnie ?
        board.add(new SpaceCity("Rue de la Fayette",29,jaune,280));
        //allez en prison
        board.add(new SpaceCity("Avenue de Breteuil",31,vert,300));
        board.add(new SpaceCity("Avenue Foch",32,vert,300));
        //caisse de communauté?
        board.add(new SpaceCity("Boulevard des Capucines",34,vert,320));
        board.add(new SpaceStation("Gare Saint-Lazare", 35));
        board.add(new SpaceChance("Chance", 36));
        board.add(new SpaceCity("Avenue des Champs-Elysées",37,bleu,350));
        board.add(new SpaceCity("Rue de la Paix",38,bleu,400));
    }
}