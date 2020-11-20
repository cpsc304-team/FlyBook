package model;

import model.user.User;

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
    private String event;
    private User user;
    private TaskStatus taskStatus;

    public ScheduleRecord(String sid, String event, User user, TaskStatus taskStatus) {
        this.sid = sid;
        this.event = event;
        this.user = user;
        this.taskStatus = taskStatus;
    }


    public String getEvent() {
        return event;
    }

    public String getSid() {
        return sid;
    }

    public User getUser() {
        return user;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }
}

