package model;

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
