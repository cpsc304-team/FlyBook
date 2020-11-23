package model.schedule;

import model.user.User;

import java.sql.Timestamp;

/*CREATE TABLE schedule_record (
    schedule_id varchar2(10),
    schedule_time TIMESTAMP,
    event varchar2(50),
    user_id varchar2(10),
    PRIMARY KEY (schedule_id),
    FOREIGN KEY (user_id) REFERENCES user_info ON DELETE CASCADE);*/

public class ScheduleRecord {
    private String scheduleid;
    private Timestamp time;
    private String event;
    private User user;

    public ScheduleRecord(String scheduleid, Timestamp time, String event, User user) {
        this.scheduleid = scheduleid;
        this.time = time;
        this.event = event;
        this.user = user;
    }


    public String getEvent() {
        return event;
    }

    public String getScheduleid() {
        return scheduleid;
    }

    public User getUser() {
        return user;
    }

    public Timestamp getTime() {
        return time;
    }
}

