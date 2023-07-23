import Enums.RefrigeratedElectricalSystem;
import Enums.RefrigeratedProductsType;
import Interfaces.Electrical;
import Interfaces.Freight;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RefrigeratedCar extends BasicCar implements Electrical, Freight {
    protected int netWeight;
    protected int grossWeight;
    Set<RefrigeratedElectricalSystem> electricalFeatures = new HashSet<>();
    private RefrigeratedProductsType productsType;

    public RefrigeratedCar(){
        super();
        this.netWeight = 29;
        changeWeight();
        generateElectricalFeatures();
        generateProductsType();
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
            this.grossWeight = 59 + netWeight;
        }else this.grossWeight = netWeight;
    }

    public void generateElectricalFeatures(){
        RefrigeratedElectricalSystem[] electricalSystems = RefrigeratedElectricalSystem.values();
        int randomQuantity = rand.nextInt(electricalSystems.length);
        for (int i = 0; i < randomQuantity; i++){
            int randIndex = rand.nextInt(electricalSystems.length);
            electricalFeatures.add(electricalSystems[randIndex]);
        }
    }

    public void generateProductsType(){
        RefrigeratedProductsType[] productsTypes = RefrigeratedProductsType.values();
        int randIndex = rand.nextInt(productsTypes.length);
        this.productsType = productsTypes[randIndex];
    }

    @Override
    public String toString(){
        return "ID: " + this.getID() + ", Name: " + this.getName() + ", Loaded -> " + loaded + ", Gross weight: "
                + grossWeight + ", Type of products: " + productsType + ", Electrical features: " + electricalFeatures.toString();
    }
}
