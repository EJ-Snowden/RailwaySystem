import Enums.ExplosiveItemType;
import Enums.ExplosivesSecurityLevel;
import Interfaces.Freight;

import java.util.ArrayList;

public class ExplosivesCar extends HeavyCar implements Freight {
    protected int netWeight;
    protected int grossWeight;
    private ExplosiveItemType itemType;
    private ExplosivesSecurityLevel securityLevel;

    public ExplosivesCar(){
        super();
        this.netWeight = 19;
        changeWeight();
        generateItemType();
        generateSecurityLevel();
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

    public void generateSecurityLevel(){
        if (!loaded) this.securityLevel = ExplosivesSecurityLevel.LOW;
        else switch (this.itemType){
            case C4, TNT, DYNAMITE -> this.securityLevel = ExplosivesSecurityLevel.ULTRAHIGH;
            case PETN, NITROGLYCERIN -> this.securityLevel = ExplosivesSecurityLevel.HIGH;
            case DETONATINGCORD -> this.securityLevel = ExplosivesSecurityLevel.MEDIUM;
        }
    }

    public void generateItemType(){
        if (loaded){
            ExplosiveItemType[] itemTypes = ExplosiveItemType.values();
            int randIndex = rand.nextInt(itemTypes.length);
            this.itemType = itemTypes[randIndex];
        }else this.itemType = null;
    }

    @Override
    public String toString(){
        return "ID: " + this.getID() + ", Name: " + this.getName() + ", Loaded -> " + loaded + ", Gross weight: " + grossWeight
                + ", Type of cargo: " + itemType + ", Security level: " + securityLevel;
    }
}
