package model;

import java.sql.Timestamp;
import java.util.Date;

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

    public String getSender() { return sender; }

    public String getContent() {
        return content;
    }

    public String getUid1() {
        return uid1;
    }

    public String getUid2() {
        return uid2;
    }
}
