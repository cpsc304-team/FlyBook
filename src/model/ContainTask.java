package model;

import oracle.sql.DATE;
/*CREATE TABLE Contain_Task
    (tname CHAR(10),
    Priority_val INTEGER,
    sid CHAR(10),
    PRIMARY KEY (sid, tname),
    FOREIGN KEY (sid) REFERENCES Schedule_Record ON DELETE CASCADE);*/

public class ContainTask {
    private String tname;
    private int priorityVal;
    private String sid;
    private ScheduleRecord scheduleRecord;

    public ContainTask(String tname, int priorityVal, String sid, ScheduleRecord scheduleRecord) {
        this.tname = tname;
        this.priorityVal = priorityVal;
        this.sid = sid;
        this.scheduleRecord = scheduleRecord;
    }

    public String getTname() {
        return tname;
    }

    public int getPriorityVal() {
        return priorityVal;
    }

    public String getSid() {
        return sid;
    }

    public ScheduleRecord getScheduleRecord() {
        return scheduleRecord;
    }
}

