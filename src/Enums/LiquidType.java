package Enums;

import java.util.EnumMap;
import java.util.Map;

public enum LiquidType {
    WATER,
    MILK,
    GASOLINE,
    OLIVEOIL;

    private static final Map<LiquidType, Double> DENSITY = new EnumMap<>(LiquidType.class);

    static {
        DENSITY.put(WATER, 1.0);
        DENSITY.put(MILK, 1.03);
        DENSITY.put(GASOLINE, 0.72);
        DENSITY.put(OLIVEOIL, 0.92);
    }
    public double getCoefficients(){
        return DENSITY.get(this);
    }
}
