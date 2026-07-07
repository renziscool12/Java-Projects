import javax.swing.*;
import java.awt.*;

public class HopsitalForm extends JFrame {
    private JTextField nameField, ageField, patientIdField;
    private JRadioButton maleButton, femaleButton, otherButton;
    private JButton addButton, exitButton;
    private HospitalGUI mainGui;
    private Patient patient;
    private JCheckBox completedCheckBox;
    private HospitalSystem hospital;

    public HopsitalForm(HospitalGUI mainGui, String title, Patient patient) {
        this.mainGui = mainGui;
        this.patient = patient;

        ImageIcon icon = new ImageIcon("image/hospital.png");

        setTitle(title);
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setIconImage(icon.getImage());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 13);

        JLabel title1 = new JLabel();
        title1.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title1.setHorizontalAlignment(SwingConstants.CENTER);

        mainPanel.add(title1, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(labelFont);
        formPanel.add(nameLabel, gbc);
        nameField = new JTextField(15);

        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setFont(labelFont);
        formPanel.add(ageLabel, gbc);
        ageField = new JTextField(5);

        gbc.gridx = 1;
        formPanel.add(ageField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(labelFont);
        formPanel.add(genderLabel, gbc);

        JPanel genderPanel = new JPanel();
        maleButton = new JRadioButton("Male");
        femaleButton = new JRadioButton("Female");
        otherButton = new JRadioButton("Other");

        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        genderPanel.add(otherButton);

        ButtonGroup group = new ButtonGroup();
        group.add(maleButton);
        group.add(femaleButton);
        group.add(otherButton);

        gbc.gridx = 1;
        formPanel.add(genderPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel patientLabel = new JLabel("Patient ID:");
        patientLabel.setFont(labelFont);
        formPanel.add(patientLabel, gbc);
        patientIdField = new JTextField(10);

        gbc.gridx = 1;
        formPanel.add(patientIdField, gbc);

        completedCheckBox = new JCheckBox("Completed");
        completedCheckBox.setFont(labelFont);

        gbc.gridx = 1;
        gbc.gridy = 5;
        formPanel.add(completedCheckBox, gbc);

        addButton = new JButton("Add");
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(addButton, gbc);

        exitButton = new JButton("Exit");
        gbc.gridx = 1;
        formPanel.add(exitButton, gbc);

        addButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        exitButton.setFont(new Font("Segoe UI", Font.BOLD, 13));

        add(formPanel);

        if (patient != null) {
            nameField.setText(patient.getPatientName());
            ageField.setText(String.valueOf(patient.getPatientAge()));
            patientIdField.setText(String.valueOf(patient.getPatientId()));
            String gender = patient.getPatientGender();
            completedCheckBox.setSelected(patient.isCompleted());

            if (gender.equals("Male")) {
                maleButton.setSelected(true);
            } else if (gender.equals("Female")) {
                femaleButton.setSelected(true);
            } else if (gender.equals("Others")) {
                otherButton.setSelected(true);
            }
        }

        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String ageText = ageField.getText();
            String gender = maleButton.isSelected() ? "Male"
                    : femaleButton.isSelected() ? "Female" : otherButton.isSelected() ? "Others" : "Not Specified";
            String patientIdText = patientIdField.getText();
            boolean completed = completedCheckBox.isSelected();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (Patient p : mainGui.getHospital().getAllPatients()) {

                if (patient != null && p.getPatientId().equals(patient.getPatientId())) {
                    continue;
                }

                if (p.getPatientName().equalsIgnoreCase(name)) {
                    JOptionPane.showMessageDialog(this, "Duplicate Name", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (p.getPatientId().equals(patientIdText)) {
                    JOptionPane.showMessageDialog(this, "Duplicate Patient ID", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            int age;
            int patientId;
            try {
                age = Integer.parseInt(ageText);
                patientId = Integer.parseInt(patientIdText);

                if (age < 0 || age > 120) {
                    JOptionPane.showMessageDialog(this, "Invalid Age", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (patientId < 0 || patientIdText.length() != 6) {
                    JOptionPane.showMessageDialog(this, "Must be 6 numbers", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Numbers Only", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (patient == null) {
                Patient p = new Patient(name, age, gender, patientIdText);
                p.setCompleted(completedCheckBox.isSelected());
                mainGui.getHospital().addPatient(p);
                mainGui.getHospital().saveToFile("patients.txt");
            } else {
                patient.setPatientName(name);
                patient.setPatientAge(age);
                patient.setPatientGender(gender);
                patient.setPatientId(patientIdText);
                patient.setCompleted(completed);
                mainGui.getHospital().saveToFile("patients.txt");
            }

            mainGui.refreshTable();
            dispose();
        });

        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you to exit?", "Exit Confirmation",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
            }
        });
    }
}