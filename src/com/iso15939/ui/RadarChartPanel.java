package com.iso15939.ui;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.util.LinkedHashMap;
import java.util.Map;

public class RadarChartPanel extends JPanel {
    private Map<String, Double> scores = new LinkedHashMap<>();

    public RadarChartPanel() {
        setBackground(UITheme.SURFACE);
    }

    public void setScores(Map<String, Double> scores) {
        this.scores = new LinkedHashMap<>(scores);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2 + 8;
        int radius = Math.min(width, height) / 2 - 86;

        if (scores == null || scores.isEmpty() || radius <= 0) {
            g2.setColor(UITheme.MUTED);
            g2.drawString("No data available", 20, 30);
            g2.dispose();
            return;
        }

        int count = scores.size();
        drawGrid(g2, centerX, centerY, radius, count);
        drawScorePolygon(g2, centerX, centerY, radius);
        drawLabels(g2, centerX, centerY, radius);
        g2.dispose();
    }

    private void drawGrid(Graphics2D g2, int centerX, int centerY, int radius, int count) {
        g2.setStroke(new BasicStroke(1f));

        for (int level = 1; level <= 5; level++) {
            double levelRadius = radius * (level / 5.0);
            Path2D polygon = new Path2D.Double();
            for (int i = 0; i < count; i++) {
                Point p = getPoint(centerX, centerY, levelRadius, i, count);
                if (i == 0) {
                    polygon.moveTo(p.x, p.y);
                } else {
                    polygon.lineTo(p.x, p.y);
                }
            }
            polygon.closePath();
            g2.setColor(level == 5 ? new Color(184, 194, 210) : new Color(225, 230, 238));
            g2.draw(polygon);
        }

        g2.setColor(new Color(226, 232, 240));
        for (int i = 0; i < count; i++) {
            Point p = getPoint(centerX, centerY, radius, i, count);
            g2.drawLine(centerX, centerY, p.x, p.y);
        }

        g2.setColor(UITheme.MUTED);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 11f));
        for (int level = 1; level <= 5; level++) {
            int y = (int) Math.round(centerY - radius * (level / 5.0));
            g2.drawString(String.valueOf(level), centerX + 6, y + 4);
        }
    }

    private void drawScorePolygon(Graphics2D g2, int centerX, int centerY, int radius) {
        int count = scores.size();
        Path2D scorePolygon = new Path2D.Double();
        int index = 0;

        for (Double score : scores.values()) {
            double scoreRadius = radius * (score / 5.0);
            Point p = getPoint(centerX, centerY, scoreRadius, index, count);
            if (index == 0) {
                scorePolygon.moveTo(p.x, p.y);
            } else {
                scorePolygon.lineTo(p.x, p.y);
            }
            index++;
        }
        scorePolygon.closePath();

        g2.setColor(new Color(240, 144, 72, 90));
        g2.fill(scorePolygon);
        g2.setColor(UITheme.ACCENT);
        g2.setStroke(new BasicStroke(3f));
        g2.draw(scorePolygon);

        index = 0;
        for (Double score : scores.values()) {
            Point p = getPoint(centerX, centerY, radius * (score / 5.0), index, count);
            g2.setColor(Color.WHITE);
            g2.fillOval(p.x - 5, p.y - 5, 10, 10);
            g2.setColor(UITheme.ACCENT);
            g2.drawOval(p.x - 5, p.y - 5, 10, 10);
            index++;
        }
    }

    private void drawLabels(Graphics2D g2, int centerX, int centerY, int radius) {
        int count = scores.size();
        FontMetrics metrics = g2.getFontMetrics();
        int index = 0;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 11f));
        g2.setColor(UITheme.INK);
        for (Map.Entry<String, Double> entry : scores.entrySet()) {
            Point p = getPoint(centerX, centerY, radius + 42, index, count);
            String label = entry.getKey() + "  " + String.format("%.1f", entry.getValue());
            int textWidth = metrics.stringWidth(label);
            g2.drawString(label, p.x - textWidth / 2, p.y);
            index++;
        }

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 12f));
        g2.setColor(UITheme.MUTED);
        g2.drawString("Scale: 1 = Poor, 5 = Excellent", 14, getHeight() - 18);
    }

    private Point getPoint(int centerX, int centerY, double radius, int index, int count) {
        double angle = -Math.PI / 2 + (2 * Math.PI * index / count);
        int x = (int) Math.round(centerX + radius * Math.cos(angle));
        int y = (int) Math.round(centerY + radius * Math.sin(angle));
        return new Point(x, y);
    }
}
