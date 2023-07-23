import Enums.BaggageMailEnum;
import Interfaces.Freight;

import java.util.ArrayList;
import java.util.Random;

public class BaggageMailCar extends Cars implements Freight {
    protected int netWeight;
    protected int grossWeight;
    private BaggageMailEnum baggageMailEnum;
    private final boolean isLighted;
    Random rand = new Random();

    public BaggageMailCar(){
        super();
        generateBaggageType();
        this.netWeight = 28;
        changeWeight();
        isLighted = rand.nextBoolean();
    }

    @Override
    protected int getGrossWeight() {
        return grossWeight;
    }

    @Override
    public boolean restrictionOnAdding(ArrayList<Object> trainset) {
        return trainset.stream().anyMatch(o -> {
            for (Class<?> clas : TrainSet.cargoListClasses) {
                if (clas.isInstance(o)) return true;
            }
            return false;
        });
    }

    @Override
    public void changeWeight() {
        if (loaded){
            this.grossWeight = 14 + netWeight;
        }else this.grossWeight = netWeight;
    }

    public void generateBaggageType(){
        BaggageMailEnum[] baggageMailEnums = BaggageMailEnum.values();
        int randIndex = rand.nextInt(baggageMailEnums.length);
        this.baggageMailEnum = baggageMailEnums[randIndex];
    }

    @Override
    public String toString(){
        return "ID: " + this.getID() + ", Name: " + this.getName() + ", Loaded -> " + loaded + ", Gross weight: " + grossWeight + ", Type of cargo: "
                + baggageMailEnum + ", Has light inside -> " + isLighted;
    }
}