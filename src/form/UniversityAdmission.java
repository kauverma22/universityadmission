package form;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class UniversityAdmission extends JFrame {
    private JTextField nameField, rollNoField, collegeNameField, ageField, gateScoreField, cgpaField;
    private JComboBox<String> genderComboBox;
    private final JButton submitButton, adminLoginButton, adminRegisterButton;
    private DatabaseConnection db;

    public UniversityAdmission() {
        setTitle("MTech Admission Form");
        setSize(800, 600);  // Increased size for better visibility
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Heading
        JLabel heading = new JLabel("M.Tech Admission", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 36));  // Increased font size
        heading.setBorder(BorderFactory.createEmptyBorder(20, 20, 40, 20));  // Add space after the heading
        mainPanel.add(heading, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(9, 2, 10, 10));  // Adjusted layout with gaps
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));  // Add margins

        // Initialize fields
        nameField = new JTextField();
        rollNoField = new JTextField();
        collegeNameField = new JTextField();
        ageField = new JTextField();
        gateScoreField = new JTextField();
        cgpaField = new JTextField();
        genderComboBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        submitButton = new JButton("Submit");
        adminLoginButton = new JButton("Admin Login");
        adminRegisterButton = new JButton("Admin Registration");

        // Add components to the panel
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Roll No:"));
        formPanel.add(rollNoField);
        formPanel.add(new JLabel("College Name:"));
        formPanel.add(collegeNameField);
        formPanel.add(new JLabel("Age:"));
        formPanel.add(ageField);
        formPanel.add(new JLabel("GATE Score:"));
        formPanel.add(gateScoreField);
        formPanel.add(new JLabel("CGPA:"));
        formPanel.add(cgpaField);
        formPanel.add(new JLabel("Gender:"));
        formPanel.add(genderComboBox);

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding

        // Positioning the Admin Registration button on the left
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        buttonPanel.add(submitButton, gbc);

        // Positioning the Submit button in the center
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        buttonPanel.add(adminRegisterButton, gbc);

        // Positioning the Admin Login button on the right
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        buttonPanel.add(adminLoginButton, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // Initialize DatabaseConnection
        db = new DatabaseConnection();

        // Add action listener for the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                int rollNo = Integer.parseInt(rollNoField.getText());
                String collegeName = collegeNameField.getText();
                int age = Integer.parseInt(ageField.getText());
                int gateScore = Integer.parseInt(gateScoreField.getText());
                float cgpa = Float.parseFloat(cgpaField.getText());
                String gender = genderComboBox.getSelectedItem().toString();

                // Check eligibility
                if (gateScore > 500 && cgpa > 8.0) {
                    db.insertStudent(name, rollNo, collegeName, age, gateScore, cgpa, gender);
                    JOptionPane.showMessageDialog(null, "You are eligible. Data has been stored.");
                } else {
                    JOptionPane.showMessageDialog(null, "You are not eligible.");
                }
            }
        });

        adminLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminPanel(db).setVisible(true);
                dispose();
            }
        });

        adminRegisterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminRegistration(db).setVisible(true); 
                dispose(); // Renamed to AdminRegistration
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UniversityAdmission().setVisible(true));
    }
}
