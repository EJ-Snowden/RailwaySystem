import java.util.ArrayList;
import java.util.Random;

public class Station{
    int x;
    int y;
    Random rand = new Random();
    int id;
    private static int counter = 1;
    public static ArrayList<Station> stations = new ArrayList<>();

    public Station(){
        this.id = counter++;
        this.x = rand.nextInt(4001);            //randomising coordinates from (0,0) to (4000,4000)
        this.y = rand.nextInt(4001);
    }

    static void addStation(int iter){
        for (int i = 0; i < iter; i++){
            stations.add(new Station());
        }
    }

    @Override
    public String toString(){
        return "Station " + id + ". x-" + x + " y-" + y;
    }
}