import Interfaces.Electrical;
import Interfaces.ForPeople;

import java.util.ArrayList;

public class RestaurantCar extends Cars implements Electrical, ForPeople {
    protected int netWeight;
    protected int grossWeight;
    private int quantityOfPeople;
    private int levelOfRestaurant;
    private int weightOfEquipment;

    public RestaurantCar(){
        super();
        assignLevel();
        this.netWeight = 37;
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
            this.grossWeight = (int)(quantityOfPeople * 0.1) + weightOfEquipment + netWeight;
        }else this.grossWeight = netWeight;
    }

    public void assignLevel(){
        levelOfRestaurant = (rand.nextInt(3) + 1);
        if (levelOfRestaurant == 1) {
            quantityOfPeople = 10;
            weightOfEquipment = 18;
        } else if (levelOfRestaurant == 2) {
            quantityOfPeople = 30;
            weightOfEquipment = 16;
        }else {
            quantityOfPeople = 50;
            weightOfEquipment = 10;
        }
    }

    @Override
    public String toString(){
        return "ID: " + this.getID() + ", Name: " + this.getName() + ", Loaded -> " + loaded + ", Gross weight: "
                + grossWeight + ", Level of restaurant: " + levelOfRestaurant + ", Quantity of people: " + quantityOfPeople
                + ", Weight of equipment: " + weightOfEquipment;
    }
}
