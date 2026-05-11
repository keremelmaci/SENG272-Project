package com.iso15939.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dimension extends NamedElement {
    private final List<Metric> metrics = new ArrayList<>();

    public Dimension(String name, double coefficient) {
        super(name, coefficient);
    }

    public void addMetric(Metric metric) {
        metrics.add(metric);
    }

    public List<Metric> getMetrics() {
        return Collections.unmodifiableList(metrics);
    }

    public double calculateWeightedScore() {
        double weightedTotal = 0.0;
        double coefficientTotal = 0.0;

        for (Metric metric : metrics) {
            weightedTotal += metric.calculateScore() * metric.getCoefficient();
            coefficientTotal += metric.getCoefficient();
        }

        if (coefficientTotal == 0.0) {
            return 0.0;
        }
        return weightedTotal / coefficientTotal;
    }
}
