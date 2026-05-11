package com.iso15939.ui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

public final class UITheme {
    public static final Color BACKGROUND = new Color(242, 245, 249);
    public static final Color SURFACE = new Color(255, 255, 255);
    public static final Color NAVY = new Color(22, 32, 57);
    public static final Color NAVY_2 = new Color(33, 47, 80);
    public static final Color INK = new Color(42, 48, 60);
    public static final Color MUTED = new Color(105, 116, 132);
    public static final Color LINE = new Color(217, 224, 235);
    public static final Color ACCENT = new Color(240, 144, 72);
    public static final Color ACCENT_SOFT = new Color(255, 239, 225);
    public static final Color GREEN = new Color(70, 166, 117);
    public static final Color GREEN_SOFT = new Color(222, 244, 232);
    public static final Color BLUE = new Color(77, 126, 216);
    public static final Color BLUE_SOFT = new Color(230, 237, 255);

    private UITheme() {
    }

    public static Border cardBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LINE),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        );
    }

    public static Border compactCardBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LINE),
                BorderFactory.createEmptyBorder(12, 14, 12, 14)
        );
    }

    public static void stylePrimaryButton(JButton button) {
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 22, 10, 22));
        button.setBackground(ACCENT);
        button.setForeground(Color.WHITE);
        button.setFont(button.getFont().deriveFont(Font.BOLD, 13f));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void styleSecondaryButton(JButton button) {
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(192, 202, 217)),
                BorderFactory.createEmptyBorder(9, 20, 9, 20)
        ));
        button.setBackground(Color.WHITE);
        button.setForeground(INK);
        button.setFont(button.getFont().deriveFont(Font.BOLD, 13f));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void styleTable(JTable table) {
        table.setRowHeight(34);
        table.setGridColor(new Color(229, 234, 242));
        table.setShowVerticalLines(false);
        table.setFillsViewportHeight(true);
        table.setForeground(INK);
        table.setSelectionBackground(BLUE_SOFT);
        table.setSelectionForeground(INK);
        table.setFont(table.getFont().deriveFont(13f));

        JTableHeader header = table.getTableHeader();
        header.setBackground(NAVY_2);
        header.setForeground(Color.WHITE);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 13f));
        header.setReorderingAllowed(false);
    }

    public static void makeSurface(JComponent component) {
        component.setBackground(SURFACE);
        component.setOpaque(true);
    }
}
