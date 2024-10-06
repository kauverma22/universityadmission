package form;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class StudentDataPanel extends JFrame {
    private final JTable studentTable;
    private DatabaseConnection db;

    public StudentDataPanel(DatabaseConnection db) {
        this.db = db;
        setTitle("Student Data");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Fetch student data from the database
        ResultSet rs = db.getAllStudents();

        // Convert the ResultSet to table format
        String[] columnNames = {"Name", "Roll No", "College Name", "Age", "GATE Score", "CGPA", "Gender"};
        Object[][] data = new Object[0][7];

        try {
            // Count the number of rows in the ResultSet
            int rowCount = 0;
            if (rs != null) {
                rs.last(); // Move to the last row to get the count
                rowCount = rs.getRow();
                rs.beforeFirst(); // Move back to the start of the ResultSet
            }

            data = new Object[rowCount][7];

            int rowIndex = 0;
            while (rs != null && rs.next()) {
                data[rowIndex][0] = rs.getString("name");
                data[rowIndex][1] = rs.getInt("roll_no");
                data[rowIndex][2] = rs.getString("college_name");
                data[rowIndex][3] = rs.getInt("age");
                data[rowIndex][4] = rs.getInt("gate_score");
                data[rowIndex][5] = rs.getFloat("cgpa");
                data[rowIndex][6] = rs.getString("gender");
                rowIndex++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Create table with the student data
        studentTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(studentTable);

        // Set up the panel layout
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        
        add(panel);
    }
}
