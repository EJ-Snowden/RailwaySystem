import java.util.ArrayList;
import java.util.Random;

public abstract class Cars{
    private static int counter = 1;
    private final int id;
    private final String name;
    protected Random rand = new Random();
    protected boolean loaded;

    public Cars(){
        this.id = counter;
        counter++;
        name = this.getClass().getSimpleName();
        loaded = rand.nextBoolean();
    }

    protected abstract int getGrossWeight();
    @Override
    public String toString(){
        return "ID: " + id + " Name: " + name + " Loaded? " + loaded;
    }
    public static void minusCounter(){
        counter--;
    }
    public abstract boolean restrictionOnAdding(ArrayList<Object> trainset);

    public int getID(){
        return id;
    }

    public String getName(){
        return name;
    }
    public abstract void changeWeight();
}