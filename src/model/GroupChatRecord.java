package model;



import java.sql.Timestamp;

public class GroupChatRecord {
    private Timestamp time;
    private String sender;
    private String content;
    private String gid;
    private GroupRecord groupRecord;

    public GroupChatRecord(Timestamp time, String sender, String content, String gid, GroupRecord groupRecord) {
        this.time = time;
        this.sender = sender;
        this.content = content;
        this.gid = gid;
        this.groupRecord = groupRecord;
    }

    public Timestamp getTime() {
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

