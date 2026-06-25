package rehlat.gui;

import rehlat.db.UserRepository;
import rehlat.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

public class ManageUsersForm extends JFrame {

    private DefaultTableModel pilgrimModel;
    private DefaultTableModel agentModel;
    private DefaultTableModel adminModel;

    public ManageUsersForm() {
        setTitle("Manage Users");
        setSize(780, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        buildForm();
        loadAllTabs();
        setVisible(true);
    }

    private void buildForm() {
        setLayout(new BorderLayout());

        add(UITheme.createHeader("Manage Users", "View and manage all system accounts"), BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(UITheme.FONT_SUBTITLE);
        tabs.setBackground(UITheme.WHITE);

        String[] pilgrimColumns = {"ID", "Full Name", "Email", "Phone", "Passport", "Nationality"};
        pilgrimModel = buildTableModel(pilgrimColumns);
        JTable pilgrimTable = buildStyledTable(pilgrimModel);
        JPanel pilgrimPanel = new JPanel(new BorderLayout());
        pilgrimPanel.add(new JScrollPane(pilgrimTable), BorderLayout.CENTER);

        JLabel pilgrimNote = new JLabel("  Pilgrims register through the Sign Up screen", SwingConstants.LEFT);
        pilgrimNote.setFont(UITheme.FONT_SMALL);
        pilgrimNote.setForeground(Color.GRAY);
        pilgrimNote.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        pilgrimPanel.add(pilgrimNote, BorderLayout.SOUTH);
        tabs.addTab("Pilgrims", pilgrimPanel);

        String[] agentColumns = {"ID", "Full Name", "Email", "Phone", "Agency Name", "License"};
        agentModel = buildTableModel(agentColumns);
        JTable agentTable = buildStyledTable(agentModel);
        JPanel agentPanel = new JPanel(new BorderLayout());
        agentPanel.add(new JScrollPane(agentTable), BorderLayout.CENTER);
        JPanel agentBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 8));
        agentBottom.setBackground(UITheme.GRAY_LIGHT);
        JButton addAgentBtn = UITheme.createButton("＋ Add New Travel Agency");
        addAgentBtn.addActionListener(e -> openAddAgentDialog());
        agentBottom.add(addAgentBtn);
        agentPanel.add(agentBottom, BorderLayout.SOUTH);
        tabs.addTab("Travel Agents", agentPanel);

        String[] adminColumns = {"ID", "Full Name", "Email", "Phone", "Access Level"};
        adminModel = buildTableModel(adminColumns);
        JTable adminTable = buildStyledTable(adminModel);
        JPanel adminPanel = new JPanel(new BorderLayout());
        adminPanel.add(new JScrollPane(adminTable), BorderLayout.CENTER);
        JLabel adminNote = new JLabel("Administrator accounts are managed at the system", SwingConstants.LEFT);
        adminNote.setFont(UITheme.FONT_SMALL);
        adminNote.setForeground(Color.GRAY);
        adminNote.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        adminPanel.add(adminNote, BorderLayout.SOUTH);
        tabs.addTab("Administrators", adminPanel);

        add(tabs, BorderLayout.CENTER);
    }

    private DefaultTableModel buildTableModel(String[] columns) {
        return new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
    }

    private JTable buildStyledTable(DefaultTableModel model) {
        JTable t = new JTable(model);
        t.setFont(UITheme.FONT_NORMAL);
        t.setRowHeight(26);
        t.getTableHeader().setFont(UITheme.FONT_SUBTITLE);
        t.getTableHeader().setBackground(UITheme.GREEN_DARK);
        t.getTableHeader().setForeground(UITheme.WHITE);
        t.setSelectionBackground(UITheme.GREEN_LIGHT);
        t.setGridColor(new Color(220, 220, 220));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < model.getColumnCount(); i++) {
            t.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        return t;
    }

    private void loadAllTabs() {
        pilgrimModel.setRowCount(0);
        agentModel.setRowCount(0);
        adminModel.setRowCount(0);

        ArrayList<User> list = UserRepository.getAllUsers();

        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);

            if (user instanceof Pilgrim) {
                Pilgrim p = (Pilgrim) user;
                pilgrimModel.addRow(new Object[]{
                        p.getUserId(), p.getFullName(), p.getEmail(),
                        p.getPhone(), p.getPassportNumber(), p.getNationality()
                });

            } else if (user instanceof TravelAgent) {
                TravelAgent a = (TravelAgent) user;
                agentModel.addRow(new Object[]{
                        a.getUserId(), a.getFullName(), a.getEmail(),
                        a.getPhone(), a.getAgencyName(), a.getLicenseNumber()
                });

            } else if (user instanceof Administrator) {
                Administrator ad = (Administrator) user;
                adminModel.addRow(new Object[]{
                        ad.getUserId(), ad.getFullName(), ad.getEmail(),
                        ad.getPhone(), ad.getAccessLevel()
                });
            }
        }
    }

    private void openAddAgentDialog() {
        JTextField nameField = UITheme.createTextField();
        JTextField emailField = UITheme.createTextField();
        JPasswordField passField = UITheme.createPasswordField();
        JTextField phoneField = UITheme.createTextField();
        JTextField agencyField = UITheme.createTextField();
        JTextField licenseField = UITheme.createTextField();

        JPanel input = new JPanel(new GridLayout(6, 2, 8, 8));
        input.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        input.add(UITheme.createLabel("Full Name:"));  input.add(nameField);
        input.add(UITheme.createLabel("Email:")); input.add(emailField);
        input.add(UITheme.createLabel("Password:"));input.add(passField);
        input.add(UITheme.createLabel("Phone:")); input.add(phoneField);
        input.add(UITheme.createLabel("Agency Name:"));input.add(agencyField);
        input.add(UITheme.createLabel("License Number:")); input.add(licenseField);

        int result = JOptionPane.showConfirmDialog(this, input,
                "Add New Travel Agent", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String pass = new String(passField.getPassword()).trim();
            String phone = phoneField.getText().trim();
            String agency = agencyField.getText().trim();
            String license = licenseField.getText().trim();

            if (name.equals("") || email.equals("") || pass.equals("")
                    || agency.equals("") || license.equals("")) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.",
                        "Missing Fields", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (UserRepository.emailExists(email)) {
                JOptionPane.showMessageDialog(this, "This email is already registered",
                        "Duplicate Email", JOptionPane.ERROR_MESSAGE);
                return;
            }

            TravelAgent agent = new TravelAgent(0, name, email, pass, phone, agency, license);
            boolean saved = UserRepository.saveTravelAgent(agent);

            if (saved) {
                JOptionPane.showMessageDialog(this,
                        "Travel Agent '" + name + "' added successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                loadAllTabs();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add agent. Please try again.");
            }
        }
    }
}