package entity;

public class Tag {
    int tagId;
    String tagName;

    public Tag(String tagName) {
        this.tagName = tagName;
    }

    public Tag(int tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }

    public int getTagId() {
        return tagId;
    }

    public String getTagName() {
        return tagName;
    }
}
