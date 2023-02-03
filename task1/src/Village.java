import exceptions.NoTroopsOwnedException;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Village {
    int x, y; //co-ordinates
    int health;
    Player owner;
    ArrayList<ResourceGeneratorBuilding> resourceBuildings = new ArrayList<>();
    ArrayList<TroopGeneratorBuilding> troopBuildings = new ArrayList<>();
    ArrayList<Troop> ownedTroops = new ArrayList<>();
    ArrayList<Troop> awayTroops = new ArrayList<>();
    ArrayList<Troop> stationedTroops = new ArrayList<>();
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

    public void updateStationedTroops() { // to add as an attribute instead of returning the list??
        ArrayList<Troop> availableTroops = new ArrayList<>(this.ownedTroops);
        availableTroops.removeAll(this.awayTroops);
        this.stationedTroops = availableTroops;
    }

    public int[] getAvailableTroopTypes(ArrayList<Troop> availableTroops) {
        int[] troopTypes = new int[3];
        // get counts of troop types in availableTroops and check if enough troops of each type are available
        for (Troop troop : availableTroops) {
            if (troop instanceof CavalryTroop) {
                troopTypes[0]++;
            }
            if (troop instanceof ArcherTroop) {
                troopTypes[1]++;
            }
            if (troop instanceof GroundTroop) {
                troopTypes[2]++;
            }
        }
        return troopTypes;
    }

    public void checkSufficientTroopTypes(int cavalryRequest, int archerRequest, int groundRequest, int[] troopTypes) throws NoTroopsOwnedException {
        if (groundRequest > troopTypes[2] || cavalryRequest > troopTypes[0] || archerRequest > troopTypes[1]) {
            throw new NoTroopsOwnedException("Insufficient troops available!");
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

    public void displayTroopBuildings() {
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

    public void updateResources() {
        for (ResourceGeneratorBuilding r : this.resourceBuildings) {
            if (r.generates.equals("Meat")) {
                this.resources.addMeat(r.amountGenerated.meat);
            }
            if (r.generates.equals("Stone")) {
                this.resources.addStone(r.amountGenerated.stone);
            }
            if (r.generates.equals("Wood")) {
                this.resources.addWood(r.amountGenerated.wood);
            }
        }
    }

    public void buildMeatGenerator() {
        if (checkSufficientResources(this.resources.meat, 15)) {
            this.resources.deductMeat(15);
            MeatGenerator m = new MeatGenerator();
            this.resourceBuildings.add(m);
            System.out.println("Meat Generator has been built!");
        }
    }

    public void buildStoneGenerator() {
        if (checkSufficientResources(this.resources.stone, 15)) {
            this.resources.deductStone(15);
            StoneGenerator s = new StoneGenerator();
            this.resourceBuildings.add(s);
            System.out.println("Stone Generator has been built!");
        }
    }

    public void buildWoodGenerator() {
        if (checkSufficientResources(this.resources.wood, 15)) {
            this.resources.deductWood(15);
            WoodGenerator w = new WoodGenerator();
            this.resourceBuildings.add(w);
            System.out.println("Wood Generator has been built!");
        }
    }

    public boolean checkSufficientResources(int balance, int amountToCheck) {
        if (balance < amountToCheck) {
            System.out.println("Insufficient funds!");
            return false;
        }
        return true;
    }

    public void buildCavalryGenerator() {
        if (checkSufficientResources(this.resources.wood, 10)) {
            this.resources.deductMeat(10);
            CavalryGenerator c = new CavalryGenerator();
            this.troopBuildings.add(c);
            System.out.println("Cavalry Troop Generator has been built!");
        }
    }

    public void buildArcherGenerator() {
        if (checkSufficientResources(this.resources.stone, 10)) {
            this.resources.deductStone(10);
            ArcherGenerator a = new ArcherGenerator();
            this.troopBuildings.add(a);
            System.out.println("Archer Troop Generator has been built!");
        }
    }

    public void buildGroundGenerator() {
        if (checkSufficientResources(this.resources.wood, 10)) {
            this.resources.deductWood(10);
            GroundGenerator g = new GroundGenerator();
            this.troopBuildings.add(g);
            System.out.println("Ground Troop Generator has been built!");
        }
    }

    public void attack(int cavalryRequested, int archerRequested, int groundRequested) throws NoTroopsOwnedException {
        // check if enough troops of each type are available
        this.updateStationedTroops();
        int[] availableTroopTypes = this.getAvailableTroopTypes(this.stationedTroops);
        ArrayList<Troop> selectedTroops;
        this.checkSufficientTroopTypes(cavalryRequested, archerRequested, groundRequested, availableTroopTypes);  //check if enough troops of each type are available
        // continue; // change to false, and continue if(!attack());
        // add troops of each amount and type required to arrayList
        selectedTroops = new ArrayList<>();
        int archerCount = 0, cavalryCount = 0, groundCount = 0;
        for (Troop t : this.stationedTroops) {
            if (cavalryCount < cavalryRequested) {
                if (t instanceof CavalryTroop) {
                    cavalryCount++;
                    selectedTroops.add(t);
                }
            }
            if (archerCount < archerRequested) {
                if (t instanceof ArcherTroop) {
                    archerCount++;
                    selectedTroops.add(t);
                }
            }

            if (groundCount < groundRequested) {
                if (t instanceof GroundTroop) {
                    groundCount++;
                    selectedTroops.add(t);
                }

            }


        }
        // enter village co-ordinates to attack - check if they are valid
        double[] coordinates = getCoordinatesToAttack();
        double[] currentLocation = {this.getX(), this.getY()};
        // create Army
        Army army = new Army(selectedTroops, currentLocation, coordinates);
        // add created army to troops away and active armies
        this.activeArmies.add(army);
        this.awayTroops.addAll(selectedTroops);
    }

    public double[] getCoordinatesToAttack() {
        int attackX = 0, attackY = 0;
        boolean repeat = false;
        double[] coordinatesAttack = new double[2];
        Scanner s = new Scanner(System.in);
        Map m = Map.getInstance();

        do {
            try {
                System.out.println("Enter x co-ordinate of village to attack: ");
                attackX = s.nextInt();

                System.out.println("Enter y co-ordinate of village to attack: ");
                attackY = s.nextInt();

                if(attackX>= m.mapDimension || attackX<0 || attackY>= m.mapDimension || attackY<0){
                    System.out.println("Please enter co-ordinates within the range 0-9!");
                }


                if (m.villages[attackX][attackY] != null) {
                    repeat = false;
                }else{
                    System.out.println("The village you have selected does not exist!");
                    repeat=true;
                }

            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid co-ordinate");
                s.nextLine();
                repeat = true;

            }
        } while (repeat);
        coordinatesAttack[0] = attackX;
        coordinatesAttack[1] = attackY;

        return coordinatesAttack;
    }


}
