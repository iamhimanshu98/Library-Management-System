package library;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class IssueBook extends JFrame {
    private Connection conn;

    public IssueBook(Connection connection) {
        this.conn = connection;

        // Set up the GUI
        setTitle("Book Issue Management");
        setSize(800, 500);
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
        JLabel bookIdLabel = new JLabel("Book ID:");
        JTextField bookIdField = new JTextField(30);
        bookIdField.setMargin(new Insets(5, 5, 5, 5)); // Add padding inside the text field
        panel.add(bookIdLabel, gbc);
        gbc.gridx = 1;
        panel.add(bookIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(30);
        nameField.setMargin(new Insets(5, 5, 5, 5)); // Add padding inside the text field
        panel.add(nameLabel, gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel contactLabel = new JLabel("Contact:");
        JTextField contactField = new JTextField(30);
        contactField.setMargin(new Insets(5, 5, 5, 5)); // Add padding inside the text field
        panel.add(contactLabel, gbc);
        gbc.gridx = 1;
        panel.add(contactField, gbc);

        // Create a separate panel for buttons to control their size
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton issueButton = new JButton("OK");
        JButton goBackButton = new JButton("Cancel");

        // Set the preferred size for both buttons to make them equal
        Dimension buttonSize = new Dimension(100, 40);
        issueButton.setPreferredSize(buttonSize);
        goBackButton.setPreferredSize(buttonSize);

        buttonPanel.add(issueButton);
        buttonPanel.add(goBackButton);

        // Add buttons to the main panel
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        // Add action listeners to the buttons
        issueButton.addActionListener(e -> issueBook(bookIdField.getText(), nameField.getText(), contactField.getText()));
        goBackButton.addActionListener(e -> dispose());

        // Add panel to the frame
        add(panel, BorderLayout.CENTER);

        // Show the window
        setVisible(true);
    }

    private void issueBook(String bookId, String name, String contact) {
        try {
            String query = "INSERT INTO BookIssues (BookID, Name, Contact) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, Integer.parseInt(bookId));
            pst.setString(2, name);
            pst.setString(3, contact);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Book Issued Successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error issuing book: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
