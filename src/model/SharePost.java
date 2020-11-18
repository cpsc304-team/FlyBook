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
    private Timestamp posttime;
    private String content;
    private String uid;



    public SharePost(String postid, Timestamp posttime, String content, String uid) {
        this.postid = postid;
        this.posttime = posttime;
        this.content = content;
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public String getPostid() {
        return postid;
    }

    public String getUid() {
        return uid;
    }

    public Timestamp getPosttime() {
        return posttime;
    }
}
