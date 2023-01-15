import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class Army {
    ArrayList<Troop> troops;
    int totalAttack;
    double marchingSpeed;
    double[] currentLocation;
    int[] target;
    boolean arrivedAtTarget;
    boolean arrivedAtBase;
    boolean successfulAttack;
    boolean arrivedX;
    boolean arrivedY;

    public Army(ArrayList<Troop> troops, double[] currentLocation, int[] target) {
        this.troops = troops;

        for(Troop t: troops){
            this.totalAttack += t.attack;  //total of all troops in troop
        }

        Troop lowestMarchingTroop = troops.stream().min(Comparator.comparingDouble(Troop::getMarchingSpeed)).orElseThrow(NoSuchElementException::new); // slowest of all troops
        this.marchingSpeed = lowestMarchingTroop.marchingSpeed;

        this.currentLocation = currentLocation;
        this.target = target;
        this.arrivedAtTarget = false;
        this.arrivedAtBase = false;
        this.successfulAttack=false;
        this.arrivedX=false;
        this.arrivedY=false;
    }
}
