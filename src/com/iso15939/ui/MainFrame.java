package com.iso15939.ui;

import com.iso15939.data.ScenarioRepository;
import com.iso15939.model.AppState;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {
    private final AppState appState = new AppState();
    private final ScenarioRepository repository = new ScenarioRepository();
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);
    private final StepIndicatorPanel stepIndicatorPanel = new StepIndicatorPanel();
    private final List<WizardStepPanel> steps = new ArrayList<>();
    private final JButton backButton = new JButton("← Back");
    private final JButton nextButton = new JButton("Next →");
    private int currentStep = 0;

    public MainFrame() {
        super("ISO 15939 Measurement Lab");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1120, 760));
        setSize(1200, 810);
        setLocationRelativeTo(null);

        createSteps();
        createLayout();
        updateNavigation();
    }

    private void createSteps() {
        addStep(new ProfilePanel(appState));
        addStep(new DefinePanel(appState, repository));
        addStep(new PlanPanel(appState));
        addStep(new CollectPanel(appState));
        addStep(new AnalysePanel(appState));
    }

    private void addStep(WizardStepPanel stepPanel) {
        steps.add(stepPanel);
        cardPanel.add((JPanel) stepPanel, "step" + (steps.size() - 1));
    }

    private void createLayout() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(UITheme.BACKGROUND);
        cardPanel.setBackground(UITheme.BACKGROUND);

        add(stepIndicatorPanel, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);

        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        navigationPanel.setBackground(UITheme.BACKGROUND);
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(12, 24, 18, 24));
        UITheme.styleSecondaryButton(backButton);
        UITheme.stylePrimaryButton(nextButton);
        navigationPanel.add(backButton);
        navigationPanel.add(nextButton);

        backButton.addActionListener(e -> goBack());
        nextButton.addActionListener(e -> goNext());

        add(navigationPanel, BorderLayout.SOUTH);
    }

    private void goBack() {
        if (currentStep > 0) {
            currentStep--;
            showCurrentStep();
        }
    }

    private void goNext() {
        WizardStepPanel currentPanel = steps.get(currentStep);
        if (!currentPanel.validateStep()) {
            return;
        }

        if (currentStep == steps.size() - 1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Measurement process completed successfully.",
                    "Completed",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        currentStep++;
        showCurrentStep();
    }

    private void showCurrentStep() {
        steps.get(currentStep).onEnterStep();
        cardLayout.show(cardPanel, "step" + currentStep);
        updateNavigation();
    }

    private void updateNavigation() {
        stepIndicatorPanel.updateStep(currentStep);
        backButton.setEnabled(currentStep > 0);
        nextButton.setText(currentStep == steps.size() - 1 ? "Finish ✓" : "Next →");
    }
}
