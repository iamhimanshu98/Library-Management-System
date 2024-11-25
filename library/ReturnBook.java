package library;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class ReturnBook extends JFrame {
    private Connection conn;

    public ReturnBook(Connection connection) {
        this.conn = connection;
        Dimension sc = Toolkit.getDefaultToolkit().getScreenSize();
        int width = sc.width;
        int height = sc.height;

        // Set up the GUI
        setTitle("Book Return Management");
        setSize(width, height);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a panel for the buttons and form fields
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; // Make components fill horizontally
        gbc.gridx = 0;
        gbc.insets = new Insets(4, 4, 4, 4); // Margin around components

        // Form fields
        JLabel issueIdLabel = new JLabel("Issue ID:");
        JTextField issueIdField = new JTextField(15);
        gbc.gridy = 0;
        panel.add(issueIdLabel, gbc);
        gbc.gridx = 1;
        panel.add(issueIdField, gbc);

//        JLabel nameLabel = new JLabel("Name:");
//        JTextField nameField = new JTextField(15);
//        gbc.gridy = 1;
//        gbc.gridx = 0;
//        panel.add(nameLabel, gbc);
//        gbc.gridx = 1;
//        panel.add(nameField, gbc);
//
//        JLabel contactLabel = new JLabel("Contact:");
//        JTextField contactField = new JTextField(15);
//        gbc.gridy = 2;
//        gbc.gridx = 0;
//        panel.add(contactLabel, gbc);
//        gbc.gridx = 1;
//        panel.add(contactField, gbc);


        JButton returnButton = new JButton("Return Book");
        gbc.gridy = 3;
        gbc.gridx = 1;
        panel.add(returnButton, gbc);
        returnButton.addActionListener(e -> returnBook(issueIdField.getText()));

        // Create a "Go Back" button
        JButton goBackButton = new JButton("Go Back");
        gbc.gridy = 3;
        gbc.gridx = 0;
        panel.add(goBackButton, gbc);

        // Add action listener to the "Go Back" button
        goBackButton.addActionListener(e -> dispose());

        // Add panel to the frame
        add(panel, BorderLayout.CENTER);

        // Show the window
        setVisible(true);
    }

    private void returnBook(String issueId) {
        try {
            String query = "UPDATE BookIssues SET ReturnDate = CURRENT_TIMESTAMP WHERE IssueID = ? AND ReturnDate IS NULL";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, Integer.parseInt(issueId));
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Book Returned Successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "No issued book found with given Issue ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error returning book: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
