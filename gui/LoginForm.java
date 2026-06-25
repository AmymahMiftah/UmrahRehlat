package rehlat.gui;

import rehlat.db.UserRepository;
import rehlat.model.User;
import javax.swing.*;
import java.awt.*;

public class LoginForm extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JLabel statusLabel;

    public LoginForm() {
        setTitle("Rehlat Umrah - Login");
        setSize(440, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        buildForm();
        setVisible(true);
    }

    private void buildForm() {
        setLayout(new BorderLayout());

        add(UITheme.createHeader(" Rehlat Umrah", "Umrah Booking System"), BorderLayout.NORTH);

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setBackground(UITheme.WHITE);
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(UITheme.WHITE);
        form.setPreferredSize(new Dimension(320, 360));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.weightx = 1.0;
        c.insets = new Insets(0, 0, 0, 0);
        c.gridy = 0;
        c.insets = new Insets(0, 0, 20, 0);
        JLabel title = new JLabel("Sign In to Your Account", SwingConstants.CENTER);
        title.setFont(UITheme.FONT_SUBTITLE);
        title.setForeground(UITheme.GREEN_DARK);
        form.add(title, c);

        c.gridy = 1;
        c.insets = new Insets(0, 0, 4, 0);
        form.add(UITheme.createLabel("Email Address"), c);

        c.gridy = 2;
        c.insets = new Insets(0, 0, 12, 0);
        emailField = UITheme.createTextField();
        emailField.setPreferredSize(new Dimension(320, 36));
        form.add(emailField, c);

        c.gridy = 3;
        c.insets = new Insets(0, 0, 4, 0);
        form.add(UITheme.createLabel("Password"), c);

        c.gridy = 4;
        c.insets = new Insets(0, 0, 20, 0);
        passwordField = UITheme.createPasswordField();
        passwordField.setPreferredSize(new Dimension(320, 36));
        form.add(passwordField, c);

        c.gridy = 5;
        c.insets = new Insets(0, 0, 8, 0);
        JButton loginBtn = UITheme.createButton("Login");
        loginBtn.setPreferredSize(new Dimension(320, 40));
        loginBtn.addActionListener(e -> checkLogin());
        form.add(loginBtn, c);

        c.gridy = 6;
        c.insets = new Insets(0, 0, 12, 0);
        statusLabel = new JLabel(" ", SwingConstants.CENTER);
        statusLabel.setFont(UITheme.FONT_SMALL);
        statusLabel.setForeground(UITheme.RED_ERROR);
        form.add(statusLabel, c);

        c.gridy = 7;
        c.insets = new Insets(0, 0, 12, 0);
        form.add(new JSeparator(), c);

        c.gridy = 8;
        c.insets = new Insets(0, 0, 0, 0);
        JLabel signupLabel = new JLabel("New pilgrim? Create an account", SwingConstants.CENTER);
        signupLabel.setFont(UITheme.FONT_SMALL);
        signupLabel.setForeground(UITheme.GREEN_MID);
        signupLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signupLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                dispose();
                new SignUpForm();
            }
        });
        form.add(signupLabel, c);

        centerWrapper.add(form);
        add(centerWrapper, BorderLayout.CENTER);

        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(UITheme.GRAY_LIGHT);
        footer.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        JLabel hint = new JLabel(
                "<html><center>"
                        + "admin@rehlat.com / admin123 &nbsp;|&nbsp; pilgrim@rehlat.com / pilgrim123<br>"
                        + "agent@rehlat.com / agent123"
                        + "</center></html>",
                SwingConstants.CENTER);
        hint.setFont(UITheme.FONT_SMALL);
        hint.setForeground(Color.GRAY);
        footer.add(hint, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);
    }

    private void checkLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (email.equals("") || password.equals("")) {
            statusLabel.setText("Fill in all fields");
            return;
        }

        User user = UserRepository.login(email, password);

        if (user != null) {
            dispose();
            new MainDashboard(user);
        } else {
            statusLabel.setText("Invalid email or password Please try again");
        }
    }
}
