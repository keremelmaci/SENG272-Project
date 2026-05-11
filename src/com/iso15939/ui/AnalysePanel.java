package com.iso15939.ui;

import com.iso15939.model.AppState;
import com.iso15939.model.Dimension;
import com.iso15939.model.Metric;
import com.iso15939.model.Scenario;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.LinkedHashMap;
import java.util.Map;

public class AnalysePanel extends JPanel implements WizardStepPanel {
    private final AppState appState;
    private final JPanel progressContainer = new JPanel();
    private final RadarChartPanel radarChartPanel = new RadarChartPanel();
    private final JLabel gapLabel = new JLabel();

    public AnalysePanel(AppState appState) {
        this.appState = appState;
        setLayout(new BorderLayout());
        setBackground(UITheme.BACKGROUND);
        add(new TitlePanel(
                "Step 5 · Analyse Results",
                "Dimension averages, radar chart and gap analysis are generated automatically."
        ), BorderLayout.NORTH);
        add(createContent(), BorderLayout.CENTER);
    }

    private JPanel createContent() {
        JPanel content = new JPanel(new GridLayout(1, 2, 20, 0));
        content.setBackground(UITheme.BACKGROUND);
        content.setBorder(BorderFactory.createEmptyBorder(18, 28, 28, 28));

        JPanel leftPanel = new JPanel(new BorderLayout(0, 16));
        leftPanel.setOpaque(false);

        progressContainer.setLayout(new BoxLayout(progressContainer, BoxLayout.Y_AXIS));
        progressContainer.setBackground(UITheme.SURFACE);
        JPanel scoreCard = createCard("5a · Weighted Dimension Scores", progressContainer);
        leftPanel.add(scoreCard, BorderLayout.CENTER);
        leftPanel.add(createGapPanel(), BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(UITheme.cardBorder());
        UITheme.makeSurface(rightPanel);
        JLabel radarTitle = new JLabel("5b · Radar Chart Bonus");
        radarTitle.setFont(radarTitle.getFont().deriveFont(Font.BOLD, 18f));
        radarTitle.setForeground(UITheme.INK);
        radarTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 14, 0));
        radarChartPanel.setPreferredSize(new java.awt.Dimension(420, 420));
        rightPanel.add(radarTitle, BorderLayout.NORTH);
        rightPanel.add(radarChartPanel, BorderLayout.CENTER);

        content.add(leftPanel);
        content.add(rightPanel);
        return content;
    }

    private JPanel createCard(String title, JPanel body) {
        JPanel card = new JPanel(new BorderLayout(0, 12));
        UITheme.makeSurface(card);
        card.setBorder(UITheme.cardBorder());

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(UITheme.INK);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 18f));
        card.add(titleLabel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(body);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(UITheme.SURFACE);
        card.add(scrollPane, BorderLayout.CENTER);
        return card;
    }

    private JPanel createGapPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        UITheme.makeSurface(panel);
        panel.setBorder(UITheme.cardBorder());

        JLabel title = new JLabel("5c · Gap Analysis");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        title.setForeground(UITheme.INK);
        gapLabel.setVerticalAlignment(JLabel.TOP);
        gapLabel.setForeground(UITheme.INK);

        panel.add(title, BorderLayout.NORTH);
        panel.add(gapLabel, BorderLayout.CENTER);
        return panel;
    }

    @Override
    public void onEnterStep() {
        progressContainer.removeAll();
        Scenario scenario = appState.getScenario();
        if (scenario == null) {
            return;
        }

        Map<String, Double> scores = new LinkedHashMap<>();
        Dimension lowestDimension = null;
        double lowestScore = Double.MAX_VALUE;

        for (Dimension dimension : scenario.getDimensions()) {
            double score = dimension.calculateWeightedScore();
            scores.put(dimension.getName(), score);
            progressContainer.add(createScoreBar(dimension, score));

            if (score < lowestScore) {
                lowestScore = score;
                lowestDimension = dimension;
            }
        }

        radarChartPanel.setScores(scores);
        updateGapAnalysis(lowestDimension, lowestScore);

        progressContainer.revalidate();
        progressContainer.repaint();
    }

    private JPanel createScoreBar(Dimension dimension, double score) {
        JPanel panel = new JPanel(new BorderLayout(12, 6));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(8, 0, 12, 0));

        JLabel label = new JLabel(dimension.getName() + "  ·  coeff " + Metric.formatNumber(dimension.getCoefficient()));
        label.setFont(label.getFont().deriveFont(Font.BOLD, 13f));
        label.setForeground(UITheme.INK);

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue((int) Math.round((score / 5.0) * 100.0));
        progressBar.setString(Metric.formatNumber(score) + " / 5.0");
        progressBar.setStringPainted(true);
        progressBar.setForeground(score >= 4.0 ? UITheme.GREEN : UITheme.ACCENT);
        progressBar.setBackground(new java.awt.Color(232, 237, 245));
        progressBar.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));

        panel.add(label, BorderLayout.NORTH);
        panel.add(progressBar, BorderLayout.CENTER);
        return panel;
    }

    private void updateGapAnalysis(Dimension lowestDimension, double score) {
        if (lowestDimension == null) {
            gapLabel.setText("No dimension data is available.");
            return;
        }

        double gap = 5.0 - score;
        String qualityLevel = getQualityLevel(score);
        gapLabel.setText("<html>" +
                "<div style='line-height:1.55;'>" +
                "<b>Lowest dimension:</b> " + lowestDimension.getName() + "<br>" +
                "<b>Score:</b> " + Metric.formatNumber(score) + " / 5.0<br>" +
                "<b>Gap value:</b> " + Metric.formatNumber(gap) + "<br>" +
                "<b>Quality level:</b> " + qualityLevel + "<br><br>" +
                "This dimension has the lowest score and requires the most improvement." +
                "</div></html>");
    }

    private String getQualityLevel(double score) {
        if (score >= 4.5) {
            return "Excellent";
        } else if (score >= 3.5) {
            return "Good";
        } else if (score >= 2.5) {
            return "Needs Improvement";
        }
        return "Poor";
    }

    @Override
    public boolean validateStep() {
        return true;
    }
}
