package model;

import oracle.sql.DATE;

public class GroupCreate {
    private DATE createTime;
    private String uid;
    private String gid;
    private User user;
    private GroupRecord groupRecord;

    public GroupCreate(DATE createTime, String uid, String gid, User user, GroupRecord groupRecord) {
        this.createTime = createTime;
        this.uid = uid;
        this.gid = gid;
        this.user = user;
        this.groupRecord = groupRecord;
    }

    public DATE getCreateTime() {
        return createTime;
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

