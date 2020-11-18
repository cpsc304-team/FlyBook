package database;

import model.IndividualChat;
import model.SharePost;
import model.User;
import model.TimeZone;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import oracle.sql.TIMESTAMP;

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

    // Drop all the tables
    public void dropTables() {
        dropTable("post_contains");
        dropTable("media");
        dropTable("share_post");
        dropTable("individual_chat");
        dropTable("time_zone");
        dropTable("user_info");
    }

    // Set up the database
    public void databaseSetUp() {
        userInfoSetUp();
        timeZoneSetUp();
        individualChatSetUp();
        sharePostSetUp();
        mediaSetUp();
        postContainsSetUp();
    }

    // Load pre-set data
    public void loadData() {
        // time_zone
        TimeZone tz1 = new TimeZone("Vancouver", "GMT-8");
        insertTimeZone(tz1);
        TimeZone tz2 = new TimeZone("Toronto", "GMT-5");
        insertTimeZone(tz2);
        TimeZone tz3 = new TimeZone("Edmonton", "GMT-7");
        insertTimeZone(tz3);
        TimeZone tz4 = new TimeZone("Winnipeg", "GMT-4");
        insertTimeZone(tz4);
        TimeZone tz5 = new TimeZone("Montreal", "GMT-5");
        insertTimeZone(tz5);
        TimeZone tz6 = new TimeZone("Ottawa", "GMT-5");
        insertTimeZone(tz6);


        // user_info
        User admin = new User("0000", "0000", "admin", tz2, "admin@gmail.com");
        insertUser(admin);
        User u1 = new User("0001", "1", "Gelila Zhang", tz1, "gelila@gmail.com");
        insertUser(u1);
        User u2 = new User("0002", "2", "Karry Yang", tz1, "kerry@gmail.com");
        insertUser(u2);
        User u3 = new User("0003", "3", "Dora Ni", tz1, "dora@gmail.com");
        insertUser(u3);

        // individual_chat
        IndividualChat chatG1 = new IndividualChat("0001", "0002",  "Hello!", Timestamp.valueOf("2020-11-17 11:23:08"));
        IndividualChat chatG2 = new IndividualChat("0001", "0003",  "How are you today?", Timestamp.valueOf("2020-11-18 11:23:08"));
        IndividualChat chatK1 = new IndividualChat("0002", "0001",  "Hello! I'm karry", Timestamp.valueOf("2020-11-17 11:23:09"));
        IndividualChat chatK2 = new IndividualChat("0002", "0003",  "Nice to see you", Timestamp.valueOf("2020-11-18 11:23:08"));
        IndividualChat chatD1 = new IndividualChat("0003", "0001",  "Hello! I'm dora", Timestamp.valueOf("2020-11-17 11:23:10"));
        IndividualChat chatD2 = new IndividualChat("0003", "0002",  "any recommand sushi?", Timestamp.valueOf("2020-11-18 11:23:08"));

        insertIndividualChat(chatG1);
        insertIndividualChat(chatG2);
        insertIndividualChat(chatK1);
        insertIndividualChat(chatK2);
        insertIndividualChat(chatD1);
        insertIndividualChat(chatD2);

        // share_post
        SharePost sp1 = new SharePost("p0001",Timestamp.valueOf("2020-01-15 12:00:00"),"My first post!!","0001");
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

    /*
     * ==================================================================
     *
     * Below are all the methods for create tables
     *
     * ==================================================================
     * */


    /* Below are the methods used to create the database */
    // Set up the time_zone table
    private void timeZoneSetUp() {
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



    // Set up the user_info table
    private void userInfoSetUp() {
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
            System.out.println("Debug: userInfoSetUp()");

            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }



    // Set up the individual_chat table
    private void individualChatSetUp() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE individual_chat (" +
                    // TODO: change to TIMESTAMP if it could be generated in SQL given a string
                    "time TIMESTAMP, " +
                    "u_id1 varchar2(10), " +
                    "u_id2 varchar2(10), " +
                    "content varchar2(100), " +
                    "PRIMARY KEY (u_id1, u_id2, time), " +
                    "FOREIGN KEY (u_id1) REFERENCES user_info ON DELETE CASCADE, " +
                    "FOREIGN KEY (u_id2) REFERENCES user_info ON DELETE CASCADE)");

            stmt.close();
        } catch (SQLException e) {
            // TODO: delete
            System.out.println("Debug: individualChatSetUp()");

            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    // Set up the individual_chat table
    private void sharePostSetUp() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE share_post (" +
                    // TODO: change to TIMESTAMP if it could be generated in SQL given a string
                    "postid varchar2(10), " +
                    "post_time TIMESTAMP, " +
                    "u_id varchar2(10) NOT NULL, " +
                    "content varchar2(100), " +
                    "PRIMARY KEY (postid), " +
                    "FOREIGN KEY (u_id) REFERENCES user_info)");

            stmt.close();
        } catch (SQLException e) {
            // TODO: delete
            System.out.println("Debug: SharePostSetUp()");

            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    // Set up the Media table
    private void mediaSetUp() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE media (" +
                    // TODO: change to TIMESTAMP if it could be generated in SQL given a string
                    "mediaid varchar2(10), " +
                    "mtype varchar2(20), " +
                    "PRIMARY KEY (mediaid))");

            stmt.close();
        } catch (SQLException e) {
            // TODO: delete
            System.out.println("Debug: MediaSetUp()");

            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    // Set up the PostContains table
    private void postContainsSetUp() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE post_contains (" +
                    // TODO: change to TIMESTAMP if it could be generated in SQL given a string
                    "postid varchar2(10), " +
                    "mediaid varchar2(20), " +
                    "PRIMARY KEY (postid,mediaid))," +
                    "FOREIGN KEY (postid) REFERENCES Share_Post," +
                    "FOREIGN KEY (mediaid) REFERENCES Media)");

            stmt.close();
        } catch (SQLException e) {
            // TODO: delete
            System.out.println("Debug: PostContainsSetUp()");

            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }




    /*
     * ==================================================================
     *
     * Below are all the methods for insert statements
     *
     * ==================================================================
     * */

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


    // Insert IndividualChar
    public void insertIndividualChat(IndividualChat chat) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO individual_chat VALUES (?,?,?,?)");

            // TODO: Trying to generate TIMESTAMP with no success :(
//            Timestamp time = chat.getTime();
//            DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
//            String text = dateFormat.format(time);
//            System.out.println(text);
//            System.out.println("TO_TIMESTAMP(\'" + text + "\', \'YYYY-MM-DD HH24:MI:SS\')");
//            ps.setString(1, "TO_TIMESTAMP(\'" + text + "\', \'yyyy-MM-DD HH24:MI:SS\')");

            ps.setString(1, chat.getTime().toString());
            ps.setString(2, chat.getUid1());
            ps.setString(3, chat.getUid2());
            ps.setString(4, chat.getContent());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }


    /*
     * ==================================================================
     *
     * Below are the methods used to generate queries or projections that
     * the application needs
     *
     * ==================================================================
     * */

    //Return all cities listed in time_zone
    public String[] getTimeZoneCities() {
        ArrayList<String> result = new ArrayList<>();

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
                    "WHERE user_id = \'" + userid + "\' AND user_info.city = time_zone.city");

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

    // return timezone given city name
    public TimeZone getTimeZoneByCity(String city) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM time_zone WHERE city = \'" + city + "\'");

            while(rs.next()) {
                TimeZone timezone = new TimeZone(rs.getString("city"), rs.getString("time_zone"));
                return timezone;
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return null;
    }

    public void resetPassword(String userid, String password) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE user_info SET password = ? WHERE user_id = ?");
            ps.setString(1, password);
            ps.setString(2, userid);

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void deleteAccount(String userid) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM user_info WHERE user_id = ?");
            ps.setString(1, userid);

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }
}