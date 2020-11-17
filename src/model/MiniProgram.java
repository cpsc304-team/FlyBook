package model;


/*CREATE TABLE Mini_Program
    (pid CHAR(10),
    pname CHAR(20),
    type CHAR(10),
    PRIMARY KEY (pid));*/

public class MiniProgram {
    private String pid;
    private String pname;
    private String type;

    public MiniProgram(String pid, String pname, String type) {
        this.pid = pid;
        this.pname = pname;
        this.type = type;
    }

    public String getPid() {
        return pid;
    }

    public String getPname() {
        return pname;
    }

    public String getType() {
        return type;
    }
}
