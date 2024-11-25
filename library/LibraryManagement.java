package library;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class LibraryManagement extends JFrame {
    private Connection conn;

    Dimension sc = Toolkit.getDefaultToolkit().getScreenSize();
    int width = sc.width;
    int height = sc.height;

    public LibraryManagement() {

        // Establish database connection
        connectDatabase();

        // Set up the GUI
        setTitle("Library Management System");
        setSize(width, height);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a panel for the welcome note and buttons
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; // Make components fill horizontally
        gbc.gridx = 0;
        gbc.insets = new Insets(4, 4, 4, 4); // Margin around components

        // Welcome text
        JLabel welcomeLabel = new JLabel("Welcome to the Library Management System", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Verdana", Font.BOLD, 26));
        gbc.gridy = 0;
        panel.add(welcomeLabel, gbc);

        // Buttons
        JButton addButton = new JButton("Add Book");
        gbc.gridy = 1;
        panel.add(addButton, gbc);
        addButton.addActionListener(e -> addBook());

        JButton updateButton = new JButton("Update Book");
        gbc.gridy = 2;
        panel.add(updateButton, gbc);
        updateButton.addActionListener(e -> updateBook());

        JButton deleteButton = new JButton("Delete Book");
        gbc.gridy = 3;
        panel.add(deleteButton, gbc);
        deleteButton.addActionListener(e -> deleteBook());

        JButton viewButton = new JButton("View Books");
        gbc.gridy = 4;
        panel.add(viewButton, gbc);
        viewButton.addActionListener(e -> viewBooks());

        JButton issueReturnButton = new JButton("Issue Book");
        gbc.gridy = 5;
        panel.add(issueReturnButton, gbc);
        issueReturnButton.addActionListener(e -> new IssueBook(conn));

        JButton ReturnButton = new JButton("Return Book");
        gbc.gridy = 6;
        panel.add(ReturnButton, gbc);
        ReturnButton.addActionListener(e -> new ReturnBook(conn));

        // Add button for viewing issued books
        JButton viewIssuedBooksButton = new JButton("View Issued Books");
        gbc.gridy = 7;
        panel.add(viewIssuedBooksButton, gbc);
        viewIssuedBooksButton.addActionListener(e -> viewIssuedBooks());

        // Add button for viewing returned books
        JButton viewReturnedBooksButton = new JButton("View Returned Books");
        gbc.gridy = 8;
        panel.add(viewReturnedBooksButton, gbc);
        viewReturnedBooksButton.addActionListener(e -> viewReturnedBooks());

        // Create a "Go Back" button
        JButton closeButton = new JButton("Close App");
        gbc.gridy = 9;
        panel.add(closeButton, gbc);

        // Add action listener to the "Go Back" button
        closeButton.addActionListener(e -> dispose());

        add(panel, BorderLayout.CENTER);

        // Show the window
        setVisible(true);
    }

    private void connectDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3306/librarydb";
            String user = "root";
            String pass = "0000"; // Update with your MySQL password
            conn = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addBook() {
        String title = JOptionPane.showInputDialog(this, "Enter Book Title:");
        String author = JOptionPane.showInputDialog(this, "Enter Book Author:");
        String genre = JOptionPane.showInputDialog(this, "Enter Book Genre:");
        int year = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Book Year:"));

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
            e.printStackTrace();
        }
    }

    private void viewBooks() {
        try {
            String query = "SELECT * FROM Books";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Define column names
            String[] columnNames = {"BookID", "Title", "Author", "Genre", "Year"};

            // Load data from the ResultSet into a 2D array
            java.util.List<Object[]> data = new java.util.ArrayList<>();
            while (rs.next()) {
                data.add(new Object[] {
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
            // Set preferred size for JScrollPane
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(width-150, height-200));

            // Show the JTable in a message dialog
            JOptionPane.showMessageDialog(this, scrollPane, "View Books", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error viewing books: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    private void updateBook() {
        int bookId;
        try {
            bookId = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Book ID to Update:"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Book ID. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] options = {"Title", "Author", "Genre", "Year"};
        String field = (String) JOptionPane.showInputDialog(this, "Which field would you like to update?", "Update Book", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (field == null) {
            return; // User canceled
        }

        String newValue = JOptionPane.showInputDialog(this, "Enter new " + field + ":");
        if (newValue == null || newValue.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid " + field + ".", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String query;
            PreparedStatement pst;
            if ("Year".equals(field)) {
                int newYear;
                try {
                    newYear = Integer.parseInt(newValue);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid Year. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                query = "UPDATE Books SET Year = ? WHERE BookID = ?";
                pst = conn.prepareStatement(query);
                pst.setInt(1, newYear);
            } else {
                query = "UPDATE Books SET " + field + " = ? WHERE BookID = ?";
                pst = conn.prepareStatement(query);
                pst.setString(1, newValue);
            }
            pst.setInt(2, bookId);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Book Updated Successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Book ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating book: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    private void deleteBook() {
        int bookId = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Book ID to Delete:"));

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

    private void viewIssuedBooks() {
        try {
            String query = "SELECT * FROM BookIssues WHERE ReturnDate IS NULL";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            String[] columnNames = {"IssueID", "BookID", "Name", "Contact", "IssueDate"};
            java.util.List<Object[]> data = new java.util.ArrayList<>();
            while (rs.next()) {
                data.add(new Object[] {
                        rs.getInt("IssueID"),
                        rs.getInt("BookID"),
                        rs.getString("Name"),
                        rs.getString("Contact"),
                        rs.getTimestamp("IssueDate")
                });
            }
            Object[][] dataArray = data.toArray(new Object[0][]);
            JTable table = new JTable(dataArray, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(width-150, height-200));
            JOptionPane.showMessageDialog(this, scrollPane, "View Issued Books", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error viewing issued books: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void viewReturnedBooks() {
        try {
            String query = "SELECT * FROM BookIssues WHERE ReturnDate IS NOT NULL";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            String[] columnNames = {"IssueID", "BookID", "Name", "Contact", "IssueDate", "ReturnDate"};
            java.util.List<Object[]> data = new java.util.ArrayList<>();
            while (rs.next()) {
                data.add(new Object[] {
                        rs.getInt("IssueID"),
                        rs.getInt("BookID"),
                        rs.getString("Name"),
                        rs.getString("Contact"),
                        rs.getTimestamp("IssueDate"),
                        rs.getTimestamp("ReturnDate")
                });
            }
            Object[][] dataArray = data.toArray(new Object[0][]);
            JTable table = new JTable(dataArray, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(width-150, height-200));
            JOptionPane.showMessageDialog(this, scrollPane, "View Returned Books", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error viewing returned books: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

//
//        System.out.println("Code Run Successful \n");
//        new LibraryManagement();
    }
}

