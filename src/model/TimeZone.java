package model;

/*CREATE TABLE Time_Zone
    (city CHAR(20),
    time_zone CHAR(5),
    PRIMARY KEY (city));*/

public class TimeZone {
    private String city;
    private String zonecode;

    public TimeZone(String city, String zonecode) {
        this.city = city;
        this.zonecode = zonecode;
    }

    public String getCity() {
        return city;
    }

    public String getZoneCode() {
        return zonecode;
    }
}
