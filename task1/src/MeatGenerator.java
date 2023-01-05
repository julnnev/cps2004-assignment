public class MeatGenerator extends ResourceGeneratorBuilding{
    public MeatGenerator(){
        super();
        this.cost = new Resource(0,0,15);
        this.amountGenerated = new Resource(0, 0, 20);
        this.generates="Meat";
    }

    public void upgradeMeatGen(){
        this.level++;
        this.amountGenerated.meat+=10;
    }
}
