package model;

public class User {
    private String userid;
    private String password;
    private String name;
    private String city;
    private String time_zone;
    private TimeZone timezone;
    private String email;

    public User(String userid, String password, String name, String city, String time_zone, String email) {
        this.userid = userid;
        this.password = password;
        this.name = name;
        this.city = city;
        this.time_zone = time_zone;
        this.email = email;
    }

    public String getUserid() {
        return userid;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getTime_zone() { return time_zone; }

    public String getEmail() { return email; }
}