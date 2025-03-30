package services;

import data_structures.Node;
import data_structures.SinglyLinkedList;
import models.User;
import utils.DatabaseConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    private SinglyLinkedList<User> users;
    private Connection connection;

    public UserService(Connection connection) {
        this.connection = connection;
        this.users = new SinglyLinkedList<>();
        loadUsersFromDatabase();
    }

    public void registerUser(User user) throws SQLException {
        String query = "INSERT INTO users (id, name, email, password, mobile, role) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getMobile());
            preparedStatement.setString(6, user.getRole());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User added successfully.");
            } else {
                System.out.println("Failed to add user.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }

    public User authenticateUser(String email, String password) {
        Node<User> temp = users.head;
        while (temp != null) {
            User user = temp.data;
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user;
            }
            temp = temp.next;
        }
        return null; // Authentication failed
    }

    public void deleteUserById(int userId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Start a transaction
            connection.setAutoCommit(false);

            // Delete borrowings related to the user
            String deleteBorrowingsSql = "{CALL delete_borrowings_by_user_id(?)}";
            try (CallableStatement deleteBorrowingsStmt = connection.prepareCall(deleteBorrowingsSql)) {
                deleteBorrowingsStmt.setInt(1, userId);
                deleteBorrowingsStmt.executeUpdate();
            }

            // Delete the user
            String deleteUserSql = "{CALL delete_user_by_id(?)}";
            try (CallableStatement deleteUserStmt = connection.prepareCall(deleteUserSql)) {
                deleteUserStmt.setInt(1, userId);
                deleteUserStmt.executeUpdate();
            }

            // Commit the transaction
            connection.commit();
            System.out.println("User with ID " + userId + " and related records have been deleted.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error removing user.");
        }
    }

    public void deleteUserFromListById(int userId) {
        Node<User> temp = users.head;
        Node<User> previous = null;

        while (temp != null) {
            if (temp.data.getId() == userId) {
                if (previous == null) { // Node to delete is the head
                    users.head = temp.next;
                } else {
                    previous.next = temp.next;
                }
                break;
            }
            previous = temp;
            temp = temp.next;
        }
    }

    private void loadUsersFromDatabase() {
        String storedProcedure = "{CALL get_all_users()}";
        try (CallableStatement callableStatement = connection.prepareCall(storedProcedure);
                ResultSet resultSet = callableStatement.executeQuery()) {

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("mobile"),
                        resultSet.getString("role"));
                users.insertAtLast(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public SinglyLinkedList<User> listUsers() {
        return users;
    }
}