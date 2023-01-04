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

        map.printMap();



    }

    public void createVillages(ArrayList<Player> playerList){
        Map m = Map.getInstance();
        System.out.println(playerList.size());
        for(Player p: playerList){
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
