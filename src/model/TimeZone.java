package model;

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
