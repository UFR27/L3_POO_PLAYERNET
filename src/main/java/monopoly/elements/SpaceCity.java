package monopoly.elements;

public class SpaceCity extends Space {
        private Player owner; 
        private Color color;
        private int charge;
    public SpaceCity(String name, int index,Player owner,Color color,int charge) {
        super(name, index);
        this.owner = owner;
        this.color = color;
        this.charge = charge;
    }

    public void changeOwner(Player p){
        //
    }

    public void changeCharge(){
        //
    }

    public boolean isOccupied(){
        return this.owner != null;
    }

}
