public class GroundGenerator extends TroopGeneratorBuilding{
    public GroundGenerator(){
        super();
        this.cost = new Resource(15,0,0);
    }

    public void train(int i, int j){
        Map m = Map.getInstance();
        if(m.villages[i][j].resources.wood>=10){
            Resource r = new Resource(0, 10, 0);
            GroundTroop ground = new GroundTroop(troopHealth, troopAttack, r, 0.5);
            m.villages[i][j].ownedTroops.add(ground);
        }else{
            System.out.println("Insufficient funds!");
        }
    }
}
