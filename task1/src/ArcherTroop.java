public class ArcherTroop extends Troop{
    public ArcherTroop(int health, int attack, Resource carryingCapacity, int marchingSpeed, Resource cost) {
        super(health, attack, carryingCapacity, marchingSpeed);
        this.cost = new Resource(0, 10, 0);
    }
}
