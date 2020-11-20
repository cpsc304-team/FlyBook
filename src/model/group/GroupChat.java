package model.group;



import model.user.User;

import java.sql.Timestamp;

public class GroupChat {
    private Timestamp time;
    private User sender;
    private String content;
    private Group group;

    public GroupChat(Timestamp time, User sender, String content, Group group) {
        this.time = time;
        this.sender = sender;
        this.content = content;
        this.group = group;
    }

    public Timestamp getTime() {
        return time;
    }

    public User getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public Group getGroup() {
        return group;
    }
}

