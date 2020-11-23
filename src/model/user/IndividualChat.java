package model.user;

/*CREATE TABLE individual_chat (
    time TIMESTAMP,
    sender varchar2(10),
    receiver varchar2(10),
    content varchar2(100),
    PRIMARY KEY (sender, receiver, time),
    FOREIGN KEY (sender) REFERENCES user_info ON DELETE CASCADE,
    FOREIGN KEY (receiver) REFERENCES user_info ON DELETE CASCADE);*/

import model.user.User;

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
