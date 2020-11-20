package model.post;

import model.user.User;

import java.sql.Timestamp;

/*CREATE TABLE share_post (
        post_id varchar2(10),
        time TIMESTAMP,
        user_id varchar2(10) NOT NULL,
        content varchar2(100),
        media_url varchar2(10),
        PRIMARY KEY (post_id),
        FOREIGN KEY (user_id) REFERENCES user_info ON DELETE CASCADE,
        FOREIGN KEY (media_url) REFERENCES media);*/

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
