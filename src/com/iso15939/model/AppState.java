package com.iso15939.model;

public class AppState {
    private final Profile profile = new Profile();
    private QualityType qualityType = QualityType.PRODUCT;
    private String mode = "Education";
    private Scenario scenario;

    public Profile getProfile() {
        return profile;
    }

    public QualityType getQualityType() {
        return qualityType;
    }

    public void setQualityType(QualityType qualityType) {
        this.qualityType = qualityType;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }
}
