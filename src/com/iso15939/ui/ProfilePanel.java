package com.iso15939.ui;

import com.iso15939.model.AppState;
import com.iso15939.model.Profile;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

public class ProfilePanel extends JPanel implements WizardStepPanel {
    private final AppState appState;
    private final JTextField usernameField = new JTextField(24);
    private final JTextField schoolField = new JTextField(24);
    private final JTextField sessionNameField = new JTextField(24);

    public ProfilePanel(AppState appState) {
        this.appState = appState;
        setLayout(new BorderLayout());
        setBackground(UITheme.BACKGROUND);
        add(new TitlePanel(
                "Step 1 · Profile",
                "Create the measurement session before selecting dimensions and metrics."
        ), BorderLayout.NORTH);
        add(createFormPanel(), BorderLayout.CENTER);
    }

    private JPanel createFormPanel() {
        JPanel wrapper = new JPanel(new GridLayout(1, 2, 22, 0));
        wrapper.setBackground(UITheme.BACKGROUND);
        wrapper.setBorder(BorderFactory.createEmptyBorder(18, 28, 28, 28));

        wrapper.add(createIntroCard());
        wrapper.add(createInputCard());
        return wrapper;
    }

    private JPanel createIntroCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        UITheme.makeSurface(card);
        card.setBorder(UITheme.cardBorder());

        JLabel badge = new JLabel("SESSION SETUP");
        badge.setOpaque(true);
        badge.setBackground(UITheme.ACCENT_SOFT);
        badge.setForeground(UITheme.ACCENT);
        badge.setFont(badge.getFont().deriveFont(Font.BOLD, 12f));
        badge.setBorder(BorderFactory.createEmptyBorder(7, 12, 7, 12));

        JLabel headline = new JLabel("Start with identity data");
        headline.setFont(headline.getFont().deriveFont(Font.BOLD, 24f));
        headline.setForeground(UITheme.INK);
        headline.setBorder(BorderFactory.createEmptyBorder(24, 0, 8, 0));

        JLabel info = new JLabel("<html><div style='width:360px; line-height:1.45;'>" +
                "This screen stores the username, school and session name. These fields are checked before the wizard moves to the Define step." +
                "</div></html>");
        info.setForeground(UITheme.MUTED);

        JPanel miniGrid = new JPanel(new GridLayout(3, 1, 0, 10));
        miniGrid.setOpaque(false);
        miniGrid.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        miniGrid.add(createMiniLine("01", "Profile information is entered"));
        miniGrid.add(createMiniLine("02", "Quality type and scenario are selected"));
        miniGrid.add(createMiniLine("03", "Scores and analysis are generated"));

        card.add(badge);
        card.add(headline);
        card.add(info);
        card.add(miniGrid);
        card.add(Box.createVerticalGlue());
        return card;
    }

    private JPanel createMiniLine(String number, String text) {
        JPanel line = new JPanel(new BorderLayout(12, 0));
        line.setOpaque(false);
        JLabel numberLabel = new JLabel(number, JLabel.CENTER);
        numberLabel.setOpaque(true);
        numberLabel.setBackground(UITheme.BLUE_SOFT);
        numberLabel.setForeground(UITheme.BLUE);
        numberLabel.setFont(numberLabel.getFont().deriveFont(Font.BOLD));
        numberLabel.setBorder(BorderFactory.createEmptyBorder(7, 10, 7, 10));
        JLabel textLabel = new JLabel(text);
        textLabel.setForeground(UITheme.INK);
        line.add(numberLabel, BorderLayout.WEST);
        line.add(textLabel, BorderLayout.CENTER);
        return line;
    }

    private JPanel createInputCard() {
        JPanel card = new JPanel(new BorderLayout());
        UITheme.makeSurface(card);
        card.setBorder(UITheme.cardBorder());

        JLabel cardTitle = new JLabel("User Information");
        cardTitle.setFont(cardTitle.getFont().deriveFont(Font.BOLD, 20f));
        cardTitle.setForeground(UITheme.INK);
        cardTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 18, 0));
        card.add(cardTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        styleTextField(usernameField);
        styleTextField(schoolField);
        styleTextField(sessionNameField);

        addRow(formPanel, gbc, 0, "Username", usernameField, "Example: Ebru Elmaci");
        addRow(formPanel, gbc, 1, "School", schoolField, "Example: Ankara University");
        addRow(formPanel, gbc, 2, "Session Name", sessionNameField, "Example: Education LMS Review");

        card.add(formPanel, BorderLayout.CENTER);
        return card;
    }

    private void styleTextField(JTextField field) {
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new java.awt.Color(199, 209, 224)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        field.setFont(field.getFont().deriveFont(14f));
    }

    private void addRow(JPanel panel, GridBagConstraints gbc, int row, String labelText, JTextField field, String hint) {
        gbc.gridx = 0;
        gbc.gridy = row * 3;
        gbc.weightx = 1;
        JLabel label = new JLabel(labelText);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 13f));
        label.setForeground(UITheme.INK);
        panel.add(label, gbc);

        gbc.gridy = row * 3 + 1;
        panel.add(field, gbc);

        gbc.gridy = row * 3 + 2;
        JLabel hintLabel = new JLabel(hint);
        hintLabel.setForeground(UITheme.MUTED);
        hintLabel.setFont(hintLabel.getFont().deriveFont(11f));
        panel.add(hintLabel, gbc);
    }

    @Override
    public void onEnterStep() {
        Profile profile = appState.getProfile();
        usernameField.setText(profile.getUsername() == null ? "" : profile.getUsername());
        schoolField.setText(profile.getSchool() == null ? "" : profile.getSchool());
        sessionNameField.setText(profile.getSessionName() == null ? "" : profile.getSessionName());
    }

    @Override
    public boolean validateStep() {
        String username = usernameField.getText().trim();
        String school = schoolField.getText().trim();
        String sessionName = sessionNameField.getText().trim();

        if (username.isEmpty()) {
            showWarning("Please enter your username to continue.");
            usernameField.requestFocusInWindow();
            return false;
        }
        if (school.isEmpty()) {
            showWarning("Please enter your school name to continue.");
            schoolField.requestFocusInWindow();
            return false;
        }
        if (sessionName.isEmpty()) {
            showWarning("Please enter a session name to continue.");
            sessionNameField.requestFocusInWindow();
            return false;
        }

        Profile profile = appState.getProfile();
        profile.setUsername(username);
        profile.setSchool(school);
        profile.setSessionName(sessionName);
        return true;
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "Missing Information", JOptionPane.WARNING_MESSAGE);
    }
}
