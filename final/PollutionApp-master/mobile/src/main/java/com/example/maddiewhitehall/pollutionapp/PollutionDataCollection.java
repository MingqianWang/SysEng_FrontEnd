package com.example.maddiewhitehall.pollutionapp;

/**
 * Created by mingqianwang on 13/04/16.
 */
public class PollutionDataCollection {
    private PollutionData NO2 = null;
    private PollutionData CO = null;
    private PollutionData light = null;
    private PollutionData noise = null;
    private PollutionData SO2 = null;
    private PollutionData O3 = null;
    private PollutionData PM10 = null;
    private PollutionData PM25 = null;

    PollutionDataCollection(PollutionData no2, PollutionData co, PollutionData light,
                            PollutionData noise, PollutionData so2, PollutionData o3, PollutionData pm10, PollutionData pm25) {
        this.NO2 = no2;
        this.CO = co;
        this.light = light;
        this.noise = noise;
        this.SO2 = so2;
        this.O3 = o3;
        this.PM10 = pm10;
        this.PM25 = pm25;

    }

    public PollutionData getNO2() {
        return NO2;
    }

    public PollutionData getCO() {
        return CO;
    }

    public PollutionData getLight() {
        return light;
    }

    public PollutionData getNoise() {
        return noise;
    }

    public PollutionData getSO2() {
        return SO2;
    }

    public PollutionData getO3() {
        return O3;
    }

    public PollutionData getPM10() {
        return PM10;
    }

    public PollutionData getPM25() {
        return PM25;
    }
}
