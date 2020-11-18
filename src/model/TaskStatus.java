package model;

import oracle.sql.TIMESTAMP;

import java.sql.Timestamp;

public class TaskStatus {
    private Timestamp stime;
    private int passed;

    public TaskStatus(Timestamp stime, int passed) {
        this.stime = stime;
        this.passed = passed;
    }

    public Timestamp getStime() {
        return stime;
    }

    public int getPassed() {
        return passed;
    }
}
