import Interfaces.Electrical;
import Interfaces.ForPeople;

import java.util.ArrayList;

public class PostOfficeCar extends Cars implements Electrical, ForPeople {
    protected int netWeight;
    protected int grossWeight;
    private boolean trackingSystem;
    private int quantityOfWorkers;
    private int weightOfFurniture;

    public PostOfficeCar(){
        super();
        this.netWeight = 36;
        changeWeight();
        isTrackingNeeded();
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
            this.grossWeight = 16 + passengerSpaceWeight() + netWeight;
        }else this.grossWeight = netWeight;
    }

    public void isTrackingNeeded(){
        trackingSystem = loaded;
    }

    public int passengerSpaceWeight(){
        this.quantityOfWorkers = 10;
        this.weightOfFurniture = 4;
        return (int)(quantityOfWorkers * 0.1) + weightOfFurniture;
    }

    @Override
    public String toString(){
        return "ID: " + this.getID() + ", Name: " + this.getName() + ", Loaded -> " + loaded + ", Gross weight: "
                + grossWeight + ", Quantity of workers: " + quantityOfWorkers + ", Weight of furniture: "
                + weightOfFurniture + ", Tracking system -> " + trackingSystem;
    }
}
