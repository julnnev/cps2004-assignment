import java.util.ArrayList;

public class Village {
    int x, y; //co-ordinates
    int health;
    Player owner;
    ArrayList<ResourceGeneratorBuilding> resourceBuildings = new ArrayList<>();
    ArrayList<TroopGeneratorBuilding> troopBuildings = new ArrayList<>();
    ArrayList<Troop> ownedTroops = new ArrayList<>();
    ArrayList<Troop> awayTroops = new ArrayList<>(); //troops at the village at any time
    Resource resources;

    // constructor
    public Village(Player owner) {
        this.health = 100; // set health to 100 by default
        this.owner = owner;
        this.resources = new Resource(); // set default resource values when player is created through default constructor
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

    public void displayResourceBuildings() {
        //display resource generator buildings
        int index = 0;
        for (ResourceGeneratorBuilding r : this.resourceBuildings) {
            System.out.println(index + "\nLevel: " + r.level + " \nGenerates: " + r.generates);
            int tempAmount = 0;
            if (r.amountGenerated.wood != 0) {
                tempAmount = r.amountGenerated.wood;
            }
            if (r.amountGenerated.stone != 0) {
                tempAmount = r.amountGenerated.stone;
            }
            if (r.amountGenerated.meat != 0) {
                tempAmount = r.amountGenerated.meat;
            }
            System.out.println("Amount Generated: " + tempAmount);
            index++;
        }
    }

    public void displayTroopBuildings(){
        int index = 0;
        for (TroopGeneratorBuilding r : this.troopBuildings) {
            System.out.println(index + "\nLevel: " + r.level + " \nTroop Health: " + r.troopHealth + "\nTroop Attack: " + r.troopAttack);
            String type = " ";
            if (r.cost.wood != 0) {
                type = "Ground";
            }
            if (r.cost.stone != 0) {
                type = "Archery";
            }
            if (r.cost.meat != 0) {
                type = "Cavalry";
            }
            System.out.println("Troop Generated: " + type);
            index++;
        }
    }

}
