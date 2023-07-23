package Enums;

import java.util.EnumMap;
import java.util.Map;

public enum GasType {
    PROPANE,
    METHANE,
    ETHANE,
    BUTANE;

    private static final Map<GasType, Double> COEFFICIENTS = new EnumMap(GasType.class);

    static {
        COEFFICIENTS.put(PROPANE, 0.517);
        COEFFICIENTS.put(METHANE, 0.715);
        COEFFICIENTS.put(ETHANE, 0.641);
        COEFFICIENTS.put(BUTANE, 0.601);
    }
    public double getCoefficients(){
        return COEFFICIENTS.get(this);
    }
}
