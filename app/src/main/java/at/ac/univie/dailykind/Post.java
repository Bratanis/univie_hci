package at.ac.univie.dailykind;

public class Post {
    private String title;
    private int imageResId; // Resource ID for the image
    private String description;

    public Post(String title, int imageResId, String description) {
        this.title = title;
        this.imageResId = imageResId;
        this.description = description;
    }

    // Getters
    public String getTitle() { return title; }
    public int getImageResId() { return imageResId; }
    public String getDescription() { return description; }
}

