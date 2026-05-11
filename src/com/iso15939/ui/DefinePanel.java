package com.iso15939.ui;

import com.iso15939.data.ScenarioRepository;
import com.iso15939.model.AppState;
import com.iso15939.model.QualityType;
import com.iso15939.model.Scenario;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

public class DefinePanel extends JPanel implements WizardStepPanel {
    private final AppState appState;
    private final ScenarioRepository repository;
    private final JRadioButton productRadio = new JRadioButton("Product Quality", true);
    private final JRadioButton processRadio = new JRadioButton("Process Quality");
    private final JRadioButton educationRadio = new JRadioButton("Education", true);
    private final JRadioButton healthRadio = new JRadioButton("Health");
    private final JComboBox<Scenario> scenarioComboBox = new JComboBox<>();
    private final JLabel scenarioDescription = new JLabel();

    public DefinePanel(AppState appState, ScenarioRepository repository) {
        this.appState = appState;
        this.repository = repository;
        setLayout(new BorderLayout());
        setBackground(UITheme.BACKGROUND);
        add(new TitlePanel(
                "Step 2 · Define Scope",
                "Pick exactly one quality type, one mode and one ready-made scenario."
        ), BorderLayout.NORTH);
        add(createContent(), BorderLayout.CENTER);
        updateScenarioList();
    }

    private JPanel createContent() {
        JPanel wrapper = new JPanel(new BorderLayout(0, 20));
        wrapper.setBackground(UITheme.BACKGROUND);
        wrapper.setBorder(BorderFactory.createEmptyBorder(18, 28, 28, 28));

        JPanel topPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        topPanel.setOpaque(false);
        topPanel.add(createQualityTypePanel());
        topPanel.add(createModePanel());

        wrapper.add(topPanel, BorderLayout.CENTER);
        wrapper.add(createScenarioPanel(), BorderLayout.SOUTH);
        return wrapper;
    }

    private JPanel createQualityTypePanel() {
        JPanel panel = createChoiceCard("2a", "Quality Type", "Defines whether the measurement focuses on the software product or the development process.");

        ButtonGroup qualityGroup = new ButtonGroup();
        qualityGroup.add(productRadio);
        qualityGroup.add(processRadio);
        panel.add(styleRadio(productRadio, "Performance, security, usability, reliability"));
        panel.add(Box.createVerticalStrut(10));
        panel.add(styleRadio(processRadio, "Sprint efficiency, code quality, collaboration"));
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    private JPanel createModePanel() {
        JPanel panel = createChoiceCard("2b", "Mode", "Select the dataset family that will supply dimensions, metrics and raw values.");

        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(educationRadio);
        modeGroup.add(healthRadio);

        educationRadio.addActionListener(e -> updateScenarioList());
        healthRadio.addActionListener(e -> updateScenarioList());

        panel.add(styleRadio(educationRadio, "Learning management system scenarios"));
        panel.add(Box.createVerticalStrut(10));
        panel.add(styleRadio(healthRadio, "Health management system scenarios"));
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    private JPanel createChoiceCard(String number, String title, String description) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        UITheme.makeSurface(panel);
        panel.setBorder(UITheme.cardBorder());

        JLabel badge = new JLabel(number);
        badge.setOpaque(true);
        badge.setBackground(UITheme.BLUE_SOFT);
        badge.setForeground(UITheme.BLUE);
        badge.setFont(badge.getFont().deriveFont(Font.BOLD, 13f));
        badge.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 22f));
        titleLabel.setForeground(UITheme.INK);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(12, 0, 4, 0));

        JLabel descLabel = new JLabel("<html><div style='width:420px;'>" + description + "</div></html>");
        descLabel.setForeground(UITheme.MUTED);
        descLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 18, 0));

        panel.add(badge);
        panel.add(titleLabel);
        panel.add(descLabel);
        return panel;
    }

    private JPanel styleRadio(JRadioButton radioButton, String detail) {
        JPanel row = new JPanel(new BorderLayout(8, 0));
        row.setBackground(UITheme.BACKGROUND);
        row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.LINE),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        radioButton.setOpaque(false);
        radioButton.setFont(radioButton.getFont().deriveFont(Font.BOLD, 14f));
        radioButton.setForeground(UITheme.INK);
        JLabel detailLabel = new JLabel(detail);
        detailLabel.setForeground(UITheme.MUTED);
        detailLabel.setFont(detailLabel.getFont().deriveFont(12f));
        row.add(radioButton, BorderLayout.NORTH);
        row.add(detailLabel, BorderLayout.SOUTH);
        return row;
    }

    private JPanel createScenarioPanel() {
        JPanel panel = new JPanel(new BorderLayout(14, 10));
        UITheme.makeSurface(panel);
        panel.setBorder(UITheme.cardBorder());

        JLabel title = new JLabel("2c · Scenario Selection");
        title.setForeground(UITheme.INK);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 19f));

        scenarioDescription.setForeground(UITheme.MUTED);
        scenarioDescription.setText("Each mode has at least two scenarios. Pick one to continue.");

        scenarioComboBox.setFont(scenarioComboBox.getFont().deriveFont(14f));
        scenarioComboBox.addActionListener(e -> updateSelectedScenarioText());

        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setOpaque(false);
        titlePanel.add(title);
        titlePanel.add(scenarioDescription);

        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(scenarioComboBox, BorderLayout.CENTER);
        return panel;
    }

    private void updateScenarioList() {
        String selectedMode = educationRadio.isSelected() ? "Education" : "Health";
        List<Scenario> scenarios = repository.getScenariosByMode(selectedMode);
        scenarioComboBox.removeAllItems();
        for (Scenario scenario : scenarios) {
            scenarioComboBox.addItem(scenario);
        }
        updateSelectedScenarioText();
    }

    private void updateSelectedScenarioText() {
        Scenario scenario = (Scenario) scenarioComboBox.getSelectedItem();
        if (scenario == null) {
            scenarioDescription.setText("Choose a mode first, then select one scenario.");
        } else {
            scenarioDescription.setText("Selected mode: " + scenario.getMode() + "  |  Scenario ID: " + scenario.getId());
        }
    }

    @Override
    public void onEnterStep() {
        productRadio.setSelected(appState.getQualityType() == QualityType.PRODUCT);
        processRadio.setSelected(appState.getQualityType() == QualityType.PROCESS);
        educationRadio.setSelected("Education".equals(appState.getMode()));
        healthRadio.setSelected("Health".equals(appState.getMode()));
        updateScenarioList();

        Scenario selectedScenario = appState.getScenario();
        if (selectedScenario != null) {
            for (int i = 0; i < scenarioComboBox.getItemCount(); i++) {
                if (scenarioComboBox.getItemAt(i).getId().equals(selectedScenario.getId())) {
                    scenarioComboBox.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    @Override
    public boolean validateStep() {
        Scenario scenario = (Scenario) scenarioComboBox.getSelectedItem();
        if (scenario == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a scenario to continue.",
                    "Missing Scenario",
                    JOptionPane.WARNING_MESSAGE
            );
            return false;
        }

        appState.setQualityType(productRadio.isSelected() ? QualityType.PRODUCT : QualityType.PROCESS);
        appState.setMode(educationRadio.isSelected() ? "Education" : "Health");
        appState.setScenario(scenario);
        return true;
    }
}
