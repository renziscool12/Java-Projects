import javax.swing.*;
import javax.swing.text.MaskFormatter;

import java.awt.*;

public class LibraryForm extends JFrame {
    private JTextField titleField, authorField, shelfLocationField, idField;
    private JComboBox<String> genreBox;
    private JButton addButton, exitButton;
    private JCheckBox availableCheckBox;
    private JFormattedTextField dateField;
    private LibraryGUI mainGui;
    private LibrarySystem librarySystem;
    private Library library;

    public LibraryForm(LibraryGUI mainGui, String titles, Library library) {
        this.mainGui = mainGui;
        this.library = library;

        setTitle(titles);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Book ID:"), gbc);
        idField = new JTextField(10);
        idField.setText(String.valueOf(mainGui.getLibrary().generateId()));
        idField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Title:"), gbc);
        titleField = new JTextField(15);

        gbc.gridx = 1;
        add(titleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Author:"), gbc);
        authorField = new JTextField(15);

        gbc.gridx = 1;
        add(authorField, gbc);

        String[] genres = {
                "Fiction", "Non-Fiction", "Science",
                "Fantasy", "Horror", "Romance", "History"
        };

        genreBox = new JComboBox<>(genres);
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Genre:"), gbc);

        gbc.gridx = 1;
        add(genreBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Publication Date:"), gbc);

        gbc.gridx = 1;
        try {
            MaskFormatter dateMask = new MaskFormatter("##-##-####");
            dateField = new JFormattedTextField(dateMask);
            add(dateField, gbc);
        } catch (Exception e) {
            e.printStackTrace();
        }

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("Shelf Location"), gbc);
        shelfLocationField = new JTextField(15);
        gbc.gridx = 1;
        add(shelfLocationField, gbc);

        availableCheckBox = new JCheckBox("Avaialble");

        gbc.gridx = 1;
        gbc.gridy = 7;
        add(availableCheckBox, gbc);

        addButton = new JButton("Add");
        gbc.gridx = 0;
        gbc.gridy = 8;
        add(addButton, gbc);

        exitButton = new JButton("Exit");
        gbc.gridx = 1;
        add(exitButton, gbc);

        if (library != null) {
            titleField.setText(library.getTitle());
            authorField.setText(library.getAuthor());
            genreBox.setSelectedItem(library.getGenre());
            dateField.setText(String.valueOf(library.getPublicationDate()));
            shelfLocationField.setText(library.getShelfLocation());
            availableCheckBox.setSelected(library.isAvailable());
        }

        // Action Listener for when clicking add
        addButton.addActionListener(e -> {
            String bookIdText = idField.getText();
            String title = titleField.getText();
            String author = authorField.getText();
            String selectedGenre = (String) genreBox.getSelectedItem();
            String publicationDate = dateField.getText();
            String shelfLocation = shelfLocationField.getText();
            boolean available = availableCheckBox.isSelected();

            if (author.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Author Cannot be Empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (bookIdText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID required");
                return;
            }
            int bookId;
            try {
                bookId = Integer.parseInt(bookIdText);
                if (bookId < 0) {
                    JOptionPane.showMessageDialog(this, "Invalid ID", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Numbers Only", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // if empty adds all details on the table
            if (library == null) {
                Library l = new Library(bookId, title, author, selectedGenre, publicationDate, shelfLocation,
                        available);
                l.setAvailable(availableCheckBox.isSelected());
                mainGui.getLibrary().addBook(l);
                mainGui.getLibrary().saveToFile("books.txt");
            } else {
                library.setTitle(title);
                library.setAuthor(author);
                library.setGenre(selectedGenre);
                library.setPublicationDate(publicationDate);
                library.setShelfLocation(shelfLocation);
                library.setAvailable(available);
                mainGui.getLibrary().saveToFile("books.txt");
            }
            mainGui.refreshTable();
            dispose();
        });

        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you to exit?", "Exit Confirmation",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
            }
        });
    }
}
