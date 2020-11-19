package model;


/*CREATE TABLE Post_Contains
    (postid CHAR(10),
    mediaid CHAR(10),
    PRIMARY KEY (postid,mediaid),
    FOREIGN KEY (postid) REFERENCES Share_Post,
    FOREIGN KEY (mediaid) REFERENCES Media);*/

public class PostContains {
    private SharePost sharePost;
    private Media media;

    public PostContains(SharePost sharePost, Media media) {
        this.sharePost = sharePost;
        this.media = media;
    }

    public Media getMedia() {
        return media;
    }

    public SharePost getSharePost() {
        return sharePost;
    }
}

