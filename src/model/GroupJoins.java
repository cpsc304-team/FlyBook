package model;

import java.sql.Timestamp;

/*CREATE TABLE Group_Joins
    (join_time TIMESTAMP,
    u_id CHAR(10),
    gid CHAR(10),
    PRIMARY KEY (u_id,gid),
    FOREIGN KEY (u_id) REFERENCES User_info,
    FOREIGN KEY (gid) REFERENCES Group_Record);*/

public class GroupJoins {
    private Timestamp jointime;
    private String uid;
    private String gid;
//    private User user;
//    private GroupRecord groupRecord;

    public GroupJoins(Timestamp jointime, String uid, String gid) {
        this.jointime = jointime;
        this.uid = uid;
        this.gid = gid;
//        this.user = user;
//        this.groupRecord = groupRecord;
    }

    public Timestamp getJoinTime() {
        return jointime;
    }

    public String getUid() {
        return uid;
    }

    public String getGid() {
        return gid;
    }
}
