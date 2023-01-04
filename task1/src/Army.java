import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class Army {
    ArrayList<Troop> troops;
    int totalAttack;
    int marchingSpeed;
    int[] currentLocation;
    int[] target;
    boolean arrivedAtTarget;

    public Army(ArrayList<Troop> troops, int[] currentLocation, int[] target) {
        this.troops = troops;

        for(Troop t: troops){
            this.totalAttack += t.attack;  //total of all troops in troop
        }


        Troop lowestMarchingTroop = troops.stream().min(Comparator.comparingInt(Troop::getMarchingSpeed)).orElseThrow(NoSuchElementException::new); // slowest of all troops
        this.marchingSpeed = lowestMarchingTroop.marchingSpeed;

        this.currentLocation = currentLocation;
        this.target = target;
        this.arrivedAtTarget = false;
    }
}
