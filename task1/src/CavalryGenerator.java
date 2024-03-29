public class CavalryGenerator extends TroopGeneratorBuilding{
    public CavalryGenerator(){
        super();
        this.cost = new Resource(0,0,15);
    }

    public void train(int i, int j){
        Map m = Map.getInstance();
        if(m.villages[i][j].resources.stone>=10){
            Resource capacity = new Resource(0, 0, 10);
            CavalryTroop cavalry = new CavalryTroop(troopHealth, troopAttack, capacity, 0.75);
            m.villages[i][j].ownedTroops.add(cavalry);
        }else{
            System.out.println("Insufficient funds!");
        }
    }
}
