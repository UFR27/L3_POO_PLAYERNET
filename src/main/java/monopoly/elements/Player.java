package monopoly.elements;

public class Player {
    private String name;
    private int money;
    private int position;    

    public Player(String name, int money, int position){
        this.name = name;
        this.money = money;
        this.position = position;
    }
    
    public int getPosition(){
        return this.position;
    }

    public void setPosition(int diceResult){
        this.position += diceResult;
        // if positon >  taille du plateau
    }

    public void updateBalance(int amount){
        //mise a jour de l'argent
    }

    public void updatePosition(){
        //
    }    

    public int checkBalance(){
        return money;
    }

    public void turn(Player p){
        //pass the turn to the next person 
    }

    public void buyHouse(){

    }

}
