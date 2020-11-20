package model.group;

/*CREATE TABLE group_record (
    group_id varchar2(10),
    group_name varchar2(30),
    PRIMARY KEY (group_id));*/

/*CREATE TABLE group_creates (
        create_time TIMESTAMP,
        user_id varchar2(10),
        group_id varchar2(10),
        PRIMARY KEY (user_id, group_id),
        FOREIGN KEY (user_id) REFERENCES user_info,
        FOREIGN KEY (group_id) REFERENCES group_record ON DELETE CASCADE);*/

import model.user.User;

import java.sql.Timestamp;

public class Group {
    private String groupid;
    private Timestamp creationTime;
    private String name;
    private User creator;

    public Group(String gid, Timestamp creationTime, String name, User creator) {
        this.groupid = gid;
        this.creationTime = creationTime;
        this.name = name;
        this.creator = creator;
    }

    public String getGroupid() {
        return groupid;
    }

    public Timestamp getCreationTime() { return creationTime; }

    public String getName() {
        return name;
    }

    public User getCreator() { return creator; }
}
