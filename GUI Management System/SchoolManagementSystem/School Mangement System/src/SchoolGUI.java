import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class SchoolGUI extends JFrame {
    JTextField studentNameField, studentIdField, studentEnrollFeeField;
    JRadioButton maleButton, femaleButton, otherButton;
    DefaultTableModel model = new DefaultTableModel();
    JTable table = new JTable(model);
    JButton addBtn, updateBtn, deleletBtn;
    private SchoolSystem system;

    public SchoolGUI() {
        setTitle("School Management System");
        setSize(1000, 500);
        setResizable(false);
        setLocationRelativeTo(null);

        String[] column = { "Student ID", "Student Name", "Student Gender", "Student Enrollment Fee" };
        model.setColumnIdentifiers(column);

        setLayout(new BorderLayout());

        system = new SchoolSystem();
        system.loadFromFile("student.txt");
        refreshTable();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));

        studentIdField = new JTextField(10);
        studentNameField = new JTextField(10);
        studentEnrollFeeField = new JTextField();

        JPanel studentIdPanel = new JPanel();
        studentIdPanel.setLayout(new BoxLayout(studentIdPanel, BoxLayout.Y_AXIS));
        studentIdPanel.add(new JLabel("Student ID"));
        studentIdPanel.add(studentIdField);

        panel.add(studentIdPanel);

        JPanel studentNamePanel = new JPanel();
        studentNamePanel.setLayout(new BoxLayout(studentNamePanel, BoxLayout.Y_AXIS));
        studentNamePanel.add(new JLabel("Student Name"));
        studentNamePanel.add(studentNameField);

        panel.add(studentNamePanel);

        JPanel genderPanel = new JPanel();
        genderPanel.setLayout(new BoxLayout(genderPanel, BoxLayout.Y_AXIS));
        maleButton = new JRadioButton("Male");
        femaleButton = new JRadioButton("Female");
        otherButton = new JRadioButton("Other");

        genderPanel.add(new JLabel("Gender"));
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        genderPanel.add(otherButton);

        ButtonGroup group = new ButtonGroup();
        group.add(maleButton);
        group.add(femaleButton);
        group.add(otherButton);

        panel.add(genderPanel);

        JPanel studentFeePanel = new JPanel();
        studentFeePanel.setLayout(new BoxLayout(studentFeePanel, BoxLayout.Y_AXIS));
        studentFeePanel.add(new JLabel("Student Enroll Fee"));
        studentFeePanel.add(studentEnrollFeeField);

        panel.add(studentFeePanel);

        addBtn = new JButton("ADD");
        updateBtn = new JButton("UPDATE");
        deleletBtn = new JButton("DELETE");

        panel.add(addBtn);
        panel.add(updateBtn);
        panel.add(deleletBtn);

        add(panel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> {
            String studentIdText = studentIdField.getText();
            String studentFullName = studentNameField.getText();
            String gender = maleButton.isSelected() ? "Male"
                    : femaleButton.isSelected() ? "Female" : otherButton.isSelected() ? "Others" : "Not Specified";
            String studentEnrollFeeText = studentEnrollFeeField.getText();

            int studentId;
            int studentEnrollFee;
            try {
                studentId = Integer.parseInt(studentIdText);
                studentEnrollFee = Integer.parseInt(studentEnrollFeeText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error");
                return;
            }

            School s = new School(studentId, studentFullName, gender, studentEnrollFee);
            system.addStudent(s);
            refreshTable();
            system.saveToFile("student.txt");
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                studentIdField.setText(model.getValueAt(row, 0).toString());
                studentNameField.setText(model.getValueAt(row, 1).toString());
                String gender = model.getValueAt(row, 2).toString();

                if (gender.equals("Male")) {
                    maleButton.setSelected(true);
                } else if (gender.equals("Female")) {
                    femaleButton.setSelected(true);
                } else if (gender.equals("Other")) {
                    otherButton.setSelected(true);
                }

                studentEnrollFeeField.setText(model.getValueAt(row, 3).toString());
            }
        });

        updateBtn.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row != -1) {
                int studentId = Integer.parseInt(studentIdField.getText());
                int studentEnrollFee = Integer.parseInt(studentEnrollFeeField.getText());
                String gender = maleButton.isSelected() ? "Male"
                        : femaleButton.isSelected() ? "Female" : otherButton.isSelected() ? "Others" : "Not Specified";

                School updated = new School(studentId, studentNameField.getText(), gender, studentEnrollFee);
                system.updateStudent(row, updated);
                system.saveToFile("student.txt");
                refreshTable();
                system.saveToFile("student.txt");
            }
        });

        deleletBtn.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row != -1) {
                system.removeStudent(row);
                system.saveToFile("student.txt");
                refreshTable();
            }
        });
    }

    public void refreshTable() {
        model.setRowCount(0);
        for (School s : system.getAllStudent()) {
            model.addRow(new Object[] {
                    s.getStudentId(),
                    s.getStudentFullName(),
                    s.getStudentGender(),
                    s.getStudentEnrollFee()
            });
        }
    }

    public static void main(String[] args) {
        new SchoolGUI().setVisible(true);
    }
}
