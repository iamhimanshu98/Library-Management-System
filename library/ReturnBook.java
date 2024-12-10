package library;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class ReturnBook extends JFrame {
    private Connection conn;

    public ReturnBook(Connection connection) {
        this.conn = connection;

        // Set up the GUI
        setTitle("Book Return Management");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a panel for the buttons and form fields
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margin around components
        gbc.anchor = GridBagConstraints.CENTER; // Center align components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Form fields
        JLabel issueIdLabel = new JLabel("Issue ID:");
        JTextField issueIdField = new JTextField(30);
        issueIdField.setMargin(new Insets(5, 5, 5, 5)); // Add padding inside the text field
        panel.add(issueIdLabel, gbc);
        gbc.gridx = 1;
        panel.add(issueIdField, gbc);

        // Create a separate panel for buttons to control their size
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton returnButton = new JButton("OK");
        JButton goBackButton = new JButton("Cancel");

        // Set the preferred size for both buttons to make them equal
        Dimension buttonSize = new Dimension(100, 40);
        returnButton.setPreferredSize(buttonSize);
        goBackButton.setPreferredSize(buttonSize);

        buttonPanel.add(returnButton);
        buttonPanel.add(goBackButton);

        // Add buttons to the main panel
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        // Add action listeners to the buttons
        returnButton.addActionListener(e -> returnBook(issueIdField.getText()));
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
