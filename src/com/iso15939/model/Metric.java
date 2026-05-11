package com.iso15939.model;

public class Metric extends NamedElement {
    private final Direction direction;
    private final double min;
    private final double max;
    private final String unit;
    private final double value;

    public Metric(String name,
                  double coefficient,
                  Direction direction,
                  double min,
                  double max,
                  String unit,
                  double value) {
        super(name, coefficient);
        this.direction = direction;
        this.min = min;
        this.max = max;
        this.unit = unit;
        this.value = value;
    }

    public Direction getDirection() {
        return direction;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public String getUnit() {
        return unit;
    }

    public double getValue() {
        return value;
    }

    public String getRangeText() {
        return formatNumber(min) + "–" + formatNumber(max);
    }

    /**
     * ISO 15939 assignment score formula.
     * The final result is clamped to 1.0-5.0 and rounded to the nearest 0.5.
     */
    public double calculateScore() {
        double normalized;
        if (max == min) {
            normalized = 0.0;
        } else if (direction == Direction.HIGHER_BETTER) {
            normalized = 1.0 + ((value - min) / (max - min)) * 4.0;
        } else {
            normalized = 5.0 - ((value - min) / (max - min)) * 4.0;
        }

        double clamped = Math.max(1.0, Math.min(5.0, normalized));
        return Math.round(clamped * 2.0) / 2.0;
    }

    public static String formatNumber(double number) {
        if (number == (long) number) {
            return String.format("%d", (long) number);
        }
        return String.format("%.2f", number);
    }
}
