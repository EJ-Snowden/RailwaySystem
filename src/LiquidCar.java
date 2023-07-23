import Enums.LiquidType;
import Interfaces.Freight;
import Interfaces.Liquid;

import java.util.ArrayList;
import java.util.Random;

public class LiquidCar extends BasicCar implements Liquid, Freight {
    protected int netWeight;
    protected int grossWeight;
    private double volumeOfLiquid;
    private LiquidType liquidType;
    Random rand = new Random();


    public LiquidCar(){
        super();
        this.netWeight = 13;
        changeWeight();
        generateLiquidType();
        calcVolume();
    }

    @Override
    protected int getGrossWeight() {
        return grossWeight;
    }
    @Override
    public boolean restrictionOnAdding(ArrayList<Object> trainset) {
        return trainset.stream().anyMatch(o -> {
            for (Class<?> clas : TrainSet.passengerListClasses) {
                if (clas.isInstance(o)) return true;
            }
            return false;
        });
    }

    @Override
    public void changeWeight() {
        if (loaded){
            this.grossWeight = 76 + netWeight;
        }else this.grossWeight = netWeight;
    }

    public void calcVolume(){
        volumeOfLiquid = (grossWeight - netWeight) / liquidType.getCoefficients();
    }

    public void generateLiquidType(){
        LiquidType[] liquidTypes = LiquidType.values();
        int randIndex = rand.nextInt(liquidTypes.length);
        this.liquidType = liquidTypes[randIndex];
    }

    @Override
    public String toString(){
        return "ID: " + this.getID() + ", Name: " + this.getName() + ", Loaded -> " + loaded + ", Gross weight: " + grossWeight
                + ", Type of liquid: " + liquidType + ", Volume of liquid: " + volumeOfLiquid;
    }
}
