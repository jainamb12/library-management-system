package services;

import data_structures.SinglyLinkedList;
import data_structures.Node;
import models.Reservation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationService {
    private SinglyLinkedList<Reservation> reservations;
    private Connection connection;

    public ReservationService(Connection connection) {
        this.connection = connection;
        this.reservations = new SinglyLinkedList<>();
        loadReservationsFromDatabase();
    }

    // Method to make a reservation
    public void makeReservation(Reservation reservation) throws SQLException {
        String query = "INSERT INTO reservations (userID, bookID, reservationDate) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, reservation.getUserId());
            statement.setInt(2, reservation.getBookId());
            statement.setString(3, reservation.getReservationDate()); // Using String for reservationDate
            statement.executeUpdate();
        }
        reservations.insertAtLast(reservation);
        System.out.println("=====> Reservation made for userId: " + reservation.getUserId() + ", bookId: "
                + reservation.getBookId());
    }

    // Method to list reservations for a specific user
    public SinglyLinkedList<Reservation> listUserReservations(int userId) {
        SinglyLinkedList<Reservation> userReservations = new SinglyLinkedList<>();
        Node<Reservation> temp = reservations.head;
        while (temp != null) {
            if (temp.data.getUserId() == userId) {
                userReservations.insertAtLast(temp.data);
            }
            temp = temp.next;
        }
        return userReservations;
    }

    // Private method to load reservations from the database
    private void loadReservationsFromDatabase() {
        String query = "SELECT * FROM reservations";
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Reservation reservation = new Reservation(
                        resultSet.getInt("userID"),
                        resultSet.getInt("bookID"),
                        resultSet.getString("reservationDate")); // Using String for reservationDate
                reservations.insertAtLast(reservation);
            }
            System.out.println("=====> Reservations loaded from database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public SinglyLinkedList<Reservation> getAllReservations() {
        return reservations;
    }
}
