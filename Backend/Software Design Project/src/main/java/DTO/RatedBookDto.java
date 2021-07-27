package DTO;

import entity.Book;

public class RatedBookDto {
    int bookId;
    String title;
    String authors;
    Float averageRating;
    int publicationYear;
    int ratingsCount;
    int authorId;
    String photo;
    int rating;

    public RatedBookDto(Book book, int rating){
        this.bookId = book.getBookId();
        this.title = book.getTitle();
        this.authors = book.getAuthors();
        this.averageRating = book.getAverageRating();
        this.publicationYear = book.getPublicationYear();
        this.ratingsCount = book.getRatingsCount();
        this.authorId = book.getAuthorId();
        this.photo = book.getPhoto();
        this.rating = rating;
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

    public String getPhoto() {
        return photo;
    }

    public int getRating() {
        return rating;
    }
}
