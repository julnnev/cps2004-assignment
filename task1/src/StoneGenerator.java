public class StoneGenerator extends ResourceGeneratorBuilding{

    public StoneGenerator(){
        super();
        this.cost = new Resource(0,15,0);
        this.amountGenerated = new Resource(0, 20, 0);
        this.generates="Stone";
    }

    public void upgradeStoneGen(){
        this.level++;
        this.amountGenerated.stone+=10;
    }

}
