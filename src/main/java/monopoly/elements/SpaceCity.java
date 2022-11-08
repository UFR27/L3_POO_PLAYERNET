package monopoly.elements;

public class SpaceCity extends Space {

    private Player owner; 
    private Color color;
    private int landPrice;
    private int[] rentPrice;
    private int[] nbBuilding = new int[1]; 

    public SpaceCity(String name, int index,Color color,int landPrice, int[] rentPrice, int nbHouse, int nbHotel) {
        super(name, index);
        this.color = color;
        this.landPrice =landPrice;
        this.rentPrice = rentPrice;
        nbBuilding[0] = nbHouse;
        nbBuilding[1] = nbHotel;
    }

    public void setOwner(Player p){
        this.owner = p;
    }

    public boolean isOccupied(){
        return this.owner != null;
    }

    public int[] getNbBuildingDetail(){
        return nbBuilding;
    }

    public int getNbBuilding(){
        return nbBuilding[0]+nbBuilding[1];
    }

    public int getLandPrice(){
        return landPrice;
    }

    public Color getColor(){
        return color;
    }

}

