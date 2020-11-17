package model;

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
