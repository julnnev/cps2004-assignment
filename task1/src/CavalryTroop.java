public class CavalryTroop extends Troop{
    public CavalryTroop(int health, int attack, Resource carryingCapacity, double marchingSpeed) {
        super(health, attack, carryingCapacity, marchingSpeed);
        this.cost = new Resource(0, 0, 10);
    }
}
