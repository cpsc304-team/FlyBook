package model;


/*CREATE TABLE Media
        (mediaid CHAR(10),
        mtype CHAR(10),
        PRIMARY KEY (mediaid));*/

public class Media {
    private String mediaid;
    private String mediaType;
    private String url;

    public Media(String mediaid, String mediaType, String url) {
        this.mediaid = mediaid;
        this.mediaType = mediaType;
        this.url = url;
    }

    public String getMediaid() {
        return mediaid;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getUrl() { return url; }
}

