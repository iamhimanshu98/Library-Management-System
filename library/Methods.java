package library;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.sql.*;

public class Methods extends JFrame {
    protected Connection conn;

    Dimension sc = Toolkit.getDefaultToolkit().getScreenSize();
    int width = sc.width;
    int height = sc.height;

    public void connectDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3306/librarydb";
            String user = "root";
            String pass = "0000"; // Update with your MySQL password
            conn = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void addBook() {
        // Create a form for book input
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(800, 320));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margin around components
        gbc.anchor = GridBagConstraints.CENTER; // Center align components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel titleLabel = new JLabel("Title:");
        JTextField titleField = new JTextField(30);
        titleField.setMargin(new Insets(5, 5, 5, 5)); // Add padding inside the text field
        panel.add(titleLabel, gbc);
        gbc.gridx = 1;
        panel.add(titleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel authorLabel = new JLabel("Author:");
        JTextField authorField = new JTextField(30);
        authorField.setMargin(new Insets(5, 5, 5, 5)); // Add padding inside the text field
        panel.add(authorLabel, gbc);
        gbc.gridx = 1;
        panel.add(authorField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel genreLabel = new JLabel("Genre:");
        JTextField genreField = new JTextField(30);
        genreField.setMargin(new Insets(5, 5, 5, 5)); // Add padding inside the text field
        panel.add(genreLabel, gbc);
        gbc.gridx = 1;
        panel.add(genreField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel yearLabel = new JLabel("Year:");
        JTextField yearField = new JTextField(30);
        yearField.setMargin(new Insets(5, 5, 5, 5)); // Add padding inside the text field
        panel.add(yearLabel, gbc);
        gbc.gridx = 1;
        panel.add(yearField, gbc);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Book", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String title = titleField.getText();
            String author = authorField.getText();
            String genre = genreField.getText();
            int year;
            try {
                year = Integer.parseInt(yearField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid Year. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                String query = "INSERT INTO Books (Title, Author, Genre, Year) VALUES (?, ?, ?, ?)";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, title);
                pst.setString(2, author);
                pst.setString(3, genre);
                pst.setInt(4, year);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Book Added Successfully!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error adding book: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    protected void deleteBook() {
        // Create a form for deleting book
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(800, 180));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margin around components
        gbc.anchor = GridBagConstraints.CENTER; // Center align components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel bookIdLabel = new JLabel("Book ID:");
        JTextField bookIdField = new JTextField(30);
        bookIdField.setMargin(new Insets(5, 5, 5, 5)); // Add padding inside the text field
        panel.add(bookIdLabel, gbc);
        gbc.gridx = 1;
        panel.add(bookIdField, gbc);

        int result = JOptionPane.showConfirmDialog(this, panel, "Delete Book", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            int bookId;
            try {
                bookId = Integer.parseInt(bookIdField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid Book ID. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                String query = "DELETE FROM Books WHERE BookID = ?";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, bookId);
                int rowsAffected = pst.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Book Deleted Successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Book ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error deleting book: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    protected void viewBooks() {
        try {
            String query = "SELECT * FROM Books";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Define column names
            String[] columnNames = {"BookID", "Title", "Author", "Genre", "Year"};

            // Load data from the ResultSet into a 2D array
            java.util.List<Object[]> data = new java.util.ArrayList<>();
            while (rs.next()) {
                data.add(new Object[]{
                        rs.getInt("BookID"),
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getString("Genre"),
                        rs.getInt("Year")
                });
            }

            // Convert the list to a 2D array
            Object[][] dataArray = data.toArray(new Object[0][]);

            // Create the JTable with the data
            JTable table = new JTable(dataArray, columnNames);
            table.setRowHeight(26);
            table.getColumnModel().getColumn(1).setPreferredWidth(200);
            // Set preferred size for JScrollPane
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(1000, 600));

            // Show the JTable in a message dialog
            JOptionPane.showMessageDialog(this, scrollPane, "View Books", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error viewing books: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    protected void viewIssuedBooks() {
        try {
            String query = "SELECT * FROM BookIssues WHERE ReturnDate IS NULL";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            String[] columnNames = {"IssueID", "BookID", "Name", "Contact", "IssueDate"};
            java.util.List<Object[]> data = new java.util.ArrayList<>();
            while (rs.next()) {
                java.sql.Timestamp issueTimestamp = rs.getTimestamp("IssueDate");
                String issueDate = issueTimestamp.toLocalDateTime().toLocalDate().toString();
                String issueTime = issueTimestamp.toLocalDateTime().toLocalTime().toString().substring(0, 5);
                data.add(new Object[]{
                        rs.getInt("IssueID"),
                        rs.getInt("BookID"),
                        rs.getString("Name"),
                        rs.getString("Contact"),
                        issueDate + " " + issueTime
                });
            }
            Object[][] dataArray = data.toArray(new Object[0][]);
            JTable table = new JTable(dataArray, columnNames);
            table.setRowHeight(26);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(1000, 600));
            JOptionPane.showMessageDialog(this, scrollPane, "View Issued Books", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error viewing issued books: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    protected void viewReturnedBooks() {
        try {
            String query = "SELECT * FROM BookIssues WHERE ReturnDate IS NOT NULL";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            String[] columnNames = {"IssueID", "BookID", "Name", "Contact", "IssueDate", "ReturnDate"};
            java.util.List<Object[]> data = new java.util.ArrayList<>();
            while (rs.next()) {
                java.sql.Timestamp issueTimestamp = rs.getTimestamp("IssueDate");
                java.sql.Timestamp returnTimestamp = rs.getTimestamp("ReturnDate");
                String issueDate = issueTimestamp.toLocalDateTime().toLocalDate().toString();
                String issueTime = issueTimestamp.toLocalDateTime().toLocalTime().toString().substring(0, 5);
                String returnDate = returnTimestamp.toLocalDateTime().toLocalDate().toString();
                String returnTime = returnTimestamp.toLocalDateTime().toLocalTime().toString().substring(0, 5);
                data.add(new Object[]{
                        rs.getInt("IssueID"),
                        rs.getInt("BookID"),
                        rs.getString("Name"),
                        rs.getString("Contact"),
                        issueDate + " " + issueTime,
                        returnDate + " " + returnTime
                });
            }
            Object[][] dataArray = data.toArray(new Object[0][]);
            JTable table = new JTable(dataArray, columnNames);
            table.setRowHeight(26);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(1000, 600));
            JOptionPane.showMessageDialog(this, scrollPane, "View Returned Books", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error viewing returned books: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}