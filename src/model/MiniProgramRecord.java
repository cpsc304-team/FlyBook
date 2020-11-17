package model;

import oracle.sql.TIMESTAMP;

/*CREATE TABLE MiniProgram_Record
    (u_id CHAR(10),
    pid CHAR(10),
    time TIMESTAMP,
    PRIMARY KEY (u_id, pid, time),
    FOREIGN KEY (u_id) REFERENCES User_info,
    FOREIGN KEY (pid) REFERENCES Mini_Program);*/

public class MiniProgramRecord {
    private String uid;
    private String pid;
    private TIMESTAMP time;
    private User user;
    private MiniProgram miniProgram;

    public MiniProgramRecord(String uid, String pid, TIMESTAMP time, User user, MiniProgram miniProgram) {
        this.uid = uid;
        this.pid = pid;
        this.time = time;
        this.user = user;
        this.miniProgram = miniProgram;
    }

    public String getUid() {
        return uid;
    }

    public String getPid() {
        return pid;
    }

    public TIMESTAMP getTime() {
        return time;
    }

    public User getUser() {
        return user;
    }

    public MiniProgram getMiniProgram() {
        return miniProgram;
    }
}
