package monopoly;

import java.util.ArrayList;
import java.util.List;

import monopoly.elements.Space;

public abstract class AbstractMonopoly implements monopoly {
    protected List<Space> board = new ArrayList<>();
    

    //Create a standard monopoly board
    board.add(new Space("Departure",0));
    
}