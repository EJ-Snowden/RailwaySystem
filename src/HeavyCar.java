import Enums.HeavyEnum;
import Enums.HeavyWheelType;
import Interfaces.Freight;

import java.util.ArrayList;

public class HeavyCar extends Cars implements Freight {
    protected int netWeight;
    protected int grossWeight;
    private HeavyEnum breakType;
    private HeavyWheelType wheelType;

    public HeavyCar(){
        super();
        this.netWeight = 32;
        changeWeight();
        generateBreakType();
        generateWheelType();
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
            this.grossWeight = 102 + netWeight;
        }else this.grossWeight = netWeight;
    }

    public void generateBreakType(){
        HeavyEnum[] heavyEnums = HeavyEnum.values();
        int randIndex = rand.nextInt(heavyEnums.length);
        this.breakType = heavyEnums[randIndex];
    }

    public void generateWheelType(){
        HeavyWheelType[] heavyWheelTypes = HeavyWheelType.values();
        int randIndex = rand.nextInt(heavyWheelTypes.length);
        this.wheelType = heavyWheelTypes[randIndex];
    }

    @Override
    public String toString(){
        return "ID: " + this.getID() + ", Name: " + this.getName() + ", Loaded -> " + loaded + ", Gross weight: " + grossWeight
                + ", Type of wheels: " + wheelType + ", Type of breaks: " + breakType;
    }
}
