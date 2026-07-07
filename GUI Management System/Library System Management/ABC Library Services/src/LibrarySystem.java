import java.io.*;
import java.util.*;

public class LibrarySystem {
    ArrayList<Library> library = new ArrayList<>();

    public void addBook(Library p) {
        library.add(p);
    }

    public ArrayList<Library> getAllBooks() {
        return library;
    }

    public void updateBook(int index) {
        if (index >= 0 && index < library.size()) {
            Library l = library.get(index);
            l.setAuthor(l.getAuthor());
            l.setAuthor(l.getAuthor());
            l.setGenre(l.getGenre());
            l.setPublicationDate(l.getPublicationDate());
            l.setShelfLocation(l.getShelfLocation());
        }
    }

    public void removeBook(int index) {
        if (index >= 0 && index < library.size()) {
            library.remove(index);
        }
    }

    private int nextId = 1000001;

    public int generateId() {
        return nextId++;
    }

    public void saveToFile(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Library l : library) {
                pw.println(l.getBookId() + "," +
                        l.getTitle() + "," +
                        l.getAuthor() + "," +
                        l.getGenre() + "," +
                        l.getPublicationDate() + "," +
                        l.getShelfLocation() + "," +
                        l.isAvailable());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void loadFromFile(String filename) {
        File file = new File(filename);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            library.clear();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                int id = Integer.parseInt(data[0]);
                String title = data[1];
                String author = data[2];
                String genre = data[3];
                String pubDate = data[4];
                String shelfLocation = data[5];
                boolean available = Boolean.parseBoolean(data[6]);

                Library l = new Library(id, title, author, genre, pubDate, shelfLocation, available);
                l.setAvailable(available);

                library.add(l);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
