public class GroundTroop extends Troop{
    public GroundTroop(int health, int attack, Resource carryingCapacity, int marchingSpeed, Resource cost) {
        super(health, attack, carryingCapacity, marchingSpeed);
        this.cost = new Resource(10, 0, 0);
    }
}
