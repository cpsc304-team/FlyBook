package model;

/*CREATE TABLE time_zone (
        city varchar2(20),
        time_zone varchar2(5) NOT NULL,
        PRIMARY KEY (city));*/

public class TimeZone {
    private String city;
    private String zoneCode;

    public TimeZone(String city, String zoneCode) {
        this.city = city;
        this.zoneCode = zoneCode;
    }

    public String getCity() {
        return city;
    }

    public String getZoneCode() {
        return zoneCode;
    }
}
