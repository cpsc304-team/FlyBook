package model;

import oracle.sql.TIMESTAMP;

public class TaskStatus {
    private TIMESTAMP stime;
    private int passed;

    public TaskStatus(TIMESTAMP stime, int passed) {
        this.stime = stime;
        this.passed = passed;
    }

    public TIMESTAMP getStime() {
        return stime;
    }

    public int getPassed() {
        return passed;
    }
}
