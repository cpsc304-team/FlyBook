package model;

public class PostContains {
    private String postid;
    private String mediaid;
    private SharePost sharePost;
    private Media media;

    public PostContains(String postid, String mediaid, SharePost sharePost, Media media) {
        this.postid = postid;
        this.mediaid = mediaid;
        this.sharePost = sharePost;
        this.media = media;
    }

    public String getPostid() {
        return postid;
    }

    public String getMediaid() {
        return mediaid;
    }

    public SharePost getSharePost() {
        return sharePost;
    }

    public Media getMedia() {
        return media;
    }
}

