package model;

/*CREATE TABLE Time_Zone
    (city CHAR(20),
    time_zone CHAR(5),
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
