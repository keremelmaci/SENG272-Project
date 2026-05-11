package com.iso15939.model;

public enum QualityType {
    PRODUCT("Product Quality"),
    PROCESS("Process Quality");

    private final String label;

    QualityType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
