package rehlat.gui;

import rehlat.db.UserRepository;
import rehlat.model.Pilgrim;
import javax.swing.*;
import java.awt.*;

public class SignUpForm extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField phoneField;
    private JTextField passportField;
    private JTextField nationalityField;
    private JLabel statusLabel;

    public SignUpForm() {
        setTitle("Rehlat Umrah - Sign Up");
        setSize(420, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        buildForm();
        setVisible(true);
    }

    private void buildForm() {
        setLayout(new BorderLayout());

        add(UITheme.createHeader("Create Account", "Register as a new Pilgrim"), BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(UITheme.WHITE);
        center.setBorder(UITheme.createPadding());

        center.add(Box.createVerticalStrut(10));

        nameField        = addField(center, "Full Name");
        emailField       = addField(center, "Email Address");
        passwordField    = addPasswordField(center, "Password");
        phoneField       = addField(center, "Phone Number");
        passportField    = addField(center, "Passport Number");
        nationalityField = addField(center, "Nationality");

        center.add(Box.createVerticalStrut(15));

        JButton registerBtn = UITheme.createButton("Create Account");
        registerBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        registerBtn.addActionListener(e -> register());
        center.add(registerBtn);

        center.add(Box.createVerticalStrut(8));

        statusLabel = new JLabel(" ");
        statusLabel.setFont(UITheme.FONT_SMALL);
        statusLabel.setForeground(UITheme.RED_ERROR);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(statusLabel);

        center.add(Box.createVerticalStrut(10));

        JButton backBtn = UITheme.createOutlineButton("Back to Login");
        backBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        backBtn.addActionListener(e -> { dispose(); new LoginForm(); });
        center.add(backBtn);
        add(center, BorderLayout.CENTER);
    }

    private JTextField addField(JPanel panel, String label) {
        panel.add(UITheme.createLabel(label));
        panel.add(Box.createVerticalStrut(4));
        JTextField field = UITheme.createTextField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        panel.add(field);
        panel.add(Box.createVerticalStrut(10));
        return field;
    }


    private JPasswordField addPasswordField(JPanel panel, String label) {
        panel.add(UITheme.createLabel(label));
        panel.add(Box.createVerticalStrut(4));
        JPasswordField field = UITheme.createPasswordField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        panel.add(field);
        panel.add(Box.createVerticalStrut(10));
        return field;
    }

    private void register() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String phone = phoneField.getText().trim();
        String passport = passportField.getText().trim();
        String nationality = nationalityField.getText().trim();

        if (name.equals("") || email.equals("") || password.equals("")
                || phone.equals("") || passport.equals("") || nationality.equals("")) {
            statusLabel.setText("Please fill in all fields");
            return;
        }

        if (UserRepository.emailExists(email)) {
            statusLabel.setText("Email already registered please use a different one");
            return;
        }

        Pilgrim newPilgrim = new Pilgrim(0, name, email, password, phone, passport, nationality);
        boolean saved = UserRepository.savePilgrim(newPilgrim);

        if (saved) {
            JOptionPane.showMessageDialog(this,
                    "Account created successfully\nYou can now log in.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new LoginForm();
        } else {
            statusLabel.setText("Something went wrong please try again");
        }
    }
}
