package monopoly.elements;

public class Color {

    
    private Player colorMonopolist;
    String name;
    int housePrice;

    public Color(String name, int housePrice){
        this.name = name;
        this.housePrice = housePrice;
    }

    
    public void setPlayer(Player colorMonopolist){
        this.colorMonopolist=colorMonopolist;
    }

    public int getHousePrcie(){
        return housePrice;
    }

}
