import java.util.*;

public class App {

    public void run() {
        StudentDAO studentDAO = new StudentDAO();
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            displayMenu();
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addStudent(sc, studentDAO);
                    break;
                case 2:
                    viewStudents(studentDAO);
                    break;
                case 3:
                    updateStudent(sc, studentDAO);
                    break;
                case 4:
                    deleteStudent(sc, studentDAO);
                    break;
                case 5:
                    running = false;
                    System.out.println("Exiting...");
                    break;
            }
        }

    }

    public void displayMenu() {
        System.out.println("Student Management System");
        System.out.println("1. Add Student");
        System.out.println("2. View Students");
        System.out.println("3. Update Student");
        System.out.println("4. Delete Student");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    public void addStudent(Scanner sc, StudentDAO studentDAO) {
        System.out.println("Enter First Name: ");
        String firstName = sc.nextLine();

        System.out.println("Enter Last Name: ");
        String lastName = sc.nextLine();

        System.out.println("Enter Age: ");
        String ageInput = sc.nextLine();

        System.out.println("Enter Course: ");
        String course = sc.nextLine();

        if (!ageInput.matches("\\d+")) {
            System.out.println("Error: Age must be a valid number.");
        }

        try {
            int age = Integer.parseInt(ageInput);

            if (firstName.isEmpty() || lastName.isEmpty() || age <= 0 || course.isEmpty()) {
                System.out.println("Error: All fields must be filled and age must be greater than 0.");
            } else {
                System.out.println("Student added successfully!");
            }

            if (!firstName.matches("[a-zA-Z]+") || !lastName.matches("[a-zA-Z]+")) {
                System.out.println("Error: First Name and Last Name must contain only letters.");
            }

            Student student = new Student(0, firstName, lastName, age, course);
            studentDAO.addStudent(student);
        } catch (NumberFormatException e) {
            System.out.println("Error: Age must be a valid number.");
        }

    };

    public void viewStudents(StudentDAO studentDAO) {
        List<Student> students = studentDAO.getAllStudents();

        for (Student student : students) {
            System.out.println(student);
        }
    }

    public void updateStudent(Scanner sc, StudentDAO studentDAO) {
        System.out.println("Enter Student ID to update:");
        int id = sc.nextInt();
        sc.nextLine(); // Consume newline

        System.out.println("Enter new First Name: ");
        String firstName = sc.nextLine();

        System.out.println("Enter new Last Name: ");
        String lastName = sc.nextLine();

        System.out.println("Enter new Age: ");
        int age = sc.nextInt();
        sc.nextLine(); // Consume newline

        System.out.println("Enter new Course: ");
        String course = sc.nextLine();

        Student updatedStudent = new Student(id, firstName, lastName, age, course);
        studentDAO.updateStudent(updatedStudent);
    }

    public void deleteStudent(Scanner sc, StudentDAO studentDAO) {
        System.out.println("Enter Student ID to delete:");
        int id = sc.nextInt();
        sc.nextLine(); // Consume newline

        Student studentToDelete = new Student(id, "", "", 0, ""); // Create a temporary Student object with the ID
        studentDAO.removeStudent(studentToDelete);
    }
}
