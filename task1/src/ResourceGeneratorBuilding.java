public class ResourceGeneratorBuilding {
    Resource cost;
    int level;
    Resource amountGenerated; //varies depending on level of upgrade

    public ResourceGeneratorBuilding(int level, Resource cost, Resource amountGenerated) {
        this.level = 0;
        this.cost = cost;
        this.amountGenerated = amountGenerated;
    }

    public ResourceGeneratorBuilding() {
        this.level = 0;
    }

    // public void upgradeResourceGeneratorBuilding ...


}

