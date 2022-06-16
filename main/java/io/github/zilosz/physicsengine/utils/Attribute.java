package io.github.zilosz.physicsengine.utils;

public class Attribute {

    private final String name;
    private final String unit;
    private double value;
    private final double minValue;
    private final double maxValue;

    public Attribute(String name, String unit, double value, double minValue, double maxValue) {
        this.name = name;
        this.unit = unit;
        this.value = value;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public boolean isValueWithinBounds(double value) {
        return minValue <= value && value <= maxValue;
    }

    public Attribute getClone() {
        return new Attribute(name, unit, value, minValue, maxValue);
    }
}
