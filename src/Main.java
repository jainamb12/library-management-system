import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import services.BookService;
import services.UserService;
import services.BorrowingService;
import services.ReservationService;
import utils.DatabaseConnection;
import models.Book;
import models.User;
import models.Borrowing;
import models.Reservation;
import data_structures.Node;
import data_structures.SinglyLinkedList;

public class Main {

    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Create service instances
            BookService bookService = new BookService(connection);
            UserService userService = new UserService(connection);
            BorrowingService borrowingService = new BorrowingService(connection);
            ReservationService reservationService = new ReservationService(connection);

            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println("..................................................");
                System.out.println("..................................................");
                System.out.println("..                   WELCOME                    ..");
                System.out.println("..                    TO                        ..");
                System.out.println("..                  LIBRARY                     ..");
                System.out.println("..................................................");
                System.out.println("..................................................");
                System.out.println("1. User Login");
                System.out.println("2. Admin Login");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        handleUser(scanner, userService, bookService, borrowingService, reservationService);
                        break;
                    case 2:
                        handleAdmin(scanner, bookService, userService, borrowingService, reservationService);
                        break;
                    case 3:
                        running = false;
                        System.out.println("Exiting the system. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                }
            }
            scanner.close();
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
    }

    // Handles user operations
    public static void handleUser(Scanner scanner, UserService userService, BookService bookService,
            BorrowingService borrowingService, ReservationService reservationService) {
        System.out.print("Enter your email: ");
        String email = scanner.next();
        System.out.print("Enter your password: ");
        String password = scanner.next();

        User user = userService.authenticateUser(email, password);

        if (user != null) {
            System.out.println("\nWelcome, " + user.getName() + "!");
            boolean userRunning = true;
            while (userRunning) {
                System.out.println("\n=========== User Menu ==========");
                System.out.println("1. Search Books");
                System.out.println("2. Borrow Book");
                System.out.println("3. Return Book");
                System.out.println("4. Make Reservation");
                System.out.println("5. View My Reservations");
                System.out.println("6. Logout");
                System.out.print("Choose an option: ");
                System.out.println("====================================");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        searchBooks(scanner, bookService);
                        break;
                    case 2:
                        borrowBook(scanner, user.getId(), borrowingService);
                        break;
                    case 3:
                        returnBook(scanner, user.getId(), borrowingService);
                        break;
                    case 4:
                        makeReservation(scanner, user.getId(), reservationService);
                        break;
                    case 5:
                        viewReservations(user.getId(), reservationService);
                        break;
                    case 6:
                        userRunning = false;
                        System.out.println("You have been logged out.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } else {
            System.out.println("Login failed. Incorrect email or password.");
        }
    }

    // Handles admin operations
    private static void handleAdmin(Scanner scanner, BookService bookService, UserService userService,
            BorrowingService borrowingService, ReservationService reservationService) {
        System.out.print("Enter admin password: ");
        String password = scanner.next();

        if ("password789".equals(password)) { // Replace with secure check
            System.out.println("\nAdmin login successful.");
            boolean adminRunning = true;
            while (adminRunning) {
                System.out.println("\n========== Admin Menu ==========");
                System.out.println("1. Add Book");
                System.out.println("2. Remove Book");
                System.out.println("3. View All Users");
                System.out.println("4. Add User");
                System.out.println("5. Remove User");
                System.out.println("6. View All Borrowings");
                System.out.println("7. View All Reservations");
                System.out.println("8. Logout");
                System.out.print("Choose an option: ");
                System.out.println("====================================");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        addBook(scanner, bookService);
                        break;
                    case 2:
                        removeBook(scanner, bookService);
                        break;
                    case 3:
                        viewAllUsers(userService);
                        break;
                    case 4:
                        addUser(scanner, userService);
                        break;
                    case 5:
                        removeUser(scanner, userService);
                        break;
                    case 6:
                        viewAllBorrowings(borrowingService);
                        break;
                    case 7:
                        viewAllReservations(reservationService);
                        break;
                    case 8:
                        adminRunning = false;
                        System.out.println("Admin logged out.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } else {
            System.out.println("Admin authentication failed. Incorrect password.");
        }
    }

    // Handles adding a new user
    private static void addUser(Scanner scanner, UserService userService) {
        System.out.print("Enter user ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.println("Enter mobile:");
        String mobile = scanner.nextLine();
        System.out.println("Enter role:");
        String role = scanner.nextLine();

        User user = new User(id, name, email, password, mobile, role);
        try {
            userService.registerUser(user);
            System.out.println("User added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }

    // Handles removing an existing user
    private static void removeUser(Scanner scanner, UserService userService) {
        System.out.print("Enter user ID to remove: ");
        int userId = scanner.nextInt();
        try {
            userService.deleteUserById(userId);
        } catch (Exception e) {
            System.out.println("Error removing user: " + e.getMessage());
        }
    }

    // Searches for books by title, author, or genre
    private static void searchBooks(Scanner scanner, BookService bookService) {
        System.out.println("\n===== Search Books =====");
        System.out.println("1. By Title");
        System.out.println("2. By Author");
        System.out.println("3. By Genre");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1: {
                System.out.print("Enter title: ");
                String title = scanner.nextLine();
                SinglyLinkedList<Book> categoryBooks = bookService.listBooksByTitle(title);
                displayBooks(categoryBooks);
                break;
            }
            case 2: {
                System.out.print("Enter author: ");
                String author = scanner.nextLine();
                SinglyLinkedList<Book> categoryBooks = bookService.listBooksByAuthor(author);
                displayBooks(categoryBooks);
                break;
            }
            case 3:
                System.out.print("Enter genre: ");
                String genre = scanner.nextLine();
                SinglyLinkedList<Book> categoryBooks = bookService.listBooksByGenre(genre);
                displayBooks(categoryBooks);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    // Displays a list of books
    private static void displayBooks(SinglyLinkedList<Book> books) {
        if (books.head == null) {
            System.out.println("No books found.");
        } else {
            Node<Book> current = books.head;
            System.out.println("\nBooks found:");
            while (current != null) {
                System.out.println(current.data);
                current = current.next;
            }
        }
    }

    // Handles borrowing of a book
    private static void borrowBook(Scanner scanner, int userId, BorrowingService borrowingService) {
        System.out.print("Enter book ID to borrow: ");
        int bookId = scanner.nextInt();
        try {
            borrowingService.borrowBook(userId, bookId);
            System.out.println("Book borrowed successfully.");
        } catch (SQLException e) {
            System.out.println("Error borrowing book: " + e.getMessage());
        }
    }

    // Handles returning of a book
    private static void returnBook(Scanner scanner, int userId, BorrowingService borrowingService) {
        System.out.print("Enter book ID to return: ");
        int bookId = scanner.nextInt();
        try {
            borrowingService.returnBook(userId, bookId);
            System.out.println("Book returned successfully.");
        } catch (SQLException e) {
            System.out.println("Error returning book: " + e.getMessage());
        }
    }

    // Handles making a reservation
    private static void makeReservation(Scanner scanner, int userId, ReservationService reservationService) {
        System.out.print("Enter book ID to reserve: ");
        int bookId = scanner.nextInt();
        try {
            String date = java.time.LocalDate.now().toString(); // Use current date as String
            Reservation reservation = new Reservation(userId, bookId, date);
            reservationService.makeReservation(reservation);
            System.out.println("Reservation made successfully.");
        } catch (SQLException e) {
            System.out.println("Error making reservation: " + e.getMessage());
        }
    }

    // Displays user’s reservations
    private static void viewReservations(int userId, ReservationService reservationService) {
        SinglyLinkedList<Reservation> reservations = reservationService.listUserReservations(userId);
        if (reservations.head == null) {
            System.out.println("No reservations found.");
        } else {
            System.out.println("\nYour Reservations:");
            Node<Reservation> temp = reservations.head;
            while (temp != null) {
                System.out.println(temp.data);
                temp = temp.next;
            }
        }
    }

    // Handles adding a new book
    private static void addBook(Scanner scanner, BookService bookService) {
        System.out.print("Enter book ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();
        System.out.println("Enter number of copies:");
        int copies = scanner.nextInt();
        System.out.print("Is the book available (true/false): ");
        boolean availability = scanner.nextBoolean();

        Book book = new Book(id, title, author, genre, copies, availability);
        try {
            bookService.addBook(book);
            System.out.println("Book added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    // Handles removing an existing book
    private static void removeBook(Scanner scanner, BookService bookService) {
        System.out.print("Enter book ID to remove: ");
        int bookId = scanner.nextInt();
        try {
            bookService.removeBook(bookId);
            System.out.println("Book removed successfully.");
        } catch (SQLException e) {
            System.out.println("Error removing book: " + e.getMessage());
        }
    }

    // Displays all users
    private static void viewAllUsers(UserService userService) {
        SinglyLinkedList<User> users = userService.listUsers();
        if (users.head == null) {
            System.out.println("No users found.");
        } else {
            System.out.println("\nAll Users:");
            Node<User> current = users.head;
            while (current != null) {
                System.out.println(current.data);
                current = current.next;
            }
        }
    }

    // Displays all borrowings
    private static void viewAllBorrowings(BorrowingService borrowingService) {
        SinglyLinkedList<Borrowing> borrowings = borrowingService.getAllBorrowings();
        if (borrowings.head == null) {
            System.out.println("No borrowings found.");
        } else {
            System.out.println("\nAll Borrowings:");
            Node<Borrowing> current = borrowings.head;
            while (current != null) {
                System.out.println(current.data);
                current = current.next;
            }
        }
    }

    // Displays all reservations
    private static void viewAllReservations(ReservationService reservationService) {
        SinglyLinkedList<Reservation> reservations = reservationService.getAllReservations();
        if (reservations.head == null) {
            System.out.println("No reservations found.");
        } else {
            System.out.println("\nAll Reservations:");
            Node<Reservation> current = reservations.head;
            while (current != null) {
                System.out.println(current.data);
                current = current.next;
            }
        }
    }
}