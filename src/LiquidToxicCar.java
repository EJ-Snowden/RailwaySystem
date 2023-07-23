import Enums.LiquidToxicConstrMaterial;
import Enums.LiquidToxicType;
import Interfaces.Freight;
import Interfaces.Liquid;
import Interfaces.Toxic;

import java.util.ArrayList;

public class LiquidToxicCar extends HeavyCar implements Liquid, Toxic, Freight {
    protected int netWeight;
    protected int grossWeight;
    private double volumeOfLiquid;
    private LiquidToxicType liquidToxicType;
    private LiquidToxicConstrMaterial material;
    public boolean labels;

    public LiquidToxicCar(){
        super();
        this.netWeight = 34;
        changeWeight();
        generateLiquidType();
        calcVolume();
        generateMaterialType();
        areLabelsNeeded();
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
            this.grossWeight = 17 + netWeight;
        }else this.grossWeight = netWeight;
    }

    public void calcVolume(){
        volumeOfLiquid = (grossWeight - netWeight) / liquidToxicType.getCoefficients();
    }

    public void generateLiquidType(){
        LiquidToxicType[] liquidToxicTypes = LiquidToxicType.values();
        int randIndex = rand.nextInt(liquidToxicTypes.length);
        this.liquidToxicType = liquidToxicTypes[randIndex];
    }

    public void generateMaterialType(){
        LiquidToxicConstrMaterial[] materials = LiquidToxicConstrMaterial.values();
        int randIndex = rand.nextInt(materials.length);
        this.material = materials[randIndex];
    }

    public void areLabelsNeeded(){
        labels = loaded;
    }

    @Override
    public String toString(){
        return "ID: " + this.getID() + ", Name: " + this.getName() + ", Loaded -> " + loaded + ", Gross weight: " + grossWeight
                + ", Type of toxic liquid: " + liquidToxicType + ", Volume of toxic liquid: " + volumeOfLiquid
                + ", Material of car: " + material + ", Labels -> " + labels;
    }
}
