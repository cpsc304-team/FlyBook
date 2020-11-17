package model;

public class GroupRecord {
    private String gid;
    private String gname;

    public GroupRecord(String gid, String gname) {
        this.gid = gid;
        this.gname = gname;
    }

    public String getGid() {
        return gid;
    }

    public String getGname() {
        return gname;
    }
}
