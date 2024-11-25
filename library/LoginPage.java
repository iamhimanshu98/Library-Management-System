package library;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginPage() {
        Dimension sc = Toolkit.getDefaultToolkit().getScreenSize();
        int width = sc.width;
        int height = sc.height;

        // Set up the frame
        setTitle("Library Management System - Login");
        setSize(width, height);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Apply global font style
        AppFont.setGlobalFont(new FontUIResource(new Font("Verdana", Font.PLAIN, 16)));
        SwingUtilities.updateComponentTreeUI(this);

        // Create UI elements
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel userLabel = new JLabel("Username:");
        usernameField = new JTextField(15);
        JLabel passLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);
        JButton loginButton = new JButton("Login");
        usernameField.setToolTipText("Enter your username");
        passwordField.setToolTipText("Enter your password");

        // Set grid bag constraints for each component
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(userLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(passLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

        // Add panel to frame
        panel.setBackground(Color.LIGHT_GRAY);
        add(panel);
        setVisible(true);

        // Set login button action
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (validateLogin(username, password)) {
                    // Create a JOptionPane
                    JOptionPane pane = new JOptionPane("Successfully Logged In", JOptionPane.INFORMATION_MESSAGE);

                    // Create a dialog with the JOptionPane
                    JDialog dialog = pane.createDialog("Login Successful");

                    // Set up a timer to dispose the dialog after 2 seconds (2000 milliseconds)
                    Timer timer = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            dialog.dispose();
                        }
                    });

                    // Start the timer and show the dialog
                    timer.setRepeats(false); // Ensure the timer only runs once
                    timer.start();
                    dialog.setVisible(true);

                    //JOptionPane.showMessageDialog(null, "Login Successful!");
                    dispose();
                    new LibraryManagement();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password.");
                }
            }
        });
    }

    private boolean validateLogin(String username, String password) {
        // Replace with your actual validation logic
        return "admin".equals(username) && "123".equals(password);
    }

    public static void main(String[] args) {
        try {
            // Set the Nimbus look and feel
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginPage().setVisible(true);
            }
        });
        System.out.println("Code run successful \n");
    }
}