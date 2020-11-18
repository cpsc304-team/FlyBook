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

import java.sql.Timestamp;

public class IndividualChat {
    private User sender; //sender
    private User receiver;
    private String content;

    private Timestamp time;

    public IndividualChat(User sender, User receiver, String content, Timestamp time) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.time = time;
    }

    public Timestamp getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }
}
