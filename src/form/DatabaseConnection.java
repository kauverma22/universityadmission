package form;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/university_admission";
    private static final String USER = "root";
    private static final String PASSWORD = "01290217";
    private Connection connection;

    public DatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connected successfully.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to connect to the database.");
        }
    }

    public boolean registerAdmin(String username, String password) {
        String query = "INSERT INTO admins (username, password) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean validateAdmin(String username, String password) {
        String query = "SELECT * FROM admins WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet getAllStudents() {
        String query = "SELECT * FROM students";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void insertStudent(String name, int rollNo, String collegeName, int age, int gateScore, float cgpa, String gender) {
        String query = "INSERT INTO students (name, roll_no, college_name, age, gate_score, cgpa, gender) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setInt(2, rollNo);
            stmt.setString(3, collegeName);
            stmt.setInt(4, age);
            stmt.setInt(5, gateScore);
            stmt.setFloat(6, cgpa);
            stmt.setString(7, gender);
            stmt.executeUpdate();
            System.out.println("Student data inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to insert student data.");
        }
    }
}


