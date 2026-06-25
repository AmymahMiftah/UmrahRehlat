package rehlat.gui;

import rehlat.db.PackageRepository;
import rehlat.model.UmrahPackage;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

public class PackageSearchForm extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private ArrayList<UmrahPackage> packageList;
    private JTextField searchField;

    public PackageSearchForm() {
        setTitle("Search Umrah Packages");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        buildForm();
        loadPackages();
        setVisible(true);
    }

    private void buildForm() {
        setLayout(new BorderLayout());
        add(UITheme.createHeader("Search Packages", "Find and book your Umrah trip"), BorderLayout.NORTH);
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(UITheme.GRAY_LIGHT);
        searchPanel.add(UITheme.createLabel("Search:"));
        searchField = UITheme.createTextField();
        searchField.setPreferredSize(new Dimension(200, 32));
        searchPanel.add(searchField);

        JButton searchBtn = UITheme.createButton("Search");
        searchBtn.addActionListener(e -> filterPackages());
        searchPanel.add(searchBtn);
        JButton refreshBtn = UITheme.createOutlineButton("Refresh");
        refreshBtn.addActionListener(e -> loadPackages());
        searchPanel.add(refreshBtn);
        add(searchPanel, BorderLayout.CENTER);
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
        table.setShowGrid(true);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < columns.length; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(searchPanel, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        bottomPanel.setBackground(UITheme.GRAY_LIGHT);
        JButton bookBtn = UITheme.createButton("Book Selected Package");
        bookBtn.addActionListener(e -> bookSelected());
        bottomPanel.add(bookBtn);

        tablePanel.add(bottomPanel, BorderLayout.SOUTH);

        add(tablePanel, BorderLayout.CENTER);
    }

    private void loadPackages() {
        packageList = PackageRepository.getAllPackages();
        fillTable(packageList);
    }
    private void fillTable(ArrayList<UmrahPackage> list) {
        tableModel.setRowCount(0);
        for (int i = 0; i < list.size(); i++) {
            UmrahPackage pkg = list.get(i);
            String available = pkg.isAvailable() ? " Yes" : " No";
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
    private void filterPackages() {
        String keyword = searchField.getText().toLowerCase();
        ArrayList<UmrahPackage> filtered = new ArrayList<UmrahPackage>();
        for (int i = 0; i < packageList.size(); i++) {
            if (packageList.get(i).getPackageName().toLowerCase().contains(keyword)) {
                filtered.add(packageList.get(i));
            }
        }
        fillTable(filtered);
    }
    private void bookSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select package first",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String name = (String) tableModel.getValueAt(row, 1);
        String available = (String) tableModel.getValueAt(row, 6);
        String price = (String) tableModel.getValueAt(row, 5);

        if (available.equals("No")) {
            JOptionPane.showMessageDialog(this, "This package is not available",
                    "Unavailable", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Booking Confirmed!\n\nPackage: " + name
                + "\nTotal Price: " + price
                + "\n\nA confirmation will be sent to your email.",
                "Booking Confirmed", JOptionPane.INFORMATION_MESSAGE);
    }
}
