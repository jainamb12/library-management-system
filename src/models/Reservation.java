package models;

public class Reservation {
    private int userId;
    private int bookId;
    private String reservationDate;

    public Reservation(int userId, int bookId, String reservationDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.reservationDate = reservationDate;
    }

    // Getters and setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String toString() {
        return "Reservation{" +
                "userId=" + userId +
                ", bookId=" + bookId +
                ", reservationDate='" + reservationDate + '\'' +
                '}';
    }
}
