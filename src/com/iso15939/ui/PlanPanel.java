package com.iso15939.ui;

import com.iso15939.model.AppState;
import com.iso15939.model.Dimension;
import com.iso15939.model.Metric;
import com.iso15939.model.Scenario;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Font;

public class PlanPanel extends JPanel implements WizardStepPanel {
    private final AppState appState;
    private final JPanel dimensionContainer = new JPanel();
    private final JLabel scenarioLabel = new JLabel("No scenario selected yet.");

    public PlanPanel(AppState appState) {
        this.appState = appState;
        setLayout(new BorderLayout());
        setBackground(UITheme.BACKGROUND);
        add(new TitlePanel(
                "Step 3 · Plan Measurement",
                "Review the dimensions and metric definitions. This page is read-only."
        ), BorderLayout.NORTH);

        dimensionContainer.setLayout(new BoxLayout(dimensionContainer, BoxLayout.Y_AXIS));
        dimensionContainer.setBackground(UITheme.BACKGROUND);

        JPanel content = new JPanel(new BorderLayout(0, 14));
        content.setBackground(UITheme.BACKGROUND);
        content.setBorder(BorderFactory.createEmptyBorder(18, 28, 28, 28));
        content.add(createScenarioStrip(), BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(dimensionContainer);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(UITheme.BACKGROUND);
        content.add(scrollPane, BorderLayout.CENTER);
        add(content, BorderLayout.CENTER);
    }

    private JPanel createScenarioStrip() {
        JPanel strip = new JPanel(new BorderLayout());
        UITheme.makeSurface(strip);
        strip.setBorder(UITheme.compactCardBorder());

        JLabel title = new JLabel("Measurement Blueprint");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));
        title.setForeground(UITheme.INK);
        scenarioLabel.setForeground(UITheme.MUTED);

        strip.add(title, BorderLayout.WEST);
        strip.add(scenarioLabel, BorderLayout.EAST);
        return strip;
    }

    @Override
    public void onEnterStep() {
        dimensionContainer.removeAll();
        Scenario scenario = appState.getScenario();
        if (scenario == null) {
            scenarioLabel.setText("No scenario selected yet.");
            return;
        }

        scenarioLabel.setText(scenario.getMode() + " / " + scenario.getName());
        for (Dimension dimension : scenario.getDimensions()) {
            dimensionContainer.add(createDimensionPanel(dimension));
            dimensionContainer.add(Box.createVerticalStrut(14));
        }

        dimensionContainer.revalidate();
        dimensionContainer.repaint();
    }

    private JPanel createDimensionPanel(Dimension dimension) {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        UITheme.makeSurface(panel);
        panel.setBorder(UITheme.cardBorder());

        JLabel title = new JLabel(dimension.getName() + "   ·   Coefficient " + Metric.formatNumber(dimension.getCoefficient()));
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        title.setForeground(UITheme.INK);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));
        panel.add(title, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Metric", "Coefficient", "Direction", "Range", "Unit"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Metric metric : dimension.getMetrics()) {
            model.addRow(new Object[]{
                    metric.getName(),
                    Metric.formatNumber(metric.getCoefficient()),
                    metric.getDirection().getLabel(),
                    metric.getRangeText(),
                    metric.getUnit()
            });
        }

        JTable table = new JTable(model);
        UITheme.styleTable(table);
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createLineBorder(UITheme.LINE));
        panel.add(tableScroll, BorderLayout.CENTER);
        return panel;
    }

    @Override
    public boolean validateStep() {
        return true;
    }
}
