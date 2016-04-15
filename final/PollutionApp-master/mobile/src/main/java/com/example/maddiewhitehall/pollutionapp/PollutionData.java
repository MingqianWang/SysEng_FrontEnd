package com.example.maddiewhitehall.pollutionapp;

/**
 * Created by mingqianwang on 13/04/16.
 */
public class PollutionData {

    private Double value;
    private String unit;
    private Double rawValue;
    private String rawUnit;

    PollutionData( double value, String unit, double rawValue, String rawUnit) {
        this.value = value;
        this.unit = unit;
        this.rawValue = rawValue;
        this.rawUnit = rawUnit;
    }

    public Double getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    public Double getRawValue() {
        return rawValue;
    }

    public String getRawUnit() {
        return rawUnit;
    }


}
