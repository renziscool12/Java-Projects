import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class GymGUI extends JFrame {
    private JTextField nameField, typeField, paymentField, expireDateField;
    private JButton addBtn, deleteBtn, updateBtn;
    private DefaultTableModel model = new DefaultTableModel();
    private JTable table = new JTable(model);
    private GymSystem system = new GymSystem();
    private GymFile file = new GymFile();

    public GymGUI() {
        setTitle("Gym Membership System");
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        String[] column = { "Member Name", "Membership Type", "Membership Payment", "Expiration Date" };
        model.setColumnIdentifiers(column);

        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        file.loadFromFile(system, "gym.txt");
        refreshTable();

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));

        nameField = new JTextField(10);
        typeField = new JTextField(10);
        paymentField = new JTextField(5);
        expireDateField = new JTextField(5);

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
        namePanel.add(new JLabel("Member Name"));
        namePanel.add(nameField);

        panel.add(namePanel);

        JPanel typePanel = new JPanel();
        typePanel.setLayout(new BoxLayout(typePanel, BoxLayout.Y_AXIS));
        typePanel.add(new JLabel("Membership Type"));
        typePanel.add(typeField);

        panel.add(typePanel);

        JPanel paymentPanel = new JPanel();
        paymentPanel.setLayout(new BoxLayout(paymentPanel, BoxLayout.Y_AXIS));
        paymentPanel.add(new JLabel("Membership Payment"));
        paymentPanel.add(paymentField);

        panel.add(paymentPanel);

        JPanel expireDatePanel = new JPanel();
        expireDatePanel.setLayout(new BoxLayout(expireDatePanel, BoxLayout.Y_AXIS));
        expireDatePanel.add(new JLabel("Expiry Date"));
        expireDatePanel.add(expireDateField);

        panel.add(expireDatePanel);

        addBtn = new JButton("ADD");
        updateBtn = new JButton("UPDATE");
        deleteBtn = new JButton("DELETE");

        panel.add(addBtn);
        panel.add(updateBtn);
        panel.add(deleteBtn);

        add(panel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> {
            String name = nameField.getText();
            String type = typeField.getText();
            String paymentText = paymentField.getText();
            String expiryDateText = expireDateField.getText();

            int payment;
            int expiryDate;
            try {
                payment = Integer.parseInt(paymentText);
                expiryDate = Integer.parseInt(expiryDateText);

                Gym g = new Gym(name, type, payment, expiryDate);
                system.addMember(g);
                file.saveToFile(system, "gym.txt");
                refreshTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        });

        updateBtn.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row != -1) {
                String name = nameField.getText();
                String type = typeField.getText();
                String paymentText = paymentField.getText();
                String expiryDateText = expireDateField.getText();

                int payment;
                int expiryDate;
                try {
                    payment = Integer.parseInt(paymentText);
                    expiryDate = Integer.parseInt(expiryDateText);

                    Gym updateG = new Gym(name, type, payment, expiryDate);
                    system.updateMember(row, updateG);
                    file.saveToFile(system, "gym.txt");
                    refreshTable();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Error", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();

            if (row != -1) {
                nameField.setText(model.getValueAt(row, 0).toString());
                typeField.setText(model.getValueAt(row, 1).toString());
                paymentField.setText(model.getValueAt(row, 2).toString());
                expireDateField.setText(model.getValueAt(row, 3).toString());
            } else {
                nameField.setText("");
                typeField.setText("");
                paymentField.setText("");
                expireDateField.setText("");
            }
        });

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row != -1) {
                system.removeMember(row);
                file.saveToFile(system, "gym.txt");
                refreshTable();
            }
        });
    }

    private void refreshTable() {
        model.setRowCount(0);
        for (Gym g : system.getMember()) {
            model.addRow(new Object[] { g.getMemberName(), g.getMembershipType(), g.getMonthlyPayment(),
                    g.getExpirationDate() });
        }
    }

    public static void main(String[] args) {
        new GymGUI().setVisible(true);
    }
}
