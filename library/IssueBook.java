package library;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class IssueBook extends JFrame {
    private Connection conn;

    public IssueBook(Connection connection) {
        this.conn = connection;
        Dimension sc = Toolkit.getDefaultToolkit().getScreenSize();
        int width = sc.width;
        int height = sc.height;

        // Set up the GUI
        setTitle("Book Issue Management");
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
        JLabel bookIdLabel = new JLabel("Book ID:");
        JTextField bookIdField = new JTextField(15);
        gbc.gridy = 0;
        panel.add(bookIdLabel, gbc);
        gbc.gridx = 1;
        panel.add(bookIdField, gbc);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(15);
        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(nameLabel, gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        JLabel contactLabel = new JLabel("Contact:");
        JTextField contactField = new JTextField(15);
        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(contactLabel, gbc);
        gbc.gridx = 1;
        panel.add(contactField, gbc);

        // Buttons
        JButton issueButton = new JButton("Issue Book");
        gbc.gridy = 3;
        gbc.gridx = 1;
        panel.add(issueButton, gbc);
        issueButton.addActionListener(e -> issueBook(bookIdField.getText(), nameField.getText(), contactField.getText()));

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
