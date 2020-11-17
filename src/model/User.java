package model;


/*CREATE TABLE User_info
        (u_id CHAR(10),
        password CHAR(20),
        u_name CHAR(20),
        city CHAR(20),
        email CHAR(20) UNIQUE,
        PRIMARY KEY (u_id),
        FOREIGN KEY (city) REFERENCES Time_Zone);*/

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