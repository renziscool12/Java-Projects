import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LibraryGUI extends JFrame {

    private LibrarySystem library;
    DefaultTableModel model = new DefaultTableModel();
    JTable table = new JTable(model);

    public LibraryGUI(LibrarySystem library) {
        setTitle("Library Book Management System");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        this.library = library;
        refreshTable();

        String[] column = { "Book ID", "Title", "Author", "Genre", "Pub. Year", "Shelf Loc.",
                "Avail. Stat" };
        model.setColumnIdentifiers(column);

        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.EAST);

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 10, 20, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        JButton updateButton = new JButton("Update");
        JButton exitButton = new JButton("Exit");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(addButton, gbc);

        gbc.gridy = 1;
        panel.add(deleteButton, gbc);

        gbc.gridy = 2;
        panel.add(updateButton, gbc);

        gbc.gridy = 3;
        panel.add(exitButton, gbc);

        add(panel);

        addButton.addActionListener(e -> {
            new LibraryForm(this, "Add Book", null).setVisible(true);
        });

        updateButton.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row != -1) {
                Library selcetedBook = library.getAllBooks().get(row);
                new LibraryForm(this, "Update Book", selcetedBook).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a book to update");
            }
        });

        deleteButton.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row != -1) {
                library.removeBook(row);
                library.saveToFile("books.txt");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a book to delete");
            }
        });

        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you to exit?", "Exit Confirmation",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
            }
        });

    }

    public LibrarySystem getLibrary() {
        return library;
    }

    public void refreshTable() {
        model.setRowCount(0);
        for (Library l : library.getAllBooks()) {
            model.addRow(new Object[] {
                    l.getBookId(),
                    l.getTitle(),
                    l.getAuthor(),
                    l.getGenre(),
                    l.getPublicationDate(),
                    l.getShelfLocation(),
                    l.isAvailable() ? "Available" : "Unavailable"
            });
        }
        table.clearSelection();
    }

    public static void main(String[] args) {
        LibrarySystem library = new LibrarySystem();
        library.loadFromFile("books.txt");

        LibraryGUI gui = new LibraryGUI(library);
        gui.refreshTable();
        gui.setVisible(true);
    }
}
