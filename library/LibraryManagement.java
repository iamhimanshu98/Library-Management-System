package library;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;

public class LibraryManagement extends Methods { // Extend Methods class

    public LibraryManagement() {

        // Establish database connection
        connectDatabase();

        // Set up the GUI
        setTitle("Library Management System");
        setSize(getWidth(), getHeight());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Apply global font style
        AppFont.setGlobalFont(new FontUIResource(new Font("Verdana", Font.PLAIN, 16)));
        SwingUtilities.updateComponentTreeUI(this);

        // Create UI elements
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("./img/light1.png");
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };

        mainPanel.setOpaque(false);

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0)); // Add top margin
        JLabel welcomeLabel = new JLabel("Welcome to the Library Management System", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Verdana", Font.BOLD, 26));
        welcomeLabel.setForeground(Color.BLACK);
        headerPanel.add(welcomeLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER; // Center align buttons

        // Buttons
        JButton addButton = new JButton("Add Book");
        styleButton(addButton);
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(addButton, gbc);
        addButton.addActionListener(e -> addBook());

        JButton deleteButton = new JButton("Delete Book");
        styleButton(deleteButton);
        gbc.gridy = 2;
        buttonPanel.add(deleteButton, gbc);
        deleteButton.addActionListener(e -> deleteBook());

        JButton viewButton = new JButton("View Books");
        styleButton(viewButton);
        gbc.gridy = 3;
        buttonPanel.add(viewButton, gbc);
        viewButton.addActionListener(e -> viewBooks());

        JButton issueReturnButton = new JButton("Issue Book");
        styleButton(issueReturnButton);
        gbc.gridy = 4;
        buttonPanel.add(issueReturnButton, gbc);
        issueReturnButton.addActionListener(e -> new IssueBook(conn));

        JButton returnButton = new JButton("Return Book");
        styleButton(returnButton);
        gbc.gridy = 5;
        buttonPanel.add(returnButton, gbc);
        returnButton.addActionListener(e -> new ReturnBook(conn));

        JButton viewIssuedBooksButton = new JButton("View Issued Books");
        styleButton(viewIssuedBooksButton);
        gbc.gridy = 6;
        buttonPanel.add(viewIssuedBooksButton, gbc);
        viewIssuedBooksButton.addActionListener(e -> viewIssuedBooks());

        JButton viewReturnedBooksButton = new JButton("View Returned Books");
        styleButton(viewReturnedBooksButton);
        gbc.gridy = 7;
        buttonPanel.add(viewReturnedBooksButton, gbc);
        viewReturnedBooksButton.addActionListener(e -> viewReturnedBooks());

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(300, 40)); // Set fixed width and height
        button.setBackground(new Color(94, 147, 182)); // Adding transparency
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Verdana", Font.PLAIN, 14));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LibraryManagement().setVisible(true);
            }
        });
    }
}
