package entity;

public class Rating {
    int bookId;
    int userId;
    int rating;

    public Rating(int bookId, int userId, int rating) {
        this.bookId = bookId;
        this.userId = userId;
        this.rating = rating;
    }

    public int getBookId() {
        return bookId;
    }

    public int getUserId() {
        return userId;
    }

    public int getRating() {
        return rating;
    }
}
