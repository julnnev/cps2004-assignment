public abstract class TroopGeneratorBuilding {
    int level;
    Resource cost;

    // attributes of troop vary depending on level of upgrade of generator building
    int troopAttack;
    int troopHealth;

    public TroopGeneratorBuilding() {
        this.level = 0;
        this.troopAttack = 10;  // upon building start creating troops with troopAttack = 10
        this.troopHealth = 10; // upon building start creating troops with troopHealth = 10
    }
    public void upgradeTroopGeneratorBuilding(){
        level++;
        troopAttack+=10;
        troopHealth+=10;
    }

    public abstract void train(int i, int j);
}
