package at.ac.univie.dailykind;

public class Post {
    private String title;
    private int imageResId; // Resource ID for the image
    private String description;

    private int profileViewResId;

    public Post(String title, int imageResId, String description, int profilePic) {
        this.title = title;
        this.imageResId = imageResId;
        this.description = description;
        this.profileViewResId = profilePic;
    }

    // Getters
    public String getTitle() { return title; }
    public int getImageResId() { return imageResId; }
    public String getDescription() { return description; }

    public int getProfileViewResId(){return profileViewResId;}
}

