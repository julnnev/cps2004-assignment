public class WoodGenerator extends ResourceGeneratorBuilding{
    public WoodGenerator() {
        super();
        this.cost = new Resource(15,0,0);
        this.amountGenerated = new Resource(20, 0, 0);
        this.generates="Wood";
    }

    public void upgradeWoodGen(){
        this.level++;
        this.amountGenerated.wood+=10;
    }
}
