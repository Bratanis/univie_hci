package at.ac.univie.dailykind;

public class TrendingItem {
    private String trendingInfo;
    private String hashtag;
    private String postCount;

    public TrendingItem(String trendingInfo, String hashtag, String postCount) {
        this.trendingInfo = trendingInfo;
        this.hashtag = hashtag;
        this.postCount = postCount;
    }

    public String getTrendingInfo() {
        return trendingInfo;
    }

    public String getHashtag() {
        return hashtag;
    }

    public String getPostCount() {
        return postCount;
    }
}
