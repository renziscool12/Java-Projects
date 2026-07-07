import java.util.*;
import java.io.*;

public class HospitalSystem {
    ArrayList<Patient> patients = new ArrayList<>();

    // Add Patients
    void addPatient(Patient p) {
        patients.add(p);
    }

    // Read Patients
    public ArrayList<Patient> getAllPatients() {
        return patients;
    }

    // Update Patient
    void updatePatient(int index, Patient patient) {
        if (index >= 0 && index < patients.size()) {
            Patient patientt = patients.get(index);
            patientt.setPatientName(patient.getPatientName());
            patientt.setPatientAge(patient.getPatientAge());
            patientt.setPatientGender(patient.getPatientGender());
        }
    }

    // Remove patient
    void removePatient(int index) {
        if (index >= 0 && index < patients.size()) {
            patients.remove(index);
        }
    }

    // Finds the ID of a patient
    public Patient findById(String id) {
        for (Patient p : patients) {
            if (p.getPatientId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public void saveToFile(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Patient p : patients) {
                pw.println(p.getPatientId() + "," +
                        p.getPatientName() + "," +
                        p.getPatientAge() + "," +
                        p.getPatientGender() + "," +
                        p.isCompleted());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile(String filename) {
        File file = new File(filename);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            patients.clear();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                String id = data[0];
                String name = data[1];
                int age = Integer.parseInt(data[2]);
                String gender = data[3];
                boolean completed = Boolean.parseBoolean(data[4]);

                Patient p = new Patient(name, age, gender, id);
                p.setCompleted(completed);

                patients.add(p);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
