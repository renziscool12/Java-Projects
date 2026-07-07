import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class StudentDAO {

    public void addStudent(Student student) {

        String sql = "INSERT INTO student (first_name, last_name, age, course) VALUES (?, ?, ?, ?)"; // SQL query to
                                                                                                     // insert a new
                                                                                                     // student into
                                                                                                     // the database

        try {
            Connection conn = Database.getConnection(); // Get a connection from the Database class
            PreparedStatement pstmt = conn.prepareStatement(sql); // Prepare the SQL statement
            pstmt.setString(1, student.getFirstName()); // Sets all the parameters for the prepared statement
            pstmt.setString(2, student.getLastName());
            pstmt.setInt(3, student.getAge());
            pstmt.setString(4, student.getCourse());

            int rowsAffected = pstmt.executeUpdate(); // Execute the update and get the number of rows affected

            // Check if the insertion was successful
            if (rowsAffected > 0) {
                System.out.println("Student added successfully.");
            } else {
                System.out.println("Failed to add student.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Student> getAllStudents() {

        List<Student> students = new ArrayList<>(); // Create a list to hold the temporary student objects

        String sql = "SELECT * FROM student"; // SQL query to select all students from the database

        try (
                Connection con = Database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery(); // Execute the query and get the result set

        ) {
            while (rs.next()) { // Iterate through the result set and create Student objects for each row
                int id = rs.getInt("student_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int age = rs.getInt("age");
                String course = rs.getString("course");

                Student student = new Student(id, firstName, lastName, age, course); // Create a new Student object with
                                                                                     // the retrieved data
                students.add(student);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students; // Return the list of students to the caller
    }

    public void updateStudent(Student updatedStudent) {
        String sql = "UPDATE student SET first_name = ?, last_name = ?, age = ?, course = ? WHERE student_id = ?";
        try {
            Connection conn = Database.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, updatedStudent.getFirstName());
            pstmt.setString(2, updatedStudent.getLastName());
            pstmt.setInt(3, updatedStudent.getAge());
            pstmt.setString(4, updatedStudent.getCourse());
            pstmt.setInt(5, updatedStudent.getStudentId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeStudent(Student student) {
        String sql = "DELETE FROM student WHERE student_id = ?"; // SQL query to delete a student from the database
                                                                 // based on their ID
        try {
            Connection conn = Database.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, student.getStudentId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
