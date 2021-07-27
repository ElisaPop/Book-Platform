package entity;

public class BookTag {


    int bookId;
    int tagId;
    int count;


    public BookTag(int bookId, int tagId, int count) {
        this.bookId = bookId;
        this.tagId = tagId;
        this.count = count;
    }

    public int getBookId() {
        return bookId;
    }

    public int getTagId() {
        return tagId;
    }

    public int getCount() {
        return count;
    }
}
