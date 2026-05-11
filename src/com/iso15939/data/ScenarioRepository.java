package com.iso15939.data;

import com.iso15939.model.Dimension;
import com.iso15939.model.Direction;
import com.iso15939.model.Metric;
import com.iso15939.model.Scenario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Central place for all hard-coded scenario data.
 * It uses HashMap and ArrayList as required in the assignment.
 */
public class ScenarioRepository {
    private final Map<String, List<Scenario>> scenariosByMode = new HashMap<>();

    public ScenarioRepository() {
        loadScenarios();
    }

    public List<String> getModes() {
        return new ArrayList<>(scenariosByMode.keySet());
    }

    public List<Scenario> getScenariosByMode(String mode) {
        return scenariosByMode.getOrDefault(mode, Collections.emptyList());
    }

    private void addScenario(Scenario scenario) {
        scenariosByMode.computeIfAbsent(scenario.getMode(), key -> new ArrayList<>()).add(scenario);
    }

    private void loadScenarios() {
        addScenario(createEducationScenarioC());
        addScenario(createEducationScenarioD());
        addScenario(createHealthScenarioA());
        addScenario(createHealthScenarioB());
    }

    private Scenario createEducationScenarioC() {
        Scenario scenario = new Scenario("EDU-C", "Scenario C — Team Alpha", "Education");

        Dimension usability = new Dimension("Usability", 25);
        usability.addMetric(new Metric("SUS score", 50, Direction.HIGHER_BETTER, 0, 100, "points", 89));
        usability.addMetric(new Metric("Onboarding time", 50, Direction.LOWER_BETTER, 0, 60, "min", 5));

        Dimension performance = new Dimension("Performance Efficiency", 20);
        performance.addMetric(new Metric("Video start time", 50, Direction.LOWER_BETTER, 0, 15, "sec", 3));
        performance.addMetric(new Metric("Concurrent exams", 50, Direction.HIGHER_BETTER, 0, 600, "users", 510));

        Dimension accessibility = new Dimension("Accessibility", 20);
        accessibility.addMetric(new Metric("WCAG compliance", 50, Direction.HIGHER_BETTER, 0, 100, "%", 82));
        accessibility.addMetric(new Metric("Screen reader score", 50, Direction.HIGHER_BETTER, 0, 100, "%", 76));

        Dimension reliability = new Dimension("Reliability", 20);
        reliability.addMetric(new Metric("Uptime", 50, Direction.HIGHER_BETTER, 95, 100, "%", 99.1));
        reliability.addMetric(new Metric("MTTR", 50, Direction.LOWER_BETTER, 0, 120, "min", 28));

        Dimension functional = new Dimension("Functional Suitability", 15);
        functional.addMetric(new Metric("Feature completion", 50, Direction.HIGHER_BETTER, 0, 100, "%", 91));
        functional.addMetric(new Metric("Assignment submit rate", 50, Direction.HIGHER_BETTER, 0, 100, "%", 86));

        scenario.addDimension(usability);
        scenario.addDimension(performance);
        scenario.addDimension(accessibility);
        scenario.addDimension(reliability);
        scenario.addDimension(functional);
        return scenario;
    }

    private Scenario createEducationScenarioD() {
        Scenario scenario = new Scenario("EDU-D", "Scenario D — Team Beta", "Education");

        Dimension usability = new Dimension("Usability", 25);
        usability.addMetric(new Metric("SUS score", 50, Direction.HIGHER_BETTER, 0, 100, "points", 68));
        usability.addMetric(new Metric("Onboarding time", 50, Direction.LOWER_BETTER, 0, 60, "min", 22));

        Dimension performance = new Dimension("Performance Efficiency", 20);
        performance.addMetric(new Metric("Video start time", 50, Direction.LOWER_BETTER, 0, 15, "sec", 7));
        performance.addMetric(new Metric("Concurrent exams", 50, Direction.HIGHER_BETTER, 0, 600, "users", 430));

        Dimension accessibility = new Dimension("Accessibility", 20);
        accessibility.addMetric(new Metric("WCAG compliance", 50, Direction.HIGHER_BETTER, 0, 100, "%", 64));
        accessibility.addMetric(new Metric("Screen reader score", 50, Direction.HIGHER_BETTER, 0, 100, "%", 58));

        Dimension reliability = new Dimension("Reliability", 20);
        reliability.addMetric(new Metric("Uptime", 50, Direction.HIGHER_BETTER, 95, 100, "%", 97.5));
        reliability.addMetric(new Metric("MTTR", 50, Direction.LOWER_BETTER, 0, 120, "min", 61));

        Dimension functional = new Dimension("Functional Suitability", 15);
        functional.addMetric(new Metric("Feature completion", 50, Direction.HIGHER_BETTER, 0, 100, "%", 78));
        functional.addMetric(new Metric("Assignment submit rate", 50, Direction.HIGHER_BETTER, 0, 100, "%", 74));

        scenario.addDimension(usability);
        scenario.addDimension(performance);
        scenario.addDimension(accessibility);
        scenario.addDimension(reliability);
        scenario.addDimension(functional);
        return scenario;
    }

