import Interfaces.Electrical;
import Interfaces.ForPeople;

import java.util.ArrayList;

public class PassengerCar extends Cars implements Electrical, ForPeople {
    public int netWeight;
    public int grossWeight;
    private int quantityOfPeople;
    private int classOfCar;

    public PassengerCar(){
        super();
        assignClass();
        this.netWeight = 36;
        changeWeight();
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
            this.grossWeight = (int)(quantityOfPeople * 0.1) + netWeight;
        }else this.grossWeight = netWeight;
    }

    public void assignClass(){
        int carsClass = (rand.nextInt(2) + 1);
        classOfCar = carsClass;
        if (carsClass == 1) quantityOfPeople = 40;
        else quantityOfPeople = 80;
    }

    @Override
    public String toString(){
        return "ID: " + this.getID() + ", Name: " + this.getName() + ", Loaded -> " + loaded + ", Gross weight: " + grossWeight + ", Class of car: "
                + classOfCar + ", Quantity of people: " + quantityOfPeople;
    }
}
