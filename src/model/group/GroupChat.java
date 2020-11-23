package model.group;

import java.sql.Timestamp;

public class GroupChat {
    private Timestamp time;
    private GroupMember sender;
    private String content;
    private Group group;

    public GroupChat(Timestamp time, GroupMember sender, String content, Group group) {
        this.time = time;
        this.sender = sender;
        this.content = content;
        this.group = group;
    }

    public Timestamp getTime() {
        return time;
    }

    public GroupMember getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public Group getGroup() {
        return group;
    }
}

