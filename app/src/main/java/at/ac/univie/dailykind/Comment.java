package at.ac.univie.dailykind;

public class Comment {

    private String name;
    private String comment;
    private int profileId;


    public Comment(String name, String comment, int profileId){
        this.name = name;
        this.comment = comment;
        this.profileId = profileId;
    }

    public String getName() {
        return name;
    }

    public String getComment(){
        return comment;
    }

    public int getProfileId(){
        return profileId;
    }
}

