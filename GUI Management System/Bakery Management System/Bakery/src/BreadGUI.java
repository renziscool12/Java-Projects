import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class BreadGUI extends JFrame {
    private JTextField breadIdField, breadTypeField, quantityField, priceField;
    DefaultTableModel model = new DefaultTableModel();
    JButton addBtn, updateBtn, deleteBtn;
    JTable table = new JTable(model);
    private BreadSystem system;

    public BreadGUI() {
        setTitle("Bread Management System");
        setSize(1000, 500);
        setResizable(false);
        setLocationRelativeTo(null);

        String[] column = { "Bread ID", "Bread Type", "Quantity", "Price" };
        model.setColumnIdentifiers(column);

        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        system = new BreadSystem();
        system.loadFromFile("bread.txt");
        refreshTable();

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));

        breadIdField = new JTextField(10);
        breadTypeField = new JTextField(10);
        quantityField = new JTextField(10);
        priceField = new JTextField(10);

        JPanel breadIdPanel = new JPanel();
        breadIdPanel.setLayout(new BoxLayout(breadIdPanel, BoxLayout.Y_AXIS));
        breadIdPanel.add(new JLabel("Bread ID"));
        breadIdPanel.add(breadIdField);

        panel.add(breadIdPanel);

        JPanel breadTypePanel = new JPanel();
        breadTypePanel.setLayout(new BoxLayout(breadTypePanel, BoxLayout.Y_AXIS));
        breadTypePanel.add(new JLabel("Bread Type"));
        breadTypePanel.add(breadTypeField);

        panel.add(breadTypePanel);

        JPanel quantityPanel = new JPanel();
        quantityPanel.setLayout(new BoxLayout(quantityPanel, BoxLayout.Y_AXIS));
        quantityPanel.add(new JLabel("Quantity"));
        quantityPanel.add(quantityField);

        panel.add(quantityPanel);

        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new BoxLayout(pricePanel, BoxLayout.Y_AXIS));
        pricePanel.add(new JLabel("Price"));
        pricePanel.add(priceField);

        panel.add(pricePanel);

        addBtn = new JButton("ADD");
        updateBtn = new JButton("UPDATE");
        deleteBtn = new JButton("DELETE");

        panel.add(addBtn);
        panel.add(updateBtn);
        panel.add(deleteBtn);

        add(panel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> {
            String breadIdText = breadIdField.getText();
            String breadType = breadTypeField.getText();
            String quantityText = quantityField.getText();
            String priceText = priceField.getText();

            int breadId;
            int quantity;
            int price;
            try {
                breadId = Integer.parseInt(breadIdText);
                quantity = Integer.parseInt(quantityText);
                price = Integer.parseInt(priceText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error");
                return;
            }

            Bread b = new Bread(breadId, breadType, quantity, price);
            system.addBread(b);
            refreshTable();
            system.saveToFile("bread.txt");
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();

            if (row != -1) {
                breadIdField.setText(model.getValueAt(row, 0).toString());
                breadTypeField.setText(model.getValueAt(row, 1).toString());
                quantityField.setText(model.getValueAt(row, 2).toString());
                priceField.setText(model.getValueAt(row, 3).toString());
            }
        });

        updateBtn.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row != -1) {
                int breadId = Integer.parseInt(breadIdField.getText());
                int quantity = Integer.parseInt(quantityField.getText());
                int price = Integer.parseInt(priceField.getText());

                Bread update = new Bread(breadId, breadTypeField.getText(), quantity, price);

                system.updateBread(row, update);
                refreshTable();
                system.saveToFile("bread.txt");
            }
        });

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row != -1) {
                system.deleteBread(row);
                system.saveToFile("bread.txt");
                refreshTable();
            }
        });
    }

    private void refreshTable() {
        model.setRowCount(0);
        for (Bread b : system.getAllBread()) {
            model.addRow(new Object[] {
                    b.getBreadId(),
                    b.getBreadType(),
                    b.getQuantity(),
                    b.getPrice()
            });
        }
    }

    public static void main(String[] args) {
        new BreadGUI().setVisible(true);
    }
}
