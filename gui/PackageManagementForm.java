package rehlat.gui;

import rehlat.db.PackageRepository;
import rehlat.model.TravelAgent;
import rehlat.model.UmrahPackage;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

public class PackageManagementForm extends JFrame {

    private TravelAgent agent;
    private JTable table;
    private DefaultTableModel tableModel;

    public PackageManagementForm(TravelAgent agent) {
        this.agent = agent;
        setTitle("Manage Packages");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        buildForm();
        loadPackages();
        setVisible(true);
    }

    private void buildForm() {
        setLayout(new BorderLayout());

        add(UITheme.createHeader("Manage Packages", "Agency: " + agent.getAgencyName()), BorderLayout.NORTH);

        String[] columns = {"ID", "Package Name", "Departure", "Nights", "Rating", "Price ($)", "Available"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(tableModel);
        table.setFont(UITheme.FONT_NORMAL);
        table.setRowHeight(26);
        table.getTableHeader().setFont(UITheme.FONT_SUBTITLE);
        table.getTableHeader().setBackground(UITheme.GREEN_DARK);
        table.getTableHeader().setForeground(UITheme.WHITE);
        table.setSelectionBackground(UITheme.GREEN_LIGHT);
        table.setGridColor(new Color(220, 220, 220));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < columns.length; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        bottomPanel.setBackground(UITheme.GRAY_LIGHT);
        JButton addBtn = UITheme.createButton("＋  Add New Package");
        addBtn.addActionListener(e -> openAddDialog());
        bottomPanel.add(addBtn);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadPackages() {
        tableModel.setRowCount(0);
        ArrayList<UmrahPackage> list = PackageRepository.getAllPackages();
        for (int i = 0; i < list.size(); i++) {
            UmrahPackage pkg = list.get(i);
            String available = pkg.isAvailable() ? "✓ Yes" : "✗ No";
            tableModel.addRow(new Object[]{
                    pkg.getPackageId(),
                    pkg.getPackageName(),
                    pkg.getDepartureDate(),
                    pkg.getNumberOfNights(),
                    pkg.getHotelRating() + " ★",
                    String.format("%.2f", pkg.getPrice()),
                    available
            });
        }
    }

    /** Opens the add-package dialog. */
    private void openAddDialog() {
        JTextField nameField = UITheme.createTextField();
        JTextField makkahField = UITheme.createTextField();
        JTextField madinahField = UITheme.createTextField();
        JTextField flightField = UITheme.createTextField();
        JTextField nightsField = UITheme.createTextField();
        JTextField priceField = UITheme.createTextField();
        JTextField ratingField = UITheme.createTextField();
        JTextField dateField = UITheme.createTextField();
        dateField.setText("2026-10-01");

        JPanel input = new JPanel(new GridLayout(8, 2, 8, 8));
        input.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        input.add(UITheme.createLabel("Package Name:")); input.add(nameField);
        input.add(UITheme.createLabel("Hotel Makkah:")); input.add(makkahField);
        input.add(UITheme.createLabel("Hotel Madinah:")); input.add(madinahField);
        input.add(UITheme.createLabel("Flight Details:")); input.add(flightField);
        input.add(UITheme.createLabel("Nights:")); input.add(nightsField);
        input.add(UITheme.createLabel("Price ($):")); input.add(priceField);
        input.add(UITheme.createLabel("Hotel Rating 1-5:")); input.add(ratingField);
        input.add(UITheme.createLabel("Departure Date:"));  input.add(dateField);
        int result = JOptionPane.showConfirmDialog(this, input,
                "Add New Package", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                if (name.equals("")) {
                    JOptionPane.showMessageDialog(this, "Package name cannot be empty");
                    return;
                }
                int nights = Integer.parseInt(nightsField.getText().trim());
                double price = Double.parseDouble(priceField.getText().trim());
                int rating = Integer.parseInt(ratingField.getText().trim());
                UmrahPackage pkg = new UmrahPackage(0, name,
                        makkahField.getText().trim(),
                        madinahField.getText().trim(),
                        flightField.getText().trim(),
                        nights, price, rating,
                        dateField.getText().trim(), true);

                boolean saved = PackageRepository.savePackage(pkg);
                if (saved) {
                    JOptionPane.showMessageDialog(this,
                            "Package '" + name + "' added successfully",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadPackages();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to save please try again");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Nights, Price, and Rating must be numbers.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
