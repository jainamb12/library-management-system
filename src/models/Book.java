package models;

public class Book {
    private int id;
    private String title;
    private String author;
    private String genre;
    private int copies;
    private boolean availability;

    public Book(int id, String title, String author, String genre, int copies, boolean availability) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.copies = copies;
        this.availability = availability;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public void borrowCopy() {
        if (copies > 0) {
            copies--;
        } else {
            System.out.println("No more copies available to borrow.");
        }
    }

    public void returnCopy() {
        copies++;
    }

    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", copies=" + copies +
                ", availability=" + availability +
                '}';
    }
}