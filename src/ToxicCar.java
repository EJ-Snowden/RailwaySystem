import Enums.ToxicType;
import Interfaces.Freight;
import Interfaces.Toxic;

import java.util.ArrayList;

public class ToxicCar extends HeavyCar implements Toxic, Freight {
    protected int netWeight;
    protected int grossWeight;
    private boolean emergencySystem;
    private ToxicType toxicType;

    public ToxicCar(){
        super();
        this.netWeight = 12;
        changeWeight();
        emergencySystemIsNeeded();
        generateToxicType();
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
            this.grossWeight = 22 + netWeight;
        }else this.grossWeight = netWeight;
    }

    public void emergencySystemIsNeeded(){
        emergencySystem = loaded;
    }

    public void generateToxicType(){
        ToxicType[] toxicTypes = ToxicType.values();
        int randIndex = rand.nextInt(toxicTypes.length);
        this.toxicType = toxicTypes[randIndex];
    }

    @Override
    public String toString(){
        return "ID: " + this.getID() + ", Name: " + this.getName() + ", Loaded -> " + loaded + ", Gross weight: " + grossWeight + ", Type of toxic: "
                + toxicType + ", Emergency system?: " + emergencySystem;
    }
}
