package model;

import oracle.sql.DATE;
import oracle.sql.TIMESTAMP;

public class GroupChatRecord {
    private TIMESTAMP time;
    private String sender;
    private String content;
    private String gid;
    private GroupRecord groupRecord;

    public GroupChatRecord(TIMESTAMP time, String sender, String content, String gid, GroupRecord groupRecord) {
        this.time = time;
        this.sender = sender;
        this.content = content;
        this.gid = gid;
        this.groupRecord = groupRecord;
    }

    public TIMESTAMP getTime() {
        return time;
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public String getGid() {
        return gid;
    }

    public GroupRecord getGroupRecord() {
        return groupRecord;
    }
}

