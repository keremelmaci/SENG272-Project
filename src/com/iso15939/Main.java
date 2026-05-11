package com.iso15939;

import com.iso15939.ui.MainFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // If the system look and feel is not available, Swing will use the default one.
            }
            new MainFrame().setVisible(true);
        });
    }
}
