import Enums.GasType;
import Interfaces.Freight;

import java.util.ArrayList;
import java.util.Random;

public class GaseousCar extends BasicCar implements Freight {
    protected int netWeight;
    protected int grossWeight;
    private final double specificGravity;
    private double volumeOfGas;
    private GasType gasType;
    Random rand = new Random();

    public GaseousCar(){
        super();
        this.netWeight = 7;
        changeWeight();
        specificGravity = 1.52;
        generateGasType();
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
            this.grossWeight = 20 + netWeight;
        }else this.grossWeight = netWeight;
    }

    public void calcVolume(){
        this.volumeOfGas = ((grossWeight - netWeight) * 1000) / (specificGravity * gasType.getCoefficients());
    }

    public void generateGasType(){
        GasType[] gasTypes = GasType.values();
        int randIndex = rand.nextInt(gasTypes.length);
        this.gasType = gasTypes[randIndex];
    }

    @Override
    public String toString(){
        return "ID: " + this.getID() + ", Name: " + this.getName() + ", Loaded -> " + loaded + ", Gross weight: " + grossWeight
                + ", Gas type: " + gasType + ", Volume of gas: " + volumeOfGas;
    }
}
