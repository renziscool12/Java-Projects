import java.util.*;
import java.io.*;

public class SchoolSystem {

    private List<School> school = new ArrayList<>();

    public void addStudent(School s) {
        school.add(s);
    }

    public List<School> getAllStudent() {
        return school;
    }

    public void updateStudent(int index, School updateStudent) {
        if (index >= 0 && index < school.size()) {
            School s = school.get(index);
            s.setStudentId(updateStudent.getStudentId());
            s.setStudentFullName(updateStudent.getStudentFullName());
            s.setStudentGender(updateStudent.getStudentGender());
            s.setStudentEnrollFee(updateStudent.getStudentEnrollFee());
        }
    }

    public void removeStudent(int index) {
        if (index >= 0 && index < school.size()) {
            school.remove(index);
        }
    }

    public void saveToFile(String filename) {
        try (PrintWriter wr = new PrintWriter(new FileWriter(filename))) {
            for (School s : school) {
                wr.println(s.getStudentId() + "," +
                        s.getStudentFullName() + "," +
                        s.getStudentGender() + "," +
                        s.getStudentEnrollFee());
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

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            school.clear();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                int studentId = Integer.parseInt(data[0]);
                String studentFullName = data[1];
                String studentGender = data[2];
                int studentEnrollFee = Integer.parseInt(data[3]);

                School s = new School(studentId, studentFullName, studentGender, studentEnrollFee);
                school.add(s);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
