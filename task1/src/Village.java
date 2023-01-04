import java.util.ArrayList;

public class Village {
    int x,y; //co-ordinates
    int health;
    Player owner;
    ArrayList<Troop> ownedTroops;
    ArrayList<Troop> stationedTroops; //troops at the village at any time
    Resource resources;

    // constructor
    public Village(Player owner){
        this.health=100; // set health to 100 by default
        this.owner=owner;
        this.resources=new Resource(); // set default resource values when player is created through default constructor
        // co-ordinates set when village is added to map

    }



    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
