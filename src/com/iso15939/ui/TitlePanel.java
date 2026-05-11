package com.iso15939.ui;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class TitlePanel extends JPanel {
    public TitlePanel(String title, String subtitle) {
        setLayout(new BorderLayout(12, 0));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(22, 28, 10, 28));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 24f));
        titleLabel.setForeground(UITheme.INK);

        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setForeground(UITheme.MUTED);
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0));

        JPanel text = new JPanel(new BorderLayout());
        text.setOpaque(false);
        text.add(titleLabel, BorderLayout.NORTH);
        text.add(subtitleLabel, BorderLayout.CENTER);
        add(text, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(255, 255, 255, 225));
        g2.fillRoundRect(18, 12, getWidth() - 36, getHeight() - 16, 22, 22);
        g2.setColor(UITheme.ACCENT);
        g2.fillRoundRect(28, 24, 5, getHeight() - 36, 8, 8);
        g2.dispose();
        super.paintComponent(g);
    }
}
