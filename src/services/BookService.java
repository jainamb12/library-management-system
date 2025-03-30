package services;

import data_structures.Node;
import data_structures.SinglyLinkedList;
import models.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookService {
    private SinglyLinkedList<Book> books;
    private Connection connection;

    public BookService(Connection connection) {
        this.connection = connection;
        this.books = new SinglyLinkedList<>();
        loadBooksFromDatabase();
    }

    public void addBook(Book book) throws SQLException {
        String query = "INSERT INTO books (id, title, author, genre, copies, availability) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, book.getId());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getAuthor());
            statement.setString(4, book.getGenre());
            statement.setInt(5, book.getCopies());
            statement.setBoolean(6, book.isAvailability());
            System.out.println("recorded inserted:- " + statement.executeUpdate());
        }
        books.insertAtLast(book);
    }

    public SinglyLinkedList<Book> listBooksByGenre(String genre) {
        SinglyLinkedList<Book> categoryBooks = new SinglyLinkedList<>();
        Node<Book> temp = books.head;
        while (temp != null) {
            if (temp.data.getGenre().equalsIgnoreCase(genre)) {
                categoryBooks.insertAtLast(temp.data);
            }
            temp = temp.next;
        }
        return categoryBooks;
    }

    public SinglyLinkedList<Book> listBooksByTitle(String title) {
        SinglyLinkedList<Book> categoryBooks = new SinglyLinkedList<>();
        Node<Book> temp = books.head;
        while (temp != null) {
            if (temp.data.getTitle().equalsIgnoreCase(title)) {
                categoryBooks.insertAtLast(temp.data);
            }
            temp = temp.next;
        }
        return categoryBooks;
    }

    public SinglyLinkedList<Book> listBooksByAuthor(String author) {
        SinglyLinkedList<Book> categoryBooks = new SinglyLinkedList<>();
        Node<Book> temp = books.head;
        while (temp != null) {
            if (temp.data.getAuthor().equalsIgnoreCase(author)) {
                categoryBooks.insertAtLast(temp.data);
            }
            temp = temp.next;
        }
        return categoryBooks;
    }

    private void loadBooksFromDatabase() {
        String query = "SELECT * FROM books";
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getString("genre"),
                        resultSet.getInt("copies"),
                        resultSet.getBoolean("availability"));
                books.insertAtLast(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeBook(int id) throws SQLException {
        String query = "DELETE FROM books WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            System.out.println("record deleted: " + statement.executeUpdate());
        }
        Node<Book> current = books.head;
        while (current != null) {
            if (current.data.getId() == id) {
                books.deleteByValue(current.data);
                return;
            }
            current = current.next;
        }
        System.out.println("Book not found.");
    }
}
