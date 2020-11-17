package model;

import java.util.Date;

public class IndividualChat {
    private String uid1;
    private String uid2;

    private String content;

    private Date time;

    public IndividualChat(String uid1, String uid2, String content, Date time) {
        this.uid1 = uid1;
        this.uid2 = uid2;
        this.content = content;
        this.time = time;
    }

    public Date getTime() {
        return time;
    }

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
