package at.ac.univie.dailykind;

import java.util.Random;

//Datenklasse um Post bestandteile zu gruppieren

public class Post {
    private String title;
    private int imageResId;
    private String description;

    private int profileViewResId;

    private int numberOfLikes;

    public Post(String title, int imageResId, String description, int profilePic) {
        this.title = title;
        this.imageResId = imageResId;
        this.description = description;
        this.profileViewResId = profilePic;
        Random random = new Random();
        do {
            numberOfLikes = random.nextInt(500);
        } while (numberOfLikes < 100);
    }

    public String getTitle() { return title; }
    public int getImageResId() { return imageResId; }
    public String getDescription() { return description; }

    public int getProfileViewResId(){return profileViewResId;}

    public int getNumberOfLikes(){return  numberOfLikes;}
}

