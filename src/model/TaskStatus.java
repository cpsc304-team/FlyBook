package model;

import oracle.sql.DATE;

public class TaskStatus {
    private DATE stime;
    private int passed;

    public TaskStatus(DATE stime, int passed) {
        this.stime = stime;
        this.passed = passed;
    }

    public DATE getStime() {
        return stime;
    }

    public int getPassed() {
        return passed;
    }
}
