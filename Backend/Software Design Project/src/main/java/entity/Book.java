package entity;

public class Book {
    int bookId;
    String title;
    String authors;
    Float averageRating;
    int publicationYear;
    int ratingsCount;
    int authorId;
    String photo;

    public Book(int bookId, String title, String authors, Float averageRating,
                int publicationYear, int ratingsCount, String photo) {
        this.bookId = bookId;
        this.title = title;
        this.authors = authors;
        this.averageRating = averageRating;
        this.publicationYear = publicationYear;
        this.ratingsCount = ratingsCount;
        this.photo = photo;
    }

    public Book(int bookId, String title, String authors, Float averageRating,
                int publicationYear, int ratingsCount, int authorId, String photo ) {
        this.bookId = bookId;
        this.title = title;
        this.authors = authors;
        this.averageRating = averageRating;
        this.publicationYear = publicationYear;
        this.ratingsCount = ratingsCount;
        this.authorId = authorId;
        this.photo = photo;
    }

    public Book() {

    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public Float getAverageRating() {
        return averageRating;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public int getRatingsCount() {
        return ratingsCount;
    }

    public int getAuthorId() {
        return authorId;
    }

    public String getPhoto() { return photo; }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public void setAverageRating(Float averageRating) {
        this.averageRating = averageRating;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setRatingsCount(int ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
