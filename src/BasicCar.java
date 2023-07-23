import Enums.BasicEnum;
import Interfaces.Freight;

import java.util.ArrayList;

public class BasicCar extends Cars implements Freight {
    int netWeight;
    protected int grossWeight;
    private final int carLength;
    private BasicEnum couplerType;

    public BasicCar(){
        super();
        this.netWeight = 18;
        changeWeight();
        this.carLength = 18;
        generateCouplerType();
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
            this.grossWeight = 28 + netWeight;
        }else this.grossWeight = netWeight;
    }

    public void generateCouplerType(){
        BasicEnum[] basicEnums = BasicEnum.values();
        int randIndex = rand.nextInt(basicEnums.length);
        this.couplerType = basicEnums[randIndex];
    }

    @Override
    public String toString(){
        return "ID: " + this.getID() + ", Name: " + this.getName() + ", Loaded -> " + loaded + ", Gross weight: "
                + grossWeight + ", Car length: " + carLength + ", Type of coupler: " + couplerType;
    }
}