package monopoly.elements;

public class Player {
    String name;
    int money;
    int position;    

    public Player(String name, int money, int position){
        this.name = name;
        this.money = money;
        this.position = position;
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

}
