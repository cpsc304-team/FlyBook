package model;

/*CREATE TABLE Individual_Chat
    (time TIMESTAMP,
    sender CHAR(20),
    content CHAR(100),
    u_id1 CHAR(10),
    u_id2 CHAR(10),
    PRIMARY KEY (u_id1, u_id2, time),
    FOREIGN KEY (u_id1) REFERENCES User_info ON DELETE CASCADE,
    FOREIGN KEY (u_id2) REFERENCES User_info ON DELETE CASCADE);*/

import oracle.sql.TIMESTAMP;
import java.sql.Timestamp;

public class IndividualChat {
    private String uid1;
    private String uid2;
    private String sender;
    private String content;

    private Timestamp time;

    public IndividualChat(String uid1, String uid2, String sender, String content, Timestamp time) {
        this.uid1 = uid1;
        this.uid2 = uid2;
        this.sender = sender;
        this.content = content;
        this.time = time;
    }

    public Timestamp getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public String getSender() { return sender; }

    public String getUid1() {
        return uid1;
    }

    public String getUid2() {
        return uid2;
    }
}
