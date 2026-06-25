package rehlat.gui;

import rehlat.model.*;
import javax.swing.*;
import java.awt.*;
public class MainDashboard extends JFrame {

    private User currentUser;

    public MainDashboard(User user) {
        this.currentUser = user;
        setTitle("Rehlat Umrah - Dashboard");
        setSize(480, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        buildForm();
        setVisible(true);
    }
    private void buildForm() {
        setLayout(new BorderLayout());

        add(UITheme.createHeader(
                "Welcome, " + currentUser.getFullName(),
                "Role: " + currentUser.getRole()), BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(UITheme.WHITE);
        center.setBorder(UITheme.createPadding());
        center.add(Box.createVerticalStrut(20));

        JLabel sectionLabel = new JLabel("what would you like to do");
        sectionLabel.setFont(UITheme.FONT_SUBTITLE);
        sectionLabel.setForeground(UITheme.GREEN_DARK);
        sectionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(sectionLabel);
        center.add(Box.createVerticalStrut(20));

        if (currentUser instanceof Pilgrim) {
            addDashboardButton(center, " search & book packages",
                    "Browse available Umrah packages", e -> new PackageSearchForm());
        }

        else if (currentUser instanceof TravelAgent) {
            addDashboardButton(center, "Manage Packages",
                    "View, add, and update travel packages",
                    e -> new PackageManagementForm((TravelAgent) currentUser));
        }

        else if (currentUser instanceof Administrator) {
            addDashboardButton(center, "👥  Manage Users",
                    "View all users and add new travel agencies",
                    e -> new ManageUsersForm());
            center.add(Box.createVerticalStrut(10));
            addDashboardButton(center, "View System Report",
                    "See a full report of all users",
                    e -> new ReportForm((Administrator) currentUser));
        }

        center.add(Box.createVerticalStrut(20));
        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        center.add(sep);
        center.add(Box.createVerticalStrut(12));

        JButton logoutBtn = UITheme.createOutlineButton("Logout");
        logoutBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        logoutBtn.addActionListener(e -> { dispose(); new LoginForm(); });
        center.add(logoutBtn);

        add(center, BorderLayout.CENTER);
    }

    private void addDashboardButton(JPanel panel, String title, String subtitle,
                                     java.awt.event.ActionListener action) {
        JPanel card = new JPanel(new BorderLayout(8, 4));
        card.setBackground(UITheme.GREEN_LIGHT);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(165, 214, 167), 1),
                BorderFactory.createEmptyBorder(12, 16, 12, 16)));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(UITheme.FONT_SUBTITLE);
        titleLabel.setForeground(UITheme.GREEN_DARK);

        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(UITheme.FONT_SMALL);
        subtitleLabel.setForeground(Color.GRAY);

        JLabel arrow = new JLabel("›");
        arrow.setFont(new Font("Arial", Font.BOLD, 22));
        arrow.setForeground(UITheme.GREEN_MID);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(subtitleLabel, BorderLayout.CENTER);
        card.add(arrow, BorderLayout.EAST);

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                action.actionPerformed(null);
            }
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                card.setBackground(new Color(165, 214, 167));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                card.setBackground(UITheme.GREEN_LIGHT);
            }
        });

        panel.add(card);
    }
}
