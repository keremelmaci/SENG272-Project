package com.iso15939.model;

public enum Direction {
    HIGHER_BETTER("Higher ↑"),
    LOWER_BETTER("Lower ↓");

    private final String label;

    Direction(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
