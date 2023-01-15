public class Resource {
    public int meat;
    public int stone;
    public int wood;

    public Resource(int wood, int stone, int meat){
        this.meat=meat;
        this.stone=stone;
        this.wood=wood;
    }

    public Resource(){
        this.meat=50;
        this.stone=50;
        this.wood=50;
    }

    public void addMeat(int meat){ //error checking required! can only add values >0
        this.meat += meat;
    }

    public void addStone(int stone){
        this.stone += stone;
    }

    public void addWood(int wood){
        this.wood += wood;
    }

    public void deductMeat(int meat){ // error checking required! can only deduct amounts less than or equal to available resource balance
        this.meat -= meat;
    }

    public void deductStone(int stone){
        this.stone -= stone;
    }

    public void deductWood(int wood){
        this.wood -= wood;
    }

    public String toString(){
        return "Current Resources\nWood: " + this.wood + "\nStone: " + this.stone + "\nMeat: " + this.meat;
    }
}
