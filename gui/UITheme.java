package rehlat.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class UITheme {

    public static final Color GREEN_DARK   = new Color(27, 94, 32);   // Dark green - headers
    public static final Color GREEN_MID = new Color(46, 125, 50);  // Medium green - buttons
    public static final Color GREEN_LIGHT  = new Color(200, 230, 201); // Light green - backgrounds
    public static final Color WHITE  = Color.WHITE;
    public static final Color GRAY_LIGHT = new Color(245, 245, 245);
    public static final Color TEXT_DARK = new Color(33, 33, 33);
    public static final Color RED_ERROR = new Color(198, 40, 40);
    public static final Font FONT_TITLE = new Font("Arial", Font.BOLD, 20);
    public static final Font FONT_SUBTITLE = new Font("Arial", Font.BOLD, 14);
    public static final Font FONT_NORMAL= new Font("Arial", Font.PLAIN, 13);
    public static final Font FONT_SMALL = new Font("Arial", Font.PLAIN, 11);
    public static final Font FONT_BUTTON = new Font("Arial", Font.BOLD, 13);

    public static JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BUTTON);
        btn.setBackground(GREEN_MID);
        btn.setForeground(WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        return btn;
    }

    public static JButton createOutlineButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BUTTON);
        btn.setBackground(WHITE);
        btn.setForeground(GREEN_MID);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createLineBorder(GREEN_MID, 1));
        return btn;
    }
    public static JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(FONT_NORMAL);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));
        return field;
    }

    public static JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setFont(FONT_NORMAL);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));
        return field;
    }

    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_NORMAL);
        label.setForeground(TEXT_DARK);
        return label;
    }

    public static JPanel createHeader(String title, String subtitle) {
        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setBackground(GREEN_DARK);
        header.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(FONT_TITLE);
        titleLabel.setForeground(WHITE);
        JLabel subtitleLabel = new JLabel(subtitle, SwingConstants.CENTER);
        subtitleLabel.setFont(FONT_SMALL);
        subtitleLabel.setForeground(new Color(200, 230, 201));
        header.add(titleLabel);
        header.add(subtitleLabel);
        return header;
    }

    public static Border createPadding() {
        return BorderFactory.createEmptyBorder(15, 20, 15, 20);
    }
}
