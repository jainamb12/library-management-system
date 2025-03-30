package services;

import data_structures.SinglyLinkedList;
import data_structures.Node;
import data_structures.StackOperations;
import models.Borrowing;
import models.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BorrowingService {
    private SinglyLinkedList<Borrowing> borrowings;
    private StackOperations undoStack;
    private Connection connection;
    private SinglyLinkedList<Book> books;

    public BorrowingService(Connection connection) {
        this.connection = connection;
        this.books = new SinglyLinkedList<>();
        ;
        this.borrowings = new SinglyLinkedList<>();
        this.undoStack = new StackOperations(100); // Arbitrary stack size
        loadBorrowingsFromDatabase();
        loadBooksFromDatabase();
    }

    public void borrowBook(int userId, int bookId) throws SQLException {
        Book book = getBookById(bookId);
        if (book == null || book.getCopies() == 0) {
            System.out.println("Book is not available for borrowing.");
            return;
        }

        String query = "INSERT INTO borrowings (userID, bookID, borrowDate, dueDate, returned) VALUES (?, ?, ?, ?, ?)";
        String borrowDate = getCurrentDate();
        String dueDate = getDueDate(7); // 7 days later

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, bookId);
            statement.setString(3, borrowDate);
            statement.setString(4, dueDate);
            statement.setBoolean(5, false);
            statement.executeUpdate();
        }

        Borrowing borrowing = new Borrowing(userId, bookId, borrowDate, dueDate, false);
        borrowings.insertAtLast(borrowing);
        book.borrowCopy(); // Decrease the number of copies
        undoStack.push(bookId); // Push the bookId to the undo stack
        System.out.println("=====> Book borrowed: " + bookId);
    }

    public void returnBook(int userId, int bookId) throws SQLException {
        String query = "UPDATE borrowings SET returned = TRUE WHERE userID = ? AND bookID = ? AND returned = FALSE";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, bookId);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                System.out.println("=====> No active borrowing found for userId: " + userId + " and bookId: " + bookId);
                return;
            }
        }

        Node<Borrowing> temp = borrowings.head;
        while (temp != null) {
            Borrowing borrowing = temp.data;
            if (borrowing.getUserId() == userId && borrowing.getBookId() == bookId && !borrowing.isReturned()) {
                borrowing.setReturned(true);
                break;
            }
            temp = temp.next;
        }

        Book book = getBookById(bookId);
        if (book != null) {
            book.returnCopy(); // Increase the number of copies
        }
        undoStack.push(bookId); // Push the bookId to the undo stack
        System.out.println("=====> Book returned: " + bookId);
    }

    public void undoLastAction() {
        if (undoStack.isEmpty()) {
            System.out.println("No actions to undo.");
            return;
        }
        int lastBookId = undoStack.stack[undoStack.top];
        undoStack.pop();
        Node<Borrowing> temp = borrowings.head;
        while (temp != null) {
            Borrowing borrowing = temp.data;
            if (borrowing.getBookId() == lastBookId && borrowing.isReturned()) {
                borrowing.setReturned(false);
                getBookById(lastBookId).borrowCopy(); // Re-decrement copies
                System.out.println("=====> Undo return of book with ID " + lastBookId);
                return;
            } else if (borrowing.getBookId() == lastBookId && !borrowing.isReturned()) {
                getBookById(lastBookId).returnCopy(); // Re-increment copies
                System.out.println("=====> Undo borrowing of book with ID " + lastBookId);
                return;
            }
            temp = temp.next;
        }
    }

    private void loadBorrowingsFromDatabase() {
        String query = "SELECT * FROM borrowings";
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Borrowing borrowing = new Borrowing(
                        resultSet.getInt("userID"),
                        resultSet.getInt("bookID"),
                        resultSet.getString("borrowDate"),
                        resultSet.getString("dueDate"),
                        resultSet.getBoolean("returned"));
                borrowings.insertAtLast(borrowing);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    // Helper method to get the current date as a string
    private String getCurrentDate() {
        // Return the current date as a String in "yyyy-MM-dd" format (e.g.,
        // "2024-08-21")
        return java.time.LocalDate.now().toString();
    }

    // Helper method to get the due date as a string
    private String getDueDate(int daysLater) {
        // Calculate the due date as a String in "yyyy-MM-dd" format (e.g.,
        // "2024-08-28")
        return java.time.LocalDate.now().plusDays(daysLater).toString();
    }

    // Method to fetch a Book by its ID from the books list
    private Book getBookById(int bookId) {
        Node<Book> temp = books.head;
        while (temp != null) {
            Book book = temp.data;
            if (book.getId() == bookId) {
                return book;
            }
            temp = temp.next;
        }
        return null;
    }

    public SinglyLinkedList<Borrowing> getAllBorrowings() {
        return borrowings;
    }
}