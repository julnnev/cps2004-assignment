import java.util.ArrayList;

public class Village {
    int x, y; //co-ordinates
    int health;
    Player owner;
    ArrayList<ResourceGeneratorBuilding> resourceBuildings = new ArrayList<>();
    ArrayList<TroopGeneratorBuilding> troopBuildings = new ArrayList<>();
    ArrayList<Troop> ownedTroops = new ArrayList<>();
    ArrayList<Troop> awayTroops = new ArrayList<>();
    ArrayList<Army> activeArmies = new ArrayList<>();
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

    public  ArrayList<Troop> getAvailableTroops(){ // to add as an attribute instead of returning the list??
        ArrayList<Troop> availableTroops = new ArrayList<>(this.ownedTroops);
        availableTroops.removeAll(this.awayTroops);
        return availableTroops;
    }

    public int[] getAvailableTroopTypes(ArrayList<Troop> availableTroops){
        int[] troopTypes = new int[3];
        // get counts of troop types in availableTroops and check if enough troops of each type are available
        for(Troop troop: availableTroops){
            if(troop instanceof CavalryTroop){
                troopTypes[0]++;
            }
            if(troop instanceof ArcherTroop){
                troopTypes[1]++;
            }
            if(troop instanceof GroundTroop){
                troopTypes[2]++;
            }
        }
        return troopTypes;
    }

    public boolean checkSufficientTroopTypes(int cavalryRequest, int archerRequest, int groundRequest, int[] troopTypes) {
        if (groundRequest > troopTypes[2] || cavalryRequest > troopTypes[0] || archerRequest > troopTypes[1]) {
            System.out.println("Insufficient troops available!");
            return false;
        } else {
            return true;
        }
    }

    public void displayResourceBuildings() {
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
