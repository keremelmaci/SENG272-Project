package com.iso15939.ui;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

public class StepIndicatorPanel extends JPanel {
    private static final String[] STEP_NAMES = {"Profile", "Define", "Plan", "Collect", "Analyse"};
    private final List<JPanel> stepCards = new ArrayList<>();
    private final List<JLabel> stepNumbers = new ArrayList<>();
    private final List<JLabel> stepNames = new ArrayList<>();

    public StepIndicatorPanel() {
        setLayout(new BorderLayout(24, 0));
        setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));
        setOpaque(false);
        setPreferredSize(new Dimension(1100, 118));

        add(createBrandPanel(), BorderLayout.WEST);
        add(createStepsPanel(), BorderLayout.CENTER);
        updateStep(0);
    }

    private JPanel createBrandPanel() {
        JPanel brand = new JPanel();
        brand.setOpaque(false);
        brand.setLayout(new BoxLayout(brand, BoxLayout.Y_AXIS));
        brand.setPreferredSize(new Dimension(260, 78));

        JLabel title = new JLabel("ISO 15939 LAB");
        title.setForeground(Color.WHITE);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 24f));

        JLabel subtitle = new JLabel("Measurement Process Simulator");
        subtitle.setForeground(new Color(194, 204, 220));
        subtitle.setFont(subtitle.getFont().deriveFont(12f));
        subtitle.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        brand.add(Box.createVerticalGlue());
        brand.add(title);
        brand.add(subtitle);
        brand.add(Box.createVerticalGlue());
        return brand;
    }

    private JPanel createStepsPanel() {
        JPanel steps = new JPanel(new GridLayout(1, STEP_NAMES.length, 10, 0));
        steps.setOpaque(false);

        for (int i = 0; i < STEP_NAMES.length; i++) {
            JPanel card = new JPanel(new BorderLayout(10, 0));
            card.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
            card.setOpaque(true);

            JLabel number = new JLabel(String.format("%02d", i + 1), JLabel.CENTER);
            number.setOpaque(true);
            number.setPreferredSize(new Dimension(42, 42));
            number.setFont(number.getFont().deriveFont(Font.BOLD, 14f));

            JLabel name = new JLabel(STEP_NAMES[i]);
            name.setFont(name.getFont().deriveFont(Font.BOLD, 13f));

            card.add(number, BorderLayout.WEST);
            card.add(name, BorderLayout.CENTER);

            stepCards.add(card);
            stepNumbers.add(number);
            stepNames.add(name);
            steps.add(card);
        }
        return steps;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(new java.awt.GradientPaint(0, 0, UITheme.NAVY, getWidth(), 0, UITheme.NAVY_2));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
        super.paintComponent(g);
    }

    public void updateStep(int activeStep) {
        for (int i = 0; i < stepCards.size(); i++) {
            JPanel card = stepCards.get(i);
            JLabel number = stepNumbers.get(i);
            JLabel name = stepNames.get(i);

            if (i < activeStep) {
                card.setBackground(new Color(36, 66, 83));
                number.setText("✓");
                number.setBackground(UITheme.GREEN);
                number.setForeground(Color.WHITE);
                name.setText(STEP_NAMES[i]);
                name.setForeground(new Color(218, 244, 230));
            } else if (i == activeStep) {
                card.setBackground(new Color(255, 250, 244));
                number.setText(String.format("%02d", i + 1));
                number.setBackground(UITheme.ACCENT);
                number.setForeground(Color.WHITE);
                name.setText("Current: " + STEP_NAMES[i]);
                name.setForeground(UITheme.NAVY);
            } else {
                card.setBackground(new Color(45, 60, 91));
                number.setText(String.format("%02d", i + 1));
                number.setBackground(new Color(69, 84, 119));
                number.setForeground(new Color(204, 213, 230));
                name.setText(STEP_NAMES[i]);
                name.setForeground(new Color(204, 213, 230));
            }
        }
    }
}
