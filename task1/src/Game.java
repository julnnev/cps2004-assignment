import exceptions.NoBuildingsException;
import exceptions.NoTroopsOwnedException;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
    static int playerCount = 0;

    public void run() {
        // ADD: polymorphism - method overriding
        ArrayList<Player> playerList;
        Map map = Map.getInstance();

        int[] playerCountByType;
        welcomeMessage();
        playerCountByType = getDesiredPlayerCount(); // [0] - in person players, [1] - AI Players, [2] total number of desired players

        playerList = createPlayers(playerCountByType);

        createVillages(playerList); // on creating players and their associated village, add village to the map

        System.out.println(playerCount);
        map.printMap();
        int choice;
        // game loop
        do {
            for (int i = 0; i < map.mapDimension; i++) {
                for (int j = 0; j < map.mapDimension; j++) {
                    if (map.villages[i][j] != null) {
                        if (playerCount == 1) {
                            System.out.println("Congratulations " + map.villages[i][j].owner.name + " you have won the game!");
                            break;
                        }
                        System.out.println("Player " + map.villages[i][j].owner.name + " it's your turn!");

                        // friendly troop arrival


                        // enemy troop arrival


                        //resource earning - wait one round to get resources/upgrades, until buildings are built/upgraded
                        System.out.println("Current Resources\nWood: " + map.villages[i][j].resources.wood + "\nStone: " + map.villages[i][j].resources.stone + "\nMeat: " + map.villages[i][j].resources.meat);
                        for (ResourceGeneratorBuilding r : map.villages[i][j].resourceBuildings) {
                            if (r.generates.equals("Meat")) {
                                map.villages[i][j].resources.addMeat(r.amountGenerated.meat);
                            }
                            if (r.generates.equals("Stone")) {
                                map.villages[i][j].resources.addStone(r.amountGenerated.stone);
                            }
                            if (r.generates.equals("Wood")) {
                                map.villages[i][j].resources.addWood(r.amountGenerated.wood);
                            }
                        }

                        // player actions
                        choice = playerActions();
                        if (choice == 5) {
                            continue; // indicates pass the turn
                        }
                        switch (choice) {
                            case 1: // build/upgrade buildings
                                System.out.println("1. Build Resource Generator Buildings\n2. Build Troop Generating Buildings\n3. Upgrade Resource Generator Buildings\n4. Upgrade Troop Generating Buildings\n");
                                int select = getIndex(5, false);
                                switch (select) {
                                    case 1: //build resource gen
                                        System.out.println("Time to build Resource Generator Buildings!");
                                        int option = getBuildingType(true);
                                        switch (option) {
                                            case 1: //meat
                                                if (map.villages[i][j].resources.meat < 15) {
                                                    System.out.println("Invalid funds!");
                                                } else {
                                                    map.villages[i][j].resources.deductMeat(15);
                                                    MeatGenerator m = new MeatGenerator();
                                                    map.villages[i][j].resourceBuildings.add(m);
                                                    System.out.println("Meat Generator has been built!");
                                                }
                                                break;
                                            case 2: // stone
                                                if (map.villages[i][j].resources.stone < 15) {
                                                    System.out.println("Invalid funds!");
                                                } else {
                                                    map.villages[i][j].resources.deductStone(15);
                                                    StoneGenerator s = new StoneGenerator();
                                                    map.villages[i][j].resourceBuildings.add(s);
                                                    System.out.println("Stone Generator has been built!");
                                                }
                                                break;
                                            case 3: // wood
                                                if (map.villages[i][j].resources.wood < 15) {
                                                    System.out.println("Invalid funds!");
                                                } else {
                                                    map.villages[i][j].resources.deductWood(15);
                                                    WoodGenerator w = new WoodGenerator();
                                                    map.villages[i][j].resourceBuildings.add(w);
                                                    System.out.println("Wood Generator has been built!");
                                                }
                                                break;
                                            default:
                                                System.out.println("Uh oh, something went wrong");
                                                break;

                                        }
                                        break;
                                    case 2: //build troop gen
                                        System.out.println("Time to Build Troop Generator Buildings!\n");
                                        option = getBuildingType(false);
                                        switch (option) {
                                            case 1: //meat
                                                if (map.villages[i][j].resources.meat < 10) {
                                                    System.out.println("Invalid funds!");
                                                } else {
                                                    map.villages[i][j].resources.deductMeat(10);
                                                    CavalryGenerator c = new CavalryGenerator();
                                                    map.villages[i][j].troopBuildings.add(c);
                                                    System.out.println("Cavalry Troop Generator has been built!");
                                                }
                                                break;
                                            case 2: // stone
                                                if (map.villages[i][j].resources.stone < 10) {
                                                    System.out.println("Invalid funds!");
                                                } else {
                                                    map.villages[i][j].resources.deductStone(10);
                                                    ArcherGenerator a = new ArcherGenerator();
                                                    map.villages[i][j].troopBuildings.add(a);
                                                    System.out.println("Archer Troop Generator has been built!");
                                                }
                                                break;
                                            case 3: // wood
                                                if (map.villages[i][j].resources.wood < 10) {
                                                    System.out.println("Invalid funds!");
                                                } else {
                                                    map.villages[i][j].resources.deductWood(10);
                                                    GroundGenerator g = new GroundGenerator();
                                                    map.villages[i][j].troopBuildings.add(g);
                                                    System.out.println("Ground Troop Generator has been built!");
                                                }
                                                break;
                                        }
                                        break;
                                    case 3: //upgrade resource gen
                                        try {
                                            int selection;
                                            if (map.villages[i][j].resourceBuildings.size() != 0) {
                                                map.villages[i][j].displayResourceBuildings();
                                                ResourceGeneratorBuilding toUpgrade;
                                                System.out.println("Time to Upgrade!");
                                                selection = getIndex(map.villages[i][j].resourceBuildings.size(), true);
                                                toUpgrade = map.villages[i][j].resourceBuildings.get(selection);
                                                if (toUpgrade.generates.equals("Meat")) {
                                                    if (map.villages[i][j].resources.meat < 15) {
                                                        System.out.println("Invalid funds!");
                                                    } else {
                                                        map.villages[i][j].resources.deductMeat(15);
                                                        MeatGenerator toU = (MeatGenerator) toUpgrade;
                                                        toU.upgradeResources();
                                                        System.out.println("Meat Generator has been upgraded!");
                                                    }
                                                }

                                                if (toUpgrade.generates.equals("Stone")) {
                                                    if (map.villages[i][j].resources.stone < 15) {
                                                        System.out.println("Invalid funds!");
                                                    } else {
                                                        map.villages[i][j].resources.deductStone(15);
                                                        StoneGenerator toU = (StoneGenerator) toUpgrade;
                                                        toU.upgradeResources();
                                                        System.out.println("Stone Generator has been upgraded!");
                                                    }
                                                }


                                                if (toUpgrade.generates.equals("Wood")) {
                                                    if (map.villages[i][j].resources.wood < 15) {
                                                        System.out.println("Invalid funds!");
                                                    } else {
                                                        map.villages[i][j].resources.deductWood(15);
                                                        WoodGenerator toU = (WoodGenerator) toUpgrade;
                                                        toU.upgradeResources();
                                                        System.out.println("Wood Generator has been upgraded!");
                                                    }
                                                }
                                            } else {
                                                throw new NoBuildingsException("No resource generating buildings built!");
                                            }
                                        } catch (NoBuildingsException n) {
                                            System.out.println(n.getMessage());
                                        }
                                        break;
                                    case 4: //upgrade troop gen
                                        try {
                                            int selection;
                                            if (map.villages[i][j].troopBuildings.size() != 0) {
                                                map.villages[i][j].displayTroopBuildings();
                                                System.out.println("Time to Upgrade!");
                                                TroopGeneratorBuilding toUpgradeTroop;
                                                selection = getIndex(map.villages[i][j].troopBuildings.size(), true);
                                                toUpgradeTroop = map.villages[i][j].troopBuildings.get(selection);
                                                if (toUpgradeTroop.cost.meat != 0) {
                                                    if (map.villages[i][j].resources.meat < 10) {
                                                        System.out.println("Invalid funds!");
                                                    } else {
                                                        map.villages[i][j].resources.deductMeat(10);
                                                        toUpgradeTroop.upgradeTroopGeneratorBuilding();
                                                        System.out.println("Cavalry Generator has been upgraded!");
                                                    }
                                                }

                                                if (toUpgradeTroop.cost.stone != 0) {
                                                    if (map.villages[i][j].resources.stone < 10) {
                                                        System.out.println("Invalid funds!");
                                                    } else {
                                                        map.villages[i][j].resources.deductStone(10);
                                                        toUpgradeTroop.upgradeTroopGeneratorBuilding();
                                                        System.out.println("Archery Generator has been upgraded!");
                                                    }
                                                }


                                                if (toUpgradeTroop.cost.wood != 0) {
                                                    if (map.villages[i][j].resources.wood < 10) {
                                                        System.out.println("Invalid funds!");
                                                    } else {
                                                        map.villages[i][j].resources.deductWood(10);
                                                        toUpgradeTroop.upgradeTroopGeneratorBuilding();
                                                        System.out.println("Ground Generator has been upgraded!");
                                                    }
                                                }
                                            } else {
                                                throw new NoBuildingsException("No troop generating buildings built!");
                                            }
                                        } catch (NoBuildingsException n) {
                                            System.out.println(n.getMessage());
                                        }
                                        break;
                                    default:
                                        System.out.println("Uh oh, something went wrong");
                                        break;
                                }

                                break;
                            case 2: //train troops
                                System.out.println("1. Train Cavalry Troops\n2. Train Archers\n3. Train Ground Troops");
                                int option = getIndex(4, false);
                                switch (option) { // CHECK FOR BUILDINGS BEFORE !!!
                                    case 1://train cavalry
                                        for (TroopGeneratorBuilding t : map.villages[i][j].troopBuildings) {
                                            if(t instanceof CavalryGenerator) {
                                                CavalryGenerator cavalryGen = (CavalryGenerator)t;
                                                cavalryGen.train(i, j);
                                            }
                                        }
                                        /* testing purposes - for(TroopGeneratorBuilding t: map.villages[i][j].troopBuildings) {
                                            System.out.println(t.toString());
                                        }*/
                                        break;
                                    case 2: //train archers
                                        for (TroopGeneratorBuilding t : map.villages[i][j].troopBuildings) {
                                            if (t instanceof ArcherGenerator) {
                                                ArcherGenerator archerGen = (ArcherGenerator)t;
                                                archerGen.train(i, j);
                                            }
                                        }
                                        break;
                                    case 3://train ground troops
                                        for (TroopGeneratorBuilding t : map.villages[i][j].troopBuildings) {
                                            if (t instanceof GroundGenerator) {
                                                GroundGenerator groundGen = (GroundGenerator)t;
                                                groundGen.train(i, j);
                                            }
                                        }
                                        System.out.println(map.villages[i][j].ownedTroops.size());
                                        break;
                                    default:
                                        System.out.println("Uh oh, something went wrong!");
                                        break;
                                }
                                break;
                            case 3: //attack another village with an army
                                try{
                                if ((map.villages[i][j].ownedTroops.size() != 0)) {
                                    // || (map.villages[i][j].ownedTroops.size()!=0 &&(map.villages[i][j].ownedTroops.size()) == (map.villages[i][j].awayTroops.size()))


                                    // LOGIC
                                    // ask for how much of each troop to be added - ADD ERROR HANDLING!
                                    Scanner input = new Scanner(System.in);
                                    System.out.println("How many Archer Troops would you like to add in this army?");
                                    int archerRequested = input.nextInt();
                                    System.out.println("How many Cavalry Troops would you like to add in this army?");
                                    int cavalryRequested = input.nextInt();
                                    System.out.println("How many Ground Troops would you like to add in this army?");
                                    int groundRequested = input.nextInt();


                                    // check if enough troops of each type are available
                                    ArrayList<Troop> availableTroops = map.villages[i][j].getAvailableTroops();
                                    int[] availableTroopTypes = map.villages[i][j].getAvailableTroopTypes(availableTroops);
                                    if(!map.villages[i][j].checkSufficientTroopTypes(cavalryRequested, archerRequested, groundRequested, availableTroopTypes)){  //check if enough troops of each type are available
                                       continue;
                                    }else{
                                        // add troops of each amount and type required to arrayList
                                    }

                                    // enter village co-ordinates to attack
                                    // create Army a = new Army(...)
                                    // add created army to troops away

                                } else { //if no troops are created/all are out on attack
                                    throw new NoTroopsOwnedException("No troops available!");
                                }
                                }catch(NoTroopsOwnedException t){
                                    System.out.println(t.getMessage());
                                }

                                break;
                            case 4: //surrender - village destroyed and player eliminated
                                System.out.println("Player " + map.villages[i][j].owner.name + " you have surrendered!");
                                playerList.remove(map.villages[i][j].owner);
                                map.destroyVillageFromMap(i, j);
                                if (playerCount != 1) {
                                    playerCount--;
                                }

                                break;
                            default:
                                System.out.println("Something went wrong...");
                                break;
                        }


                    }
                }


                // update marching armies' locations according to marching speed and target.


            }

        } while (playerCount != 1); //win condition
    }

    public int getIndex(int upperBound, boolean zeroIncluded) {
        Scanner s = new Scanner(System.in);
        boolean repeat = true;
        int choice = 0;

        while (repeat) {
            try {
                System.out.println("Enter your choice");
                choice = s.nextInt();

                if (zeroIncluded) {
                    if (choice >= 0 && choice < upperBound) {
                        repeat = false;
                    }
                } else {
                    if (choice > 0 && choice < upperBound) {
                        repeat = false;
                    }
                }

            } catch (InputMismatchException exception) {
                System.out.println("Please enter a valid number.");
                s.nextLine();
            }
        }
        return choice;
    }

    public int getBuildingType(boolean resource) {
        Scanner s = new Scanner(System.in);
        boolean repeat = true;
        int choice = 0;

        while (repeat) {

            if (resource) { // if true display resource buildings
                System.out.println("1. Meat Generating\n2. Stone Generating\n3. Wood Generating");
            } else {
                System.out.println("1. Cavalry Generating\n2. Archer Generating\n3. Ground Generating");
            }

            try {
                System.out.println("Enter your desired building type: ");
                choice = s.nextInt();

                if (choice > 0 && choice < 4) {
                    repeat = false;
                }

            } catch (InputMismatchException exception) {
                System.out.println("Please enter a valid number from 1 to 3.");
                s.nextLine();
                repeat = true;
            }
        }
        return choice;
    }

    public void printActions() {
        System.out.println("\t--- Player Actions ---");
        System.out.println("1. Build/Upgrade Buildings\n2. Train Troops\n3. Attack another village with an army\n4. Surrender\n5. Pass your turn");
    }


    public int playerActions() {
        Scanner s = new Scanner(System.in);
        boolean repeat = true;
        int choice = 0;

        printActions();

        while (repeat) {
            try {
                System.out.println("Enter your choice");
                choice = s.nextInt();

                if (choice > 0 && choice < 6) {
                    repeat = false;
                }

            } catch (InputMismatchException exception) {
                System.out.println("Please enter a valid number from 1 to 5.");
                s.nextLine();
                repeat = true;
            }
        }

        return choice;
    }

    public void createVillages(ArrayList<Player> playerList) {
        Map m = Map.getInstance();
        System.out.println(playerList.size());
        for (Player p : playerList) {
            Village v = new Village(p);
            m.addVillageOnMap(v);
        }

    }

    public ArrayList<Player> createPlayers(int[] counts) {
        // create players
        ArrayList<Player> players = new ArrayList<>();
        String name;
        Scanner s = new Scanner(System.in);
        for (int i = 0; i < counts[0]; i++) {
            System.out.println("Enter name for Player " + (i + 1));
            name = s.nextLine();
            Player p = new Player(name, false);
            //System.out.println(p.name);  test purposes
            //System.out.println(p.playerNo); test purposes
            players.add(p);

        }
        //creating AI Players
        if (counts[1] != 0) {
            for (int i = 0; i < counts[1]; i++) {
                name = "AI" + (i + 1);
                Player p = new Player(name, true);
                //System.out.println(p.name); test purposes
                // System.out.println(p.playerNo); test purposes
                players.add(p);

            }
            System.out.println("AI Players have been created!");
        }

        return players;
    }

    public void welcomeMessage() { // tbc
        System.out.println("Welcome to the Village War Game!\n");
        System.out.println("\t--- How to Play ---");
        System.out.println(". . . ");

        System.out.println(" ");
    }

    public int[] getDesiredPlayerCount() {
        Scanner s = new Scanner(System.in);
        int inpersonPlayers = 0, AIPlayers = 0;
        int[] players = new int[3];
        boolean repeat = true;

        while (repeat) {
            try {
                System.out.println("How many in-person players will be playing?");
                inpersonPlayers = s.nextInt();

                System.out.println("How many AI players will be playing?");
                AIPlayers = s.nextInt();

                if (inpersonPlayers > 0 && AIPlayers >= 0 && inpersonPlayers + AIPlayers <= 10) {
                    repeat = false;
                }

            } catch (InputMismatchException exception) {
                System.out.println("Please enter a valid integer");
                s.nextLine();
                repeat = true;
            }
        }

        // System.out.println(inpersonPlayers); test purposes
        players[0] = inpersonPlayers;
        // System.out.println(AIPlayers); test purposes
        players[1] = AIPlayers;
        players[2] = inpersonPlayers + AIPlayers;

        return players;
    }
}