    private Scenario createHealthScenarioA() {
        Scenario scenario = new Scenario("HLT-A", "Scenario A — Hospital Portal", "Health");

        Dimension usability = new Dimension("Usability", 22);
        usability.addMetric(new Metric("Task success rate", 50, Direction.HIGHER_BETTER, 0, 100, "%", 84));
        usability.addMetric(new Metric("Average task time", 50, Direction.LOWER_BETTER, 0, 20, "min", 6));

        Dimension security = new Dimension("Security", 23);
        security.addMetric(new Metric("MFA adoption", 50, Direction.HIGHER_BETTER, 0, 100, "%", 93));
        security.addMetric(new Metric("Open vulnerabilities", 50, Direction.LOWER_BETTER, 0, 40, "issues", 7));

        Dimension reliability = new Dimension("Reliability", 20);
        reliability.addMetric(new Metric("Uptime", 50, Direction.HIGHER_BETTER, 95, 100, "%", 99.3));
        reliability.addMetric(new Metric("Incident recovery", 50, Direction.LOWER_BETTER, 0, 180, "min", 35));

        Dimension performance = new Dimension("Performance Efficiency", 20);
        performance.addMetric(new Metric("Patient lookup time", 50, Direction.LOWER_BETTER, 0, 10, "sec", 2.5));
        performance.addMetric(new Metric("Concurrent users", 50, Direction.HIGHER_BETTER, 0, 1000, "users", 850));

        Dimension maintainability = new Dimension("Maintainability", 15);
        maintainability.addMetric(new Metric("Code coverage", 50, Direction.HIGHER_BETTER, 0, 100, "%", 71));
        maintainability.addMetric(new Metric("Bug fix cycle", 50, Direction.LOWER_BETTER, 0, 30, "days", 9));

        scenario.addDimension(usability);
        scenario.addDimension(security);
        scenario.addDimension(reliability);
        scenario.addDimension(performance);
        scenario.addDimension(maintainability);
        return scenario;
    }

    private Scenario createHealthScenarioB() {
        Scenario scenario = new Scenario("HLT-B", "Scenario B — Appointment System", "Health");

        Dimension usability = new Dimension("Usability", 22);
        usability.addMetric(new Metric("Patient satisfaction", 50, Direction.HIGHER_BETTER, 0, 100, "points", 72));
        usability.addMetric(new Metric("Booking steps", 50, Direction.LOWER_BETTER, 1, 10, "steps", 5));

        Dimension security = new Dimension("Security", 23);
        security.addMetric(new Metric("Audit log coverage", 50, Direction.HIGHER_BETTER, 0, 100, "%", 88));
        security.addMetric(new Metric("Unauthorized attempts", 50, Direction.LOWER_BETTER, 0, 80, "events", 16));

        Dimension reliability = new Dimension("Reliability", 20);
        reliability.addMetric(new Metric("Successful bookings", 50, Direction.HIGHER_BETTER, 0, 100, "%", 81));
        reliability.addMetric(new Metric("Failed notifications", 50, Direction.LOWER_BETTER, 0, 50, "messages", 11));

        Dimension performance = new Dimension("Performance Efficiency", 20);
        performance.addMetric(new Metric("Search response time", 50, Direction.LOWER_BETTER, 0, 8, "sec", 3));
        performance.addMetric(new Metric("Peak load capacity", 50, Direction.HIGHER_BETTER, 0, 2000, "requests", 1250));

        Dimension maintainability = new Dimension("Maintainability", 15);
        maintainability.addMetric(new Metric("Automated tests", 50, Direction.HIGHER_BETTER, 0, 100, "%", 62));
        maintainability.addMetric(new Metric("Hotfix frequency", 50, Direction.LOWER_BETTER, 0, 20, "per month", 6));

        scenario.addDimension(usability);
        scenario.addDimension(security);
        scenario.addDimension(reliability);
        scenario.addDimension(performance);
        scenario.addDimension(maintainability);
        return scenario;
    }
}
