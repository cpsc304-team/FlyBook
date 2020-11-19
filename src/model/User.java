package model;

/*CREATE TABLE user_info (
        user_id varchar2(10),
        password varchar2(10) NOT NULL,
        name varchar2(20) NOT NULL,
        city varchar2(20),
        email varchar2(100),
        PRIMARY KEY (user_id));*/

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