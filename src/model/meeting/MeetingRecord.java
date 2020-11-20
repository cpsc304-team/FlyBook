package model.meeting;


import model.group.Group;

import java.sql.Timestamp;


/*CREATE TABLE meeting_record (
    meeting_id varchar2(10),
    attendance INTEGER,
    topic varchar2(50),
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    group_id varchar2(10) NOT NULL,
    PRIMARY KEY (meeting_id),
    FOREIGN KEY (group_id) REFERENCES group_record ON DELETE CASCADE);*/

public class MeetingRecord {
    private String meetingid;
    private int attendance;
    private String topic;
    private Timestamp startTime;
    private Timestamp endTime;
    private Group group;

    public MeetingRecord (String meetingid, int attendance, String topic, Timestamp startTime, Timestamp endTime, Group group) {
        this.meetingid = meetingid;
        this.attendance = attendance;
        this.topic = topic;
        this.startTime = startTime;
        this.endTime = endTime;
        this.group = group;
    }

    public String getMeetingid() {
        return meetingid;
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

    public Group getGroup() {
        return group;
    }
}

