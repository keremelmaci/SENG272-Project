package com.iso15939.ui;

import com.iso15939.model.AppState;
import com.iso15939.model.Dimension;
import com.iso15939.model.Metric;
import com.iso15939.model.Scenario;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;

public class CollectPanel extends JPanel implements WizardStepPanel {
    private final AppState appState;
    private final DefaultTableModel model;
    private final JLabel summaryLabel = new JLabel("Raw values will be loaded after scenario selection.");

    public CollectPanel(AppState appState) {
        this.appState = appState;
        setLayout(new BorderLayout());
        setBackground(UITheme.BACKGROUND);
        add(new TitlePanel(
                "Step 4 · Collect Data",
                "Scenario values are converted into normalized 1.0–5.0 metric scores."
        ), BorderLayout.NORTH);

        model = new DefaultTableModel(
                new Object[]{"Dimension", "Metric", "Direction", "Range", "Value", "Score", "Coeff / Unit"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        UITheme.styleTable(table);
        table.setAutoCreateRowSorter(true);
        table.getColumnModel().getColumn(5).setCellRenderer(new ScoreCellRenderer());

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(UITheme.LINE));

        JPanel tableCard = new JPanel(new BorderLayout(0, 14));
        UITheme.makeSurface(tableCard);
        tableCard.setBorder(UITheme.cardBorder());
        tableCard.add(createSummaryPanel(), BorderLayout.NORTH);
        tableCard.add(scrollPane, BorderLayout.CENTER);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(UITheme.BACKGROUND);
        wrapper.setBorder(BorderFactory.createEmptyBorder(18, 28, 28, 28));
        wrapper.add(tableCard, BorderLayout.CENTER);
        add(wrapper, BorderLayout.CENTER);
    }

    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        JLabel title = new JLabel("Collected Metric Values");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        title.setForeground(UITheme.INK);
        summaryLabel.setForeground(UITheme.MUTED);
        panel.add(title, BorderLayout.WEST);
        panel.add(summaryLabel, BorderLayout.EAST);
        return panel;
    }

    @Override
    public void onEnterStep() {
        model.setRowCount(0);
        Scenario scenario = appState.getScenario();
        if (scenario == null) {
            summaryLabel.setText("No scenario selected.");
            return;
        }

        int metricCount = 0;
        for (Dimension dimension : scenario.getDimensions()) {
            for (Metric metric : dimension.getMetrics()) {
                model.addRow(new Object[]{
                        dimension.getName(),
                        metric.getName(),
                        metric.getDirection().getLabel(),
                        metric.getRangeText(),
                        Metric.formatNumber(metric.getValue()),
                        Metric.formatNumber(metric.calculateScore()),
                        Metric.formatNumber(metric.getCoefficient()) + " / " + metric.getUnit()
                });
                metricCount++;
            }
        }
        summaryLabel.setText(metricCount + " metrics · " + scenario.getName());
    }

    @Override
    public boolean validateStep() {
        return true;
    }

    private static class ScoreCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setHorizontalAlignment(SwingConstants.CENTER);
            setFont(getFont().deriveFont(Font.BOLD));
            if (!isSelected) {
                double score = 0.0;
                try {
                    score = Double.parseDouble(value.toString());
                } catch (NumberFormatException ignored) {
                    // Keep neutral color if score parsing fails.
                }
                if (score >= 4.5) {
                    component.setBackground(UITheme.GREEN_SOFT);
                    component.setForeground(new java.awt.Color(30, 110, 68));
                } else if (score >= 3.5) {
                    component.setBackground(UITheme.BLUE_SOFT);
                    component.setForeground(UITheme.BLUE);
                } else {
                    component.setBackground(UITheme.ACCENT_SOFT);
                    component.setForeground(new java.awt.Color(170, 88, 36));
                }
            }
            return component;
        }
    }
}
