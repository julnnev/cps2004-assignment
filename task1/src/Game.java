import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
    static int playerCount = 0;

    public void run() {
        ArrayList<Player> playerList;
        Map map = Map.getInstance();

        int[] playerCountByType;
        welcomeMessage();
        playerCountByType = getDesiredPlayerCount(); // [0] - inperson players, [1] - AI Players, [2] total number of desired players

        playerList = createPlayers(playerCountByType);
        // on creating players and their associated village, add village to the map

        createVillages(playerList);

        /*for(Player p: playerList){
           System.out.println(p.name+"\n"+p.playerNo);
        }*/

        System.out.println(playerCount);
        map.printMap();
        int choice = 0;
        String playerSurrendered=" ";
        int x = 0, y = 0;
        Player tempPL = null;
        // game loop
        do {
            for (Player p : playerList) { // loop so that players can take turns
                System.out.println("Player " + p.name + " it's your turn!");
                // friendly troop arrival

                // enemy troop arrival

                //resource earning


                // player actions
                choice = playerActions();
                if (choice == -1) {
                    continue;
                }
                switch (choice) {
                    case 1: // build/upgrade buildings
                        break;
                    case 2: //train troops
                        break;
                    case 3: //attack another village with an army
                        break;
                    case 4: //surrender - village destroyed and player eliminated
                        // get coordinates and player object to delete, no modification allowed in loop
                        for (int i = 0; i < map.mapDimension; i++) {
                            for (int j = 0; j < map.mapDimension; j++) {
                                if (map.villages[i][j] != null && p.equals(map.villages[i][j].owner)) {
                                    playerSurrendered = p.name;
                                    x = i;
                                    y = j;
                                    tempPL = p;
                                }
                            }
                        }
                        break;
                    default:
                        System.out.println("Something went wrong...");
                        break;


                }

                if(choice==4){
                    break; //break from for loop so that player can surrender
                }

            }
            if (choice == 4) {
                if(playerCount!=1){
                    System.out.println("Player " + playerSurrendered + " you have surrendered!");
                    map.destroyVillageFromMap(x, y);
                    playerList.remove(tempPL);
                    playerCount--;
                }else{
                    System.out.println("Player " + playerSurrendered + " you have surrendered!");
                    map.destroyVillageFromMap(x, y);
                    playerList.remove(tempPL);
                    break;
                }
            }
            map.printMap();


            // update marching armies' locations according to marching speed and target.
        } while (playerCount != 1 || playerCount!=0); //win condition
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

        if (choice == 5) {
            return -1;
        } else {
            return choice;
        }

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
        ArrayList<Player> players = new ArrayList<Player>();
        String name;
        Scanner s = new Scanner(System.in);
        for (int i = 0; i < counts[0]; i++) {
            System.out.println("Enter name for Player " + (i + 1));
            name = s.nextLine();
            Player p = new Player(name);
            //System.out.println(p.name);  test purposes
            //System.out.println(p.playerNo); test purposes
            players.add(p);

        }

        //creating AI Players
        if (counts[1] != 0) {
            for (int i = 0; i < counts[1]; i++) {
                name = "AI" + (i + 1);
                Player p = new Player(name);
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
