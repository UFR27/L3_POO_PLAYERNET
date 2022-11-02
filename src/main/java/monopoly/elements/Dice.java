package monopoly.elements;


public class Dice {
    

    private int dice1;
    private int dice2;


    public int getDice(){
        this.dice1= (int) ((Math.random() * 6) + 1);
        this.dice2 = (int) ((Math.random() * 6) + 1);
        return(dice1+dice2);
    }

    public int getDice1(){
        return dice1;
    }

    
    public int getDice2(){
        return dice2;
    }


}

