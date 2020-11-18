package model;


/*CREATE TABLE Post_Contains
    (postid CHAR(10),
    mediaid CHAR(10),
    PRIMARY KEY (postid,mediaid),
    FOREIGN KEY (postid) REFERENCES Share_Post,
    FOREIGN KEY (mediaid) REFERENCES Media);*/

public class PostContains {
    private String postid;
    private String mediaid;
//    private SharePost sharePost;
//    private Media media;

    public PostContains(String postid, String mediaid) {
        this.postid = postid;
        this.mediaid = mediaid;
//        this.sharePost = sharePost;
//        this.media = media;
    }

    public String getPostid() {
        return postid;
    }

    public String getMediaid() {
        return mediaid;
    }
}

