public class Player {

    public String name;
    public int playerNo; // digit displayed on map of villages

    public Player(String name) {
        this.name = name;
        this.playerNo = Game.playerCount + 1;

        //incrementing total amount of players
        Game.playerCount++;
    }

}
