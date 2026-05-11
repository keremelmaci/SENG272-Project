package com.iso15939.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scenario {
    private final String id;
    private final String name;
    private final String mode;
    private final List<Dimension> dimensions = new ArrayList<>();

    public Scenario(String id, String name, String mode) {
        this.id = id;
        this.name = name;
        this.mode = mode;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMode() {
        return mode;
    }

    public void addDimension(Dimension dimension) {
        dimensions.add(dimension);
    }

    public List<Dimension> getDimensions() {
        return Collections.unmodifiableList(dimensions);
    }

    @Override
    public String toString() {
        return name;
    }
}
