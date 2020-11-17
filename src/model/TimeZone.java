package model;

public class TimeZone {
    private String city;
    private String timeZone;

    public TimeZone(String city, String timeZone) {
        this.city = city;
        this.timeZone = timeZone;
    }

    public String getCity() {
        return city;
    }

    public String getTimeZone() {
        return timeZone;
    }
}
