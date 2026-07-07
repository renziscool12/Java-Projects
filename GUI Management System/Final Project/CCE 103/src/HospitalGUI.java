import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HospitalGUI extends JFrame {

    private HospitalSystem hospital;
    DefaultTableModel model = new DefaultTableModel();
    JTable table = new JTable(model);
    JTextField searchField;

    public HospitalGUI(HospitalSystem hospital) {
        setTitle("Hospital System Management");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        this.hospital = hospital;

        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setSelectionBackground(new Color(174, 214, 241));

        ImageIcon icon = new ImageIcon("image/hospital.png");
        setIconImage(icon.getImage());

        Color bg = new Color(245, 247, 250); // light background
        Color primary = new Color(52, 152, 219);
        Color primaryHover = new Color(41, 128, 185);
        Color danger = new Color(231, 76, 60);
        Color dangerHover = new Color(192, 57, 43);

        getContentPane().setBackground(bg);

        String[] columns = { "ID", "Name", "Age", "Gender", "Complete" };
        model.setColumnIdentifiers(columns);

        // Layout
        setLayout(new BorderLayout());

        // Table scroll
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Input panel
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        JButton updateButton = new JButton("Update");
        JButton exitButton = new JButton("Exit");
        JButton searchButton = new JButton("Search");

        styleButton(addButton, primary);
        styleButton(updateButton, primary);
        styleButton(deleteButton, danger);
        styleButton(exitButton, Color.GRAY);
        styleButton(searchButton, primary);

        applyHover(addButton, primary, primaryHover);
        applyHover(updateButton, primary, primaryHover);
        applyHover(searchButton, primary, primaryHover);

        applyHover(deleteButton, danger, dangerHover);
        applyHover(exitButton, Color.GRAY, Color.DARK_GRAY);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(addButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(deleteButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(updateButton, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        panel.add(exitButton, gbc);
        add(panel, BorderLayout.SOUTH);

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());

        // Title at top
        JLabel title = new JLabel("Hospital Management System");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        // Search panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchField = new JTextField(10);
        searchField.setPreferredSize(new Dimension(120, 25));

        topPanel.add(new JLabel("Search ID:"));
        topPanel.add(searchField);
        topPanel.add(searchButton);

        // Add both into northPanel
        northPanel.add(title, BorderLayout.NORTH);
        northPanel.add(topPanel, BorderLayout.SOUTH);

        // Add once to frame
        add(northPanel, BorderLayout.NORTH);

        addButton.addActionListener(e -> {
            new HopsitalForm(this, "Add Patient", null).setVisible(true);
        });

        updateButton.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row != -1) {
                Patient selectedPatient = hospital.getAllPatients().get(row);
                new HopsitalForm(this, "Update Patient", selectedPatient).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a patient to update.");
            }
        });

        deleteButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                hospital.removePatient(row);
                hospital.saveToFile("patients.txt");
                refreshTable();
            }
        });

        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you to exit?", "Exit Confirmation",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
            }
        });

        searchButton.addActionListener(e -> {
            searchPatient();
        });

        searchField.addActionListener(e -> {
            searchPatient();
        });
    }

    public void refreshTable() {
        model.setRowCount(0);
        for (Patient p : hospital.getAllPatients()) {
            model.addRow(new Object[] {
                    p.getPatientId(),
                    p.getPatientName(),
                    p.getPatientAge(),
                    p.getPatientGender(),
                    p.isCompleted() ? "Checked [/]" : "Unchecked [X]"
            });
        }
        table.clearSelection();
    }

    private void searchPatient() {
        String searchId = searchField.getText();
        Patient p = hospital.findById(searchId);

        if (p != null) {
            JOptionPane.showMessageDialog(this, p.getPatientInfo());
        } else {
            JOptionPane.showMessageDialog(this, "ID not found", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
    }

    private void applyHover(JButton button, Color normal, Color hover) {
        button.setBackground(normal);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(hover);
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(normal);
            }
        });
    }

    public HospitalSystem getHospital() {
        return hospital;
    }

    public static void main(String[] args) {
        HospitalSystem hospital = new HospitalSystem();
        hospital.loadFromFile("patients.txt");

        HospitalGUI gui = new HospitalGUI(hospital);
        gui.refreshTable();
        gui.setVisible(true);
    }
}
