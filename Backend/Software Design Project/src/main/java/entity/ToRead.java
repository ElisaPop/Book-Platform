package entity;

public class ToRead {
    int userId;
    int bookId;

    public ToRead(int userId, int bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public int getBookId() {
        return bookId;
    }
}
