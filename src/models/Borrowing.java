package models;

public class Borrowing {
    private int userId;
    private int bookId;
    private String borrowDate;
    private String dueDate;
    private boolean returned;

    public Borrowing(int userId, int bookId, String borrowDate, String dueDate, boolean returned) {
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returned = returned;
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

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public String toString() {
        return "Borrowing{" +
                "userId=" + userId +
                ", bookId=" + bookId +
                ", borrowDate='" + borrowDate + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", returned=" + returned +
                '}';
    }
}
