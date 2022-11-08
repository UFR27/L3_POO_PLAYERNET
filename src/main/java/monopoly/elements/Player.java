package monopoly.elements;

import java.util.ArrayList;

public class Player {
    private String name;
    private int money;
    private int position;   
    private int prisonDuration; 
    private ArrayList<SpaceCity> property = new ArrayList<>();

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
        if (this.position > 38){
            this.position -= 38;
        }
    }

    public void updateBalance(int amount){
        money = money + amount;
    }

    public int getAsset(){ //game over condtion : asset < 0
        int asset = 0;
        int nbBuildings = 0;
        for(SpaceCity s : property ){
            nbBuildings = s.getNbBuilding();
            asset += s.getColor().housePrice*nbBuildings + s.getLandPrice() + money;
        }
        return asset;
    }

    public int checkBalance(){
        return money;
    }

    public void buyHouse(){
        
    }

    public void buyLand(SpaceCity s){
        
        property.add(s);
    }



}
