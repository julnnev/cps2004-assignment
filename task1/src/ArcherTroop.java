public class ArcherTroop extends Troop{
    public ArcherTroop(int health, int attack, Resource carryingCapacity, double marchingSpeed) {
        super(health, attack, carryingCapacity, marchingSpeed);
        this.cost = new Resource(0, 10, 0);
    }
}
