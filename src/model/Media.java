package model;


/*CREATE TABLE Media
        (mediaid CHAR(10),
        mtype CHAR(10),
        PRIMARY KEY (mediaid));*/

public class Media {
    private String mediaid;
    private String mtype;

    public Media(String mediaid, String mtype) {
        this.mediaid = mediaid;
        this.mtype = mtype;
    }

    public String getMediaid() {
        return mediaid;
    }

    public String getMtype() {
        return mtype;
    }
}

