package model.group;

/*CREATE TABLE group_joins (
    join_time TIMESTAMP,
    user_id varchar2(10),
    group_id varchar2(10),
    nickname varchar2(20),
    PRIMARY KEY (user_id, group_id),
    FOREIGN KEY (user_id) REFERENCES user_info ON DELETE CASCADE,
    FOREIGN KEY (group_id) REFERENCES group_record ON DELETE CASCADE)*/

import model.user.User;

import java.sql.Timestamp;

public class GroupMember {
    private Timestamp joinTime;
    private User user;
    private Group group;
    private String nickname;

    public GroupMember(Timestamp joinTime, User user, Group group, String nickname) {
        this.joinTime = joinTime;
        this.user = user;
        this.group = group;
        this.nickname = nickname;
    }

    public Timestamp getJoinTime() {
        return joinTime;
    }

    public User getUser() {
        return user;
    }

    public Group getGroup() { return group; }

    public String getNickname() {
        return nickname;
    }
}
