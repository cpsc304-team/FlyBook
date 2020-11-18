package model;


import java.sql.Timestamp;


/*CREATE TABLE Meeting_Record
    (mid CHAR(10), attendance INTEGER, topic CHAR(50),
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    gid CHAR (10) NOT NULL, PRIMARY KEY (mid),
    FOREIGN KEY (gid) REFERENCES Group_Record ON DELETE SET NULL);*/

public class MeetingRecord {
    private String mid;
    private int attendance;
    private String topic;
    private Timestamp startTime;
    private Timestamp endTime;
    private String gid;
//    private GroupRecord groupRecord;

    public MeetingRecord (String mid, int attendance, String topic, Timestamp startTime, Timestamp endTime, String gid) {
        this.mid = mid;
        this.attendance = attendance;
        this.topic = topic;
        this.startTime = startTime;
        this.endTime = endTime;
        this.gid = gid;
//        this.groupRecord = groupRecord;
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

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public String getGid() {
        return gid;
    }
}

