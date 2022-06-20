package com.example.conversion.model;

public class ConversionResponse {
    private final double value;
    private final String unit;

    public ConversionResponse(double value, String unit) {
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return this.value;
    }

    public String getUnit() {
        return this.unit;
    }
}
