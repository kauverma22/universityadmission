package form;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class AdminRegistration extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private final JButton registerButton;
    private final DatabaseConnection db;

    public AdminRegistration(DatabaseConnection db) {
        this.db = db;
        setTitle("Admin Registration");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3, 2, 10, 10));

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        registerButton = new JButton("Register");

        formPanel.add(new JLabel("Username:"));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);
        formPanel.add(new JLabel());
        formPanel.add(registerButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (db.registerAdmin(username, password)) {
                    JOptionPane.showMessageDialog(null, "Registration successful. You can now log in.");
                    dispose();
                    new AdminPanel(db).setVisible(true); // Redirect to login after registration
                } else {
                    JOptionPane.showMessageDialog(null, "Registration failed. Try a different username.");
                }
            }
        });
    }
}
