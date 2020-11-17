package model;

import oracle.sql.DATE;

public class MiniProgramRecord {
    private String uid;
    private String pid;
    private DATE time;
    private User user;
    private MiniProgram miniProgram;

    public MiniProgramRecord(String uid, String pid, DATE time, User user, MiniProgram miniProgram) {
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

    public DATE getTime() {
        return time;
    }

    public User getUser() {
        return user;
    }

    public MiniProgram getMiniProgram() {
        return miniProgram;
    }
}
