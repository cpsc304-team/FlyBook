package model;

import oracle.sql.DATE;

public class MeetingRecord {
    private String mid;
    private int attendance;
    private String topic;
    private DATE startTime;
    private DATE endTime;
    private String gid;
    private GroupRecord groupRecord;

    public MeetingRecord (String mid, int attendance, String topic, DATE startTime, DATE endTime,
                          String gid, GroupRecord groupRecord) {
        this.mid = mid;
        this.attendance = attendance;
        this.topic = topic;
        this.startTime = startTime;
        this.endTime = endTime;
        this.gid = gid;
        this.groupRecord = groupRecord;
    }

    public String getMid() {
        return mid;
    }

    public int getAttendance() {
        return attendance;
    }

    public String getTopic() {
        return topic;
    }

    public DATE getStartTime() {
        return startTime;
    }

    public DATE getEndTime() {
        return endTime;
    }

    public String getGid() {
        return gid;
    }

    public GroupRecord getGroupRecord() {
        return groupRecord;
    }
}

