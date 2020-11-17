package model;

import oracle.sql.DATE;

public class GroupJoins {
    private DATE jointime;
    private String uid;
    private String gid;
    private User user;
    private GroupRecord groupRecord;

    public GroupJoins(DATE jointime, String uid, String gid, User user, GroupRecord groupRecord) {
        this.jointime = jointime;
        this.uid = uid;
        this.gid = gid;
        this.user = user;
        this.groupRecord = groupRecord;
    }

    public DATE getJoinTime() {
        return jointime;
    }

    public String getUid() {
        return uid;
    }

    public String getGid() {
        return gid;
    }

    public User getUser() {
        return user;
    }

    public GroupRecord getGroupRecord() {
        return groupRecord;
    }
}
