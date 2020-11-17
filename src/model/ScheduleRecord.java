package model;

import oracle.sql.DATE;

public class ScheduleRecord {
    private String sid;
    private DATE stime;
    private String event;
    private String uid;
    private User user;
    private TaskStatus taskStatus;

    public ScheduleRecord(String sid, DATE stime, String event, String uid, User user, TaskStatus taskStatus) {
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

    public DATE getStime() {
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

