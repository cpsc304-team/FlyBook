package model;

import java.sql.Timestamp;

/*CREATE TABLE Share_Post
        (postid CHAR(10),
        post_time TIMESTAMP,
        content CHAR(1000),
        u_id CHAR(10) NOT NULL,
        PRIMARY KEY (postid),
        FOREIGN KEY (u_id) REFERENCES User_info);*/

public class SharePost {
    private String postid;
    private Timestamp time;
    private String content;
    private User user;
    private Media media;

    public SharePost(String postid, Timestamp time, String content, User user, Media media) {
        this.postid = postid;
        this.time = time;
        this.content = content;
        this.user = user;
        this.media = media;
    }

    public String getContent() {
        return content;
    }

    public String getPostid() {
        return postid;
    }

    public User getUser() {
        return user;
    }

    public Timestamp getTime() {
        return time;
    }

    public Media getMedia() { return media; }
}
