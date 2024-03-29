public class ArcherGenerator extends TroopGeneratorBuilding{
    public ArcherGenerator(){
        super();
        this.cost = new Resource(0,15,0);
    }

    public void train(int i, int j){
        Map m = Map.getInstance();
        if(m.villages[i][j].resources.stone>=10){
            Resource capacity = new Resource(0, 10, 0);
            ArcherTroop archers = new ArcherTroop(troopHealth, troopAttack, capacity, 0.25);
            m.villages[i][j].ownedTroops.add(archers);
        }else{
            System.out.println("Insufficient funds!");
        }
    }
}
