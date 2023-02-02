import exceptions.NoBuildingsException;
import exceptions.NoTroopsOwnedException;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.function.Predicate;

public class Game {
    static int playerCount = 0;

    public void run() {
        int choice;
        ArrayList<Player> playerList;
        int[] playerCountByType;

        Map map = Map.getInstance();

        welcomeMessage();

        playerCountByType = getDesiredPlayerCount(); // [0] - in person players, [1] - AI Players, [2] total number of desired players

        playerList = createPlayers(playerCountByType);

        createVillages(playerList); // on creating players and their associated village, add village to the map

        do { // game loop
            for (int i = 0; i < map.mapDimension; i++) {
                for (int j = 0; j < map.mapDimension; j++) {
                    if (map.villages[i][j] != null) {
                        if (playerCount == 1) {
                            System.out.println("Congratulations " + map.villages[i][j].owner.name + " you have won the game!");
                            break;
                        }

                        if (!map.villages[i][j].owner.AI) {
                            System.out.println("Player " + map.villages[i][j].owner.name + " it's your turn!");
                        }

                        if (map.villages[i][j].owner.AI) {
                            System.out.println("An AI Player is taking its turn.");
                        }

                        // FRIENDLY TROOP ARRIVAL
                        for (Army army : map.villages[i][j].activeArmies) { // an army is a list of troops
                            if (army.arrivedAtBase) {
                                for (Troop t : army.troops) { // for each troop in the army
                                    if (t.health != 0) {
                                        //adding resources to village resources
                                        map.villages[i][j].resources.addWood(t.carryingCapacity.wood);
                                        map.villages[i][j].resources.addStone(t.carryingCapacity.stone);
                                        map.villages[i][j].resources.addMeat(t.carryingCapacity.meat);
                                    }

                                    map.villages[i][j].awayTroops.remove(t);
                                    map.villages[i][j].updateStationedTroops();
                                }

                            }

                        }

                        // safe delete the army from activeArmies once it has arrived to base
                        Iterator<Army> itr = map.villages[i][j].activeArmies.iterator();
                        // Holds true till there is single element remaining in the object
                        while (itr.hasNext()) {
                            // Remove elements  using Iterator.remove()
                            Army x = itr.next();
                            if (x.arrivedAtBase)
                                itr.remove();
                        }


                        // ENEMY TROOP ARRIVAL
                        int incomingAttack, defendingAttack = 0, difference;
                        for (int k = 0; k < map.mapDimension; k++) {
                            for (int l = 0; l < map.mapDimension; l++) {
                                if (map.villages[k][l] != null) {
                                    for (Army incoming : map.villages[k][l].activeArmies) {
                                        // resolve combat conflict for armies that arrived at their target = true and with target[0] = i and target[1] = j
                                        if (incoming.arrivedAtTarget && incoming.target[0] == i && incoming.target[1] == j) {

                                            incomingAttack = incoming.totalAttack;

                                            // get available troops at the village getAvailableTroops(), sum their attack for each troop in the list
                                            map.villages[i][j].updateStationedTroops();
                                            for (Troop troop : map.villages[i][j].stationedTroops) {
                                                defendingAttack += troop.attack;
                                            }

                                            if (!map.villages[i][j].owner.AI) {
                                                System.out.println("Your village is under attack!");
                                                System.out.println("Incoming Attack = " + incomingAttack);
                                                System.out.println("Defending Attack = " + defendingAttack);
                                            }

                                            difference = defendingAttack - incomingAttack;
                                            // Loose a troop -> set the troop health to 0 and remove it from the respective list

                                            //If the attacker's army has 10 attack and the defending army has 20 attack -> Both armies lose 10 troops and the defender wins.
                                            // OR If both armies have 10 attack -> Both armies lose 10 troops and defender wins.
                                            if (incomingAttack < defendingAttack || incomingAttack == defendingAttack) {
                                                incoming.successfulAttack = false;

                                                if (difference != 0) {
                                                    // Defending and Attacking armies loose troops
                                                    for (int counter = 1; counter <= difference; counter++) {
                                                        map.villages[i][j].stationedTroops.get(counter).health = 0;
                                                        incoming.troops.get(counter).health = 0;
                                                    }

                                                } else {
                                                    for (int counter = 1; counter <= incomingAttack; counter++) {
                                                        map.villages[i][j].stationedTroops.get(counter).health = 0;
                                                        incoming.troops.get(counter).health = 0;
                                                    }
                                                }
                                            }

                                            //If the attacker's army has 10 attack and the defending army has 5 attack -> Both armies lose 5 troops and attacker wins.
                                            if (incomingAttack > defendingAttack) {
                                                incoming.successfulAttack = true;
                                                // Defending and Attacking armies loose troops
                                                for (int counter = 1; counter <= difference; counter++) {
                                                    map.villages[i][j].stationedTroops.get(counter).health = 0;
                                                    incoming.troops.get(counter).health = 0;

                                                }
                                            }


                                            // update stationed troops and owned troops in attacked (defending) village
                                            // remove those troops in owned troops whose health==0 in stationed troops
                                            ArrayList<Troop> deadStationedTroops = new ArrayList<>();
                                            for (Troop troop : map.villages[i][j].stationedTroops) {
                                                if (troop.health == 0) {
                                                    deadStationedTroops.add(troop);
                                                }
                                            }
                                            map.villages[i][j].ownedTroops.removeAll(deadStationedTroops);

                                            Predicate<Troop> condition = troop -> (troop.health == 0);
                                            map.villages[i][j].stationedTroops.removeIf(condition);


                                            // update incoming troops and owned troops in attacking village
                                            incoming.troops.removeIf(condition);
                                            // remove those troops in owned troops of attacking village whose health==0 in incoming troops
                                            ArrayList<Troop> deadIncomingTroops = new ArrayList<>();
                                            for (Troop troop : incoming.troops) {
                                                if (troop.health == 0) {
                                                    deadIncomingTroops.add(troop);
                                                }
                                            }
                                            map.villages[k][l].ownedTroops.removeAll(deadIncomingTroops);

                                            if (incoming.successfulAttack) {
                                                if (!map.villages[i][j].owner.AI) {
                                                    System.out.println("The incoming army has completed a successful attack on your village!");
                                                }

                                                // reducing village health
                                                map.villages[i][j].health -= incomingAttack;
                                                // reducing resources
                                                Resource resourcesTaken = incoming.getTotalCarryingCapacity();
                                                map.villages[i][j].resources.deductMeat(resourcesTaken.meat);
                                                map.villages[i][j].resources.deductStone(resourcesTaken.stone);
                                                map.villages[i][j].resources.deductWood(resourcesTaken.wood);

                                                // start marching back - set target coordinates
                                                incoming.target = incoming.base;

                                            } else {
                                                if (!map.villages[i][j].owner.AI) {
                                                    System.out.println("The attacking army has failed to successfully attack your village");
                                                }

                                                //  map.villages[k][l].activeArmies.remove(incoming); moved to  iterator
                                            }
                                        }
                                    }

                                    Predicate<Army> condition = a -> !a.successfulAttack && a.arrivedAtTarget;
                                    map.villages[k][l].activeArmies.removeIf(condition);

                                }
                            }




                            if (map.villages[i][j] != null) {

                                // resource earning - wait one round to get resources/upgrades, until buildings are built/upgraded
                                if(!map.villages[i][j].owner.AI){
                                    map.printMap();
                                    System.out.println(map.villages[i][j].resources.toString()); // replaces System.out.println("Current Resources\nWood: " + map.villages[i][j].resources.wood + "\nStone: " + map.villages[i][j].resources.stone + "\nMeat: " + map.villages[i][j].resources.meat);
                                }
                                map.villages[i][j].updateResources();


                                // PLAYER ACTIONS
                                if(!map.villages[i][j].owner.AI) {
                                    choice = playerActions();
                                    if (choice == 5) {
                                        continue; // indicates pass the turn
                                    }
                                }
                                else{
                                    choice = getAIChoice(1,5);
                                }

                                switch (choice) {
                                    case 1: // BUILD/UPGRADE BUILDINGS
                                        int select;
                                        if(!map.villages[i][j].owner.AI){
                                            System.out.println("1. Build Resource Generator Buildings\n2. Build Troop Generating Buildings\n3. Upgrade Resource Generator Buildings\n4. Upgrade Troop Generating Buildings\n");
                                             select = getOption(5, false);
                                        }else{
                                            select = getAIChoice(1,4);
                                        }

                                        switch (select) {
                                            case 1: //build resource gen
                                                int option;
                                                if(!map.villages[i][j].owner.AI){
                                                    System.out.println("Time to build Resource Generator Buildings!");
                                                    option = getBuildingType(true);
                                                }else{
                                                    option = getAIChoice(1,3);
                                                }
                                                switch (option) {
                                                    case 1: //meat
                                                        map.villages[i][j].buildMeatGenerator();
                                                        break;
                                                    case 2: // stone
                                                        map.villages[i][j].buildStoneGenerator();
                                                        break;
                                                    case 3: // wood
                                                        map.villages[i][j].buildWoodGenerator();
                                                        break;
                                                    default:
                                                        System.out.println("Uh oh, something went wrong");
                                                        break;

                                                }
                                                break;
                                            case 2: //build troop gen
                                                if(!map.villages[i][j].owner.AI) {
                                                    System.out.println("Time to Build Troop Generator Buildings!\n");
                                                    option = getBuildingType(false);
                                                }else{
                                                    option = getAIChoice(1,3);
                                                }
                                                switch (option) {
                                                    case 1: //cavalry
                                                        map.villages[i][j].buildCavalryGenerator();
                                                        break;
                                                    case 2: // archers
                                                        map.villages[i][j].buildArcherGenerator();
                                                        break;
                                                    case 3: // ground
                                                        map.villages[i][j].buildGroundGenerator();
                                                        break;
                                                }
                                                break;
                                            case 3: //upgrade resource gen
                                                try {
                                                    int selection;
                                                    ResourceGeneratorBuilding toUpgrade;

                                                    if (map.villages[i][j].resourceBuildings.size() != 0) {
                                                        if(!map.villages[i][j].owner.AI){
                                                            map.villages[i][j].displayResourceBuildings();
                                                            System.out.println("Time to Upgrade!");
                                                            selection = getOption(map.villages[i][j].resourceBuildings.size(), true);
                                                        }else{
                                                            selection = getAIChoice(0, map.villages[i][j].resourceBuildings.size()-1);
                                                        }
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
                                                    if(!map.villages[i][j].owner.AI) {
                                                        System.out.println(n.getMessage());
                                                    }
                                                }
                                                break;
                                            case 4: //upgrade troop gen
                                                try {
                                                    int selection;
                                                    TroopGeneratorBuilding toUpgradeTroop;
                                                    if (map.villages[i][j].troopBuildings.size() != 0) {

                                                        if(!map.villages[i][j].owner.AI){ // in person player
                                                            map.villages[i][j].displayTroopBuildings();
                                                            System.out.println("Time to Upgrade!");
                                                            selection = getOption(map.villages[i][j].troopBuildings.size(), true);
                                                        }else{ //AI
                                                            selection = getAIChoice(0, map.villages[i][j].troopBuildings.size()-1);
                                                        }
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
                                                    if(!map.villages[i][j].owner.AI) {
                                                        System.out.println(n.getMessage());
                                                    }
                                                }
                                                break;
                                            default:
                                                System.out.println("Uh oh, something went wrong");
                                                break;
                                        }

                                        break;
                                    case 2: // TRAIN (I.E. CREATE) TROOPS
                                        if (map.villages[i][j].resourceBuildings.size() == 0) {
                                            if(!map.villages[i][j].owner.AI){
                                                System.out.println("No troop buildings available!");
                                            }
                                            break;
                                        }
                                        int option;
                                        if(!map.villages[i][j].owner.AI){
                                            System.out.println("1. Train Cavalry Troops\n2. Train Archers\n3. Train Ground Troops");
                                            option = getOption(4, false);
                                        }else{
                                            option = getAIChoice(1,3);
                                        }

                                        switch (option) {
                                            case 1://train cavalry
                                                for (TroopGeneratorBuilding t : map.villages[i][j].troopBuildings) {
                                                    if (t instanceof CavalryGenerator) {
                                                        CavalryGenerator cavalryGen = (CavalryGenerator) t;
                                                        cavalryGen.train(i, j);
                                                    }
                                                }
                                                break;
                                            case 2: //train archers
                                                for (TroopGeneratorBuilding t : map.villages[i][j].troopBuildings) {
                                                    if (t instanceof ArcherGenerator) {
                                                        ArcherGenerator archerGen = (ArcherGenerator) t;
                                                        archerGen.train(i, j);
                                                    }
                                                }
                                                break;
                                            case 3://train ground troops
                                                for (TroopGeneratorBuilding t : map.villages[i][j].troopBuildings) {
                                                    if (t instanceof GroundGenerator) {
                                                        GroundGenerator groundGen = (GroundGenerator) t;
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
                                    case 3: // ATTACK ANOTHER VILLAGE
                                        try {
                                            if ((map.villages[i][j].ownedTroops.size() == 0)) {
                                                throw new NoTroopsOwnedException("No troops available!\n");
                                            }

                                            int archerRequested, cavalryRequested, groundRequested;

                                            if(!map.villages[i][j].owner.AI){
                                                Scanner input = new Scanner(System.in);
                                                System.out.println("How many Archer Troops would you like to add in this army?");
                                                 archerRequested = input.nextInt();
                                                System.out.println("How many Cavalry Troops would you like to add in this army?");
                                                 cavalryRequested = input.nextInt();
                                                System.out.println("How many Ground Troops would you like to add in this army?");
                                                 groundRequested = input.nextInt();
                                            }else{
                                                archerRequested = getAIChoice(0,3);
                                                cavalryRequested = getAIChoice(0,3);
                                                groundRequested = getAIChoice(0,3);
                                            }


                                            map.villages[i][j].attack(cavalryRequested, archerRequested, groundRequested);

                                        } catch (NoTroopsOwnedException t) {
                                            if(!map.villages[i][j].owner.AI) {
                                                System.out.println(t.getMessage());
                                            }
                                        }

                                        break;
                                    case 4: // SURRENDER - I.E. village destroyed and player eliminated
                                        if(!map.villages[i][j].owner.AI){
                                            System.out.println("Player " + map.villages[i][j].owner.name + " you have surrendered!");
                                        }
                                        playerList.remove(map.villages[i][j].owner);
                                        map.destroyVillageFromMap(i, j);
                                        if (playerCount != 1) {
                                            playerCount--;
                                        }

                                        break;
                                    default:
                                        if(!map.villages[i][j].owner.AI) {
                                            System.out.println("Something went wrong...");
                                        }
                                        break;
                                }

                            }


                            // UPDATING MARCHING ARMIES LOCATION ACCORDING TO SPEED AND TARGET
                            for (int q = 0; q < map.mapDimension; q++) {
                                for (int r = 0; r < map.mapDimension; r++) {
                                    if (map.villages[q][r] != null) {
                                        for (Army army : map.villages[q][r].activeArmies) {
                                            //updating marching speed and determining arrivedAtTarget for armies en route
                                            if (!army.arrivedAtTarget) {
                                                army.march();
                                            }

                                            //  determining arrivedAtBase
                                            if (army.arrivedAtTarget && army.successfulAttack) {
                                                army.updateMarchingSpeed();

                                                // marching back to base
                                                if (!army.arrivedAtBase) {
                                                    army.march();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } while (playerCount != 1); //win condition
    }

    public int getOption(int upperBound, boolean zeroIncluded) {
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
            players.add(p);

        }
        //creating AI Players
        if (counts[1] != 0) {
            for (int i = 0; i < counts[1]; i++) {
                name = "AI" + (i + 1);
                Player p = new Player(name, true);
                players.add(p);

            }
            System.out.println("AI Players have been created!");
        }

        return players;
    }

    public void welcomeMessage() {
        System.out.println("Welcome to the Village War Game!");
        System.out.println("\t--- How to Play ---");
        System.out.println(
                "This game has 3 resources: Wood, Stone and Meat corresponding to Ground, Archers and Cavalry Troops, respectively.\nResources are used to build and upgrade buildings and train troops.\n" +
                        "Before the game starts, please enter how many in-person and AI players will be playing. AI Players are optional\n" +
                        "Each round you will be prompted to perform a player action: build/upgrade buildings, train troops, attack another village, surrender or pass your turn.\n" +

                        "Note that you can either build resource generating or troop generating buildings to earn resources each turn. You can only upgrade once you have buildings built \n" +
                        "Also, training troops requires troop generator buildings \n" +
                        "Enemy and friendly troop arrivals, if any, will take place before you are prompted to perform your action.\n" +
                        "Enjoy and Good Luck!\n");

        System.out.println(" ");
    }

    public int[] getDesiredPlayerCount() {
        Scanner s = new Scanner(System.in);
        int inPersonPlayers = 0, AIPlayers = 0;
        int[] players = new int[3];
        boolean repeat = true;

        while (repeat) {
            try {
                System.out.println("How many in-person players will be playing?");
                inPersonPlayers = s.nextInt();

                System.out.println("How many AI players will be playing?");
                AIPlayers = s.nextInt();

                if (inPersonPlayers > 0 && AIPlayers >= 0 && inPersonPlayers + AIPlayers <= 10) {
                    repeat = false;
                }

            } catch (InputMismatchException exception) {
                System.out.println("Please enter a valid integer");
                s.nextLine();
                repeat = true;
            }
        }

        players[0] = inPersonPlayers;
        players[1] = AIPlayers;
        players[2] = inPersonPlayers + AIPlayers;

        return players;
    }

    public int getAIChoice(int min, int max) {
        return (int) ((Math.random() * (max + 1 - min)) + min);
    }
}
