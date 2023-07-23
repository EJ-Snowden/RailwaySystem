package Enums;

import java.util.EnumMap;
import java.util.Map;

public enum LiquidToxicType {
    ACID,
    AMMONIA,
    BENZENE,
    CHLORINE;
    private static final Map<LiquidToxicType, Double> DENSITY = new EnumMap<>(LiquidToxicType.class);

    static {
        DENSITY.put(ACID, 1.18);
        DENSITY.put(AMMONIA, 0.77);
        DENSITY.put(BENZENE, 0.88);
        DENSITY.put(CHLORINE, 1.55);
    }
    public double getCoefficients(){
        return DENSITY.get(this);
    }
}
