package model;

import oracle.sql.DATE;
import oracle.sql.TIMESTAMP;

import java.sql.Timestamp;

/*CREATE TABLE Schedule_Record
    (sid CHAR(10),
    stime TIMESTAMP,
    event CHAR(20),
    u_id CHAR(10) NOT NULL,
    PRIMARY KEY (sid),
    FOREIGN KEY (u_id) REFERENCES User_info,
    FOREIGN KEY (stime) REFERENCES Task_Status);*/

public class ScheduleRecord {
    private String sid;
    private Timestamp stime;
    private String event;
    private String uid;
    private User user;
    private TaskStatus taskStatus;

    public ScheduleRecord(String sid, Timestamp stime, String event, String uid, User user, TaskStatus taskStatus) {
        this.sid = sid;
        this.stime = stime;
        this.event = event;
        this.uid = uid;
        this.user = user;
        this.taskStatus = taskStatus;
    }

    public String getUid() {
        return uid;
    }

    public Timestamp getStime() {
        return stime;
    }

    public String getEvent() {
        return event;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public User getUser() {
        return user;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }
}

