package form;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class AdminPanel extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private final JButton loginButton, registerButton;
    private final  DatabaseConnection db;

    public AdminPanel(DatabaseConnection db) {
        this.db = db;
        setTitle("Admin Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 2, 10, 10));

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        formPanel.add(new JLabel("Username:"));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);
        formPanel.add(new JLabel());
        formPanel.add(loginButton);
        formPanel.add(new JLabel());
        formPanel.add(registerButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (db.validateAdmin(username, password)) {
                    JOptionPane.showMessageDialog(null, "Welcome, Admin!");
                    displayStudentData(); // Show the student data
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials.");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminRegistration(db).setVisible(true);
                dispose(); // Close login window
            }
        });
    }

    private void displayStudentData() {
        // Create a new frame to display student data
        JFrame studentFrame = new JFrame("Student Data");
        studentFrame.setSize(800, 400);
        studentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"Name", "Roll No", "College Name", "Age", "GATE Score", "CGPA", "Gender"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        try {
            ResultSet rs = db.getAllStudents(); // Retrieve all student data
            while (rs.next()) {
                String name = rs.getString("name");
                int rollNo = rs.getInt("roll_no");
                String collegeName = rs.getString("college_name");
                int age = rs.getInt("age");
                int gateScore = rs.getInt("gate_score");
                float cgpa = rs.getFloat("cgpa");
                String gender = rs.getString("gender");
                model.addRow(new Object[]{name, rollNo, collegeName, age, gateScore, cgpa, gender});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to retrieve student data.");
        }

        studentFrame.add(scrollPane, BorderLayout.CENTER);
        studentFrame.setVisible(true);
    }
}

