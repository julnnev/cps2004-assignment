import java.util.Random;

public class Map {
    final int mapDimension = 10;
    Village[][] villages = new Village[mapDimension][mapDimension];
    private static Map instance;


    private Map(){
        // upon creation of map, assign all cells, i.e. villages, to null
        for(int i=0;i<mapDimension;i++){
            for (int j=0;i<mapDimension;i++){
                villages[i][j]=null;
            }
        }
    }

    public static Map getInstance(){
        if (instance == null){
            instance = new Map();
        }
        return instance;
    }

    public void addVillageOnMap(Village village){
        int randomX, randomY;
        // randomly picks x and y coords [0,4] and allocates the passed village at that location
        Random randomPosition = new Random();

        do {
             randomX = randomPosition.nextInt(mapDimension);
             randomY = randomPosition.nextInt(mapDimension);
        }while(villages[randomX][randomY]!=null); // to ensure no overwriting of villages in cells

        // setting the randomly selected village co-ordinates
        village.setX(randomX);
        village.setY(randomY);

        villages[randomX][randomY] = village;

    }

    public void destroyVillageFromMap(int x, int y){
        villages[x][y] = null;

    }

    public void printMap(){
        System.out.println("\t--- VILLAGE MAP ---");
        System.out.print("\t");
        for (int i = 0; i < mapDimension; i++){

            System.out.print(i+"\t");
        }
        System.out.println();


        for(int i=0;i<mapDimension;i++){
            System.out.print(i+ "\t");
            for (int j=0;j<mapDimension;j++){
                if(villages[i][j]!=null){
                    System.out.print(villages[i][j].owner.playerNo + "\t");
                }else{
                    System.out.print("--\t");
                }
            }
            System.out.println();
        }
    }

}
