package model;

public class User {
    private String userid;
    private String password;
    private String name;
    private TimeZone timezone;
    private String email;

    public User(String userid, String password, String name, TimeZone time_zone,String email) {
        this.userid = userid;
        this.password = password;
        this.name = name;
        this.timezone = time_zone;
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

    public TimeZone getTimezone() {
        return timezone;
    }

    public String getEmail() { return email; }
}