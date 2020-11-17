package database;

import model.User;
import model.TimeZone;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnection {
    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    private Connection connection = null;

    public DatabaseConnection() {
        try {
            // Load the Oracle JDBC driver
            // Note that the path could change for new drivers
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    // Login into the Oracle server
    public boolean login() {
        try {
            if (connection != null) {
                connection.close();
            }

            connection = DriverManager.getConnection(ORACLE_URL, "ora_gelilacn", "a56781693");
            connection.setAutoCommit(false);

            System.out.println("\nConnected to Oracle!");
            return true;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return false;
        }
    }

    private void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    // Set up the database
    public void databaseSetUp() {
        userInfoSetUp();
        timeZoneSetUp();
    }

    // Load pre-set data
    public void loadData() {
        // time_zone
        TimeZone tz1 = new TimeZone("Vancouver", "GMT-8");
        insertTimeZone(tz1);
        TimeZone tz2 = new TimeZone("Toronto", "GMT-5");
        insertTimeZone(tz2);

        // user_info

        User admin = new User("0000", "0000", "admin", tz1, "admin@gmail.com");
        insertUser(admin);

        //
    }

    // Drop the table if it exists given a table name
    private void dropTable(String tableName) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select table_name from user_tables");

            while(rs.next()) {
                if(rs.getString(1).toLowerCase().equals(tableName)) {
                    stmt.execute("DROP TABLE " + tableName);
                    break;
                }
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }


    /* Below are the methods used to create the database */
    // Set up the time_zone table
    private void timeZoneSetUp() {
        dropTable("time_zone");

        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE time_zone (" +
                    "city varchar2(20) not null PRIMARY KEY, " +
                    "time_zone varchar2(5) not null)");

            stmt.close();
        } catch (SQLException e) {
            // TODO: delete
            System.out.println("Debug: timeZoneSetUp()");

            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    // Insert time_zone
    public void insertTimeZone(TimeZone tz) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO time_zone VALUES (?,?)");
            ps.setString(1, tz.getCity());
            ps.setString(2, tz.getZoneCode());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    // Set up the user_info table
    private void userInfoSetUp() {
        dropTable("user_info");

        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE user_info (" +
                    "user_id varchar2(10) not null PRIMARY KEY, " +
                    "password varchar2(10) not null, " +
                    "name varchar2(20) not null, " +
                    "city varchar2(20), " +
                    "email varchar2(100))");

            stmt.close();
        } catch (SQLException e) {
            // TODO: delete
            System.out.println("Debug: databaseSetUp()");

            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    // Insert user_info
    public void insertUser(User user) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO user_info VALUES (?,?,?,?,?)");
            ps.setString(1, user.getUserid());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            if (user.getTimezone().getCity() == null) {
                ps.setNull(4, java.sql.Types.CHAR);
            } else {
                ps.setString(4, user.getTimezone().getCity());
            }
            if (user.getEmail() == null) {
                ps.setNull(5, java.sql.Types.CHAR);
            } else {
                ps.setString(5, user.getEmail());
            }

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }


    /* Below are the methods used to generate queries or projections that
    * the application needs */

    //Return all cities listed in time_zone
    public String[] getTimeZoneCities() {
        ArrayList<String> result = new ArrayList<String>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT city FROM time_zone");

            while(rs.next()) {
                result.add(rs.getString("city"));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new String[result.size()]);
    }

    // Get a user's info with time_zone
    public User[] getUser() {
        ArrayList<User> result = new ArrayList<User>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM user_info, " +
                    "time_zone WHERE user_info.city = time_zone.city");

            while(rs.next()) {
                TimeZone timezone = new TimeZone(rs.getString("city"),rs.getString("time_zone"));
                User user = new User(rs.getString("user_id"),
                        rs.getString("password"),
                        rs.getString("name"),
                        timezone,
                        rs.getString("email"));
                result.add(user);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new User[result.size()]);
    }

    // Check if the userid and password match what have been stored when login
    public boolean containLoginInfo(String userid, String password) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select user_id, password from user_info");

            while(rs.next()) {
                if (userid.equals(rs.getString("user_id"))) {
                    if (password.equals(rs.getString("password"))) {
                        return true;
                    }
                }
            }

            rs.close();
            stmt.close();

            return false;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return false;
        }
    }

    // Check if a user exists
    public boolean userExist(String userid) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select user_id from user_info");

            while(rs.next()) {
                if (userid.equals(rs.getString("user_id"))) {
                    return true;
                }
            }

            rs.close();
            stmt.close();

            return false;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return false;
        }
    }

    // Get a user's info by user ID
    public User getUserByID(String userid) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM user_info, time_zone " +
                    "WHERE user_id = " + userid + " AND user_info.city = time_zone.city");

            while(rs.next()) {
                TimeZone timezone = new TimeZone(rs.getString("city"),rs.getString("time_zone"));
                User user = new User(rs.getString("user_id"),
                        rs.getString("password"),
                        rs.getString("name"),
                        timezone,
                        rs.getString("email"));
                return user;
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return null;
    }
}
