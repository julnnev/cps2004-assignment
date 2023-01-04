public class Troop {
    int health;
    int attack;
    Resource carryingCapacity;
    int marchingSpeed;
    Resource cost;

    public Troop(int health, int attack, Resource carryingCapacity, int marchingSpeed, Resource cost) {
        this.health = health;
        this.attack = attack;
        this.carryingCapacity = carryingCapacity;
        this.marchingSpeed = marchingSpeed;
        this.cost = cost;
    }

    public Troop(int health, int attack, Resource carryingCapacity, int marchingSpeed) {
        this.health = health;
        this.attack = attack;
        this.carryingCapacity = carryingCapacity;
        this.marchingSpeed = marchingSpeed;
    }

    public Troop(){

    }

    int getMarchingSpeed(){
        return marchingSpeed;
    }
}
