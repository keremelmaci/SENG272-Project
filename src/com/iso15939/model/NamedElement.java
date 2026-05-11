package com.iso15939.model;

/**
 * Base class used to demonstrate inheritance for named project elements.
 * Dimension and Metric are both named, weighted elements.
 */
public abstract class NamedElement {
    private final String name;
    private final double coefficient;

    protected NamedElement(String name, double coefficient) {
        this.name = name;
        this.coefficient = coefficient;
    }

    public String getName() {
        return name;
    }

    public double getCoefficient() {
        return coefficient;
    }
}
