public class Library {
    private int bookId;
    private String title;
    private String author;
    private String genre;
    private String publicationDate;
    private String shelfLocation;
    private boolean isAvailable;

    public Library(int bookId, String title, String author, String genre, String publicationDate, String shelfLocation,
            boolean isAvailable) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationDate = publicationDate;
        this.shelfLocation = shelfLocation;
        this.isAvailable = isAvailable;
    }

    // GETTER
    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public String getShelfLocation() {
        return shelfLocation;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    // SETTER
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setShelfLocation(String shelfLocation) {
        this.shelfLocation = shelfLocation;
    }

    public String getBookInfo() {
        return "Book ID: " + bookId +
                "\nTitle: " + title +
                "\nAuthor: " + author +
                "\nGenre: " + genre +
                "\nYear: " + publicationDate +
                "\nShelf: " + shelfLocation +
                "\nAvailable: " + (isAvailable ? "Yes" : "No");
    }
}