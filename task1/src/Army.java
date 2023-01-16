import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class Army {

    ArrayList<Troop> troops;
    int totalAttack;
    double marchingSpeed;
    double[] currentLocation;
    double[] target;
    double[] base;
    boolean arrivedAtTarget;
    boolean arrivedAtBase;
    boolean successfulAttack;
    boolean arrivedX;
    boolean arrivedY;

    public Army(ArrayList<Troop> troops, double[] currentLocation, double[] target) {
        this.troops = troops;

        for(Troop t: troops){
            this.totalAttack += t.attack;  //total of all troops in troop
        }

        Troop lowestMarchingTroop = troops.stream().min(Comparator.comparingDouble(Troop::getMarchingSpeed)).orElseThrow(NoSuchElementException::new); // slowest of all troops
        this.marchingSpeed = lowestMarchingTroop.marchingSpeed;

        this.currentLocation = currentLocation;
        this.base=currentLocation;
        this.target = target;
        this.arrivedAtTarget = false;
        this.arrivedAtBase = false;
        this.successfulAttack=false;
        this.arrivedX=false;
        this.arrivedY=false;
    }

    public void march(){
        if (this.target[0] > this.currentLocation[0]) {
            this.currentLocation[0] += this.marchingSpeed;
        }
        if (this.target[0] < this.currentLocation[0]) {
            this.currentLocation[0] -= this.marchingSpeed;
        }
        if ((Math.abs(this.target[0] - this.currentLocation[0])) < this.marchingSpeed) {
            this.arrivedX = true;
        }

        if (this.arrivedX) {
            if (this.target[1] > this.currentLocation[1]) {
                this.currentLocation[1] += this.marchingSpeed;
            }
            if (this.target[1] < this.currentLocation[1]) {
                this.currentLocation[1] -= this.marchingSpeed;
            }

            if ((Math.abs(this.target[1] - this.currentLocation[1])) < this.marchingSpeed) {
                this.arrivedY = true;
            }
        }

        if (this.arrivedY && this.arrivedX) {
            this.arrivedAtTarget = true;
        }
    }


    public Resource getTotalCarryingCapacity(){
        Resource resource = new Resource(0,0,0);
        for(Troop t: troops){
            if(t.health!=0){
                if(t instanceof CavalryTroop){
                    resource.addMeat(t.carryingCapacity.meat);
                }
                if(t instanceof ArcherTroop){
                    resource.addStone(t.carryingCapacity.stone);
                }
                if(t instanceof GroundTroop){
                    resource.addWood(t.carryingCapacity.wood);
                }
            }
        }
        return resource;
    }

    public void updateMarchingSpeed(){
        for(Troop t: troops){
            if(t.health!=0){
                Troop lowestMarchingTroop = troops.stream().min(Comparator.comparingDouble(Troop::getMarchingSpeed)).orElseThrow(NoSuchElementException::new); // slowest of all troops
                this.marchingSpeed = lowestMarchingTroop.marchingSpeed;
            }
        }
    }
}
