public class Player {

    public String name;
    public int playerNo; // digit displayed on map of villages
    boolean AI;

    public Player(String name, boolean AI) {
        this.name = name;
        this.playerNo = Game.playerCount + 1;
        this.AI=AI;
        //incrementing total amount of players
        Game.playerCount++;
    }

}
