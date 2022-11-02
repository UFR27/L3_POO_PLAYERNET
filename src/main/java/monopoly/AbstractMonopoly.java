package monopoly;

import java.util.ArrayList;
import java.util.List;

import monopoly.elements.Color;
import monopoly.elements.Space;
import monopoly.elements.SpaceCity;

public abstract class AbstractMonopoly implements monopoly {
    protected List<Space> board = new ArrayList<>();

    public void createBoard(){

        Color brown = new Color("brown",60,50);
        Color skyBlue = new Color("skyBlue",100,50);


        //Create a standard monopoly board
        board.add(new Space("Departure",0));
        board.add(new SpaceCity("Boulevard de Bellvile",1,null,brown,0));


    }
}