import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/student_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD); // Establish a connection to the database using the
                                                                     // provided URL, username, and password
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
