package monopoly.elements;

public class SpaceCity extends Space {

    private Player owner; 
    private Color color;
    private int price;
    private int rentPrice;

    public SpaceCity(String name, int index,Color color,int price) {
        super(name, index);
        
        this.color = color;
        
        this.price =price;
    }

    public void setOwner(Player p){
        this.owner = p;
    }
    public void changeOwner(Player p){
        //
    }

    public void changeRentPrice(){
        //
    }

    public boolean isOccupied(){
        return this.owner != null;
    }

}
