package database;

import model.*;

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
        SharePost sp2 = new SharePost("p0002",Timestamp.valueOf("2020-01-15 12:30:00"),"Hello everyone","0002");
        SharePost sp3 = new SharePost("p0003",Timestamp.valueOf("2020-01-16 12:00:00"),"Hello! THis is gelila","0003");
        SharePost sp4 = new SharePost("p0004",Timestamp.valueOf("2020-01-17 12:00:00"),"Hello! Karry from the north","0004");
        SharePost sp5 = new SharePost("p0005",Timestamp.valueOf("2020-01-18 12:00:00"),"Long time no see, Vancouver","0005");

        // media
        Media media1 = new Media("m0001", "Image");
        Media media2 = new Media("m0002", "Music");
        Media media3 = new Media("m0003", "Video");
        Media media4 = new Media("m0004", "Image");
        Media media5 = new Media("m0005", "Video");

        // post_contains
         PostContains postContains1 = new PostContains("p0001", "m0001");
         PostContains postContains2 = new PostContains("p0002", "m0002");
         PostContains postContains3 = new PostContains("p0003", "m0003");
         PostContains postContains4 = new PostContains("p0004", "m0004");
         PostContains postContains5 = new PostContains("p0005", "m0005");

        // mini_program
        MiniProgram miniProgram1 = new MiniProgram("mi0001", "Payroll Check", "Work");
        MiniProgram miniProgram2 = new MiniProgram("mi0002", "Monthly Report", "Work");
        MiniProgram miniProgram3 = new MiniProgram("mi0003", "Announcement", "Work");
        MiniProgram miniProgram4 = new MiniProgram("mi0004", "Payroll Check", "Work");
        MiniProgram miniProgram5 = new MiniProgram("mi0005", "Payroll Check", "Work");

        // miniprogram_record
        MiniProgramRecord miniProgramRecord1 = new MiniProgramRecord("0001","mi0001",Timestamp.valueOf("2020-01-15 12:00:00"));
        MiniProgramRecord miniProgramRecord2 = new MiniProgramRecord("0002","mi002",Timestamp.valueOf("2020-01-15 12:30:00"));
        MiniProgramRecord miniProgramRecord3 = new MiniProgramRecord("0003","mi003",Timestamp.valueOf("2020-01-16 12:00:00"));
        MiniProgramRecord miniProgramRecord4 = new MiniProgramRecord("0004","mi004",Timestamp.valueOf("2020-01-17 12:00:00"));
        MiniProgramRecord miniProgramRecord5 = new MiniProgramRecord("0005","mi005",Timestamp.valueOf("2020-01-18 12:00:00"));

        // group_member
        GroupMemberAdministrator groupMemberAdministrator1 = new GroupMemberAdministrator("0001","FrozenCloud");
        GroupMemberAdministrator groupMemberAdministrator2 = new GroupMemberAdministrator("0002","Doooora");
        GroupMemberAdministrator groupMemberAdministrator3 = new GroupMemberAdministrator("0003","QQQ");
        GroupMemberAdministrator groupMemberAdministrator4 = new GroupMemberAdministrator("0004","7k+");
        GroupMemberAdministrator groupMemberAdministrator5 = new GroupMemberAdministrator("0005","magge");

        // group_joins
        GroupJoins groupJoins1 = new GroupJoins(Timestamp.valueOf("2020-02-16 12:00:00"),"p0001","g0002");
        GroupJoins groupJoins2 = new GroupJoins(Timestamp.valueOf("2020-02-17 12:00:00"),"p0002","g0001");
        GroupJoins groupJoins3 = new GroupJoins(Timestamp.valueOf("2020-02-18 12:00:00"),"p0003","g0002");
        GroupJoins groupJoins4 = new GroupJoins(Timestamp.valueOf("2020-02-19 12:00:00"),"p0004","g0003");
        GroupJoins groupJoins5 = new GroupJoins(Timestamp.valueOf("2020-02-20 12:00:00"),"p0005","g0001");

        // group_creates
        GroupCreate groupCreate1 = new GroupCreate(Timestamp.valueOf("2020-02-15 12:00:00"),"p0001","g0001");
        GroupCreate groupCreate2 = new GroupCreate(Timestamp.valueOf("2020-02-17 12:00:00"),"p0002","g0002");
        GroupCreate groupCreate3 = new GroupCreate(Timestamp.valueOf("2020-02-18 12:00:00"),"p0003","g0003");
        GroupCreate groupCreate4 = new GroupCreate(Timestamp.valueOf("2020-02-19 12:00:00"),"p0004","g0004");
        GroupCreate groupCreate5 = new GroupCreate(Timestamp.valueOf("2020-02-20 12:00:00"),"p0005","g0005");

        // group_record
        GroupRecord groupRecord1 = new GroupRecord("g0001","project_team1");
        GroupRecord groupRecord2 = new GroupRecord("g0002","project_team2");
        GroupRecord groupRecord3 = new GroupRecord("g0003","project_team3");
        GroupRecord groupRecord4 = new GroupRecord("g0004","project_team4");
        GroupRecord groupRecord5 = new GroupRecord("g0005","project_team5");

        // groupchat_record
        GroupChatRecord groupChatRecord1 = new GroupChatRecord(Timestamp.valueOf("2020-02-17 12:00:00"), "0001","Nice to see you guys! This is karry","g0001");
        GroupChatRecord groupChatRecord2 = new GroupChatRecord(Timestamp.valueOf("2020-02-18 12:00:00"), "0002","Nice to see you guys! This is dora.","g0001");
        GroupChatRecord groupChatRecord3 = new GroupChatRecord(Timestamp.valueOf("2020-02-19 12:00:00"), "0003","Nice to see you guys! This is gelila.","g0001");
        GroupChatRecord groupChatRecord4 = new GroupChatRecord(Timestamp.valueOf("2020-02-20 12:00:00"), "0004","Nice to see you guys! This is tony.","g0001");
        GroupChatRecord groupChatRecord5 = new GroupChatRecord(Timestamp.valueOf("2020-02-21 12:00:00"), "0005","Nice to see you guys! This is Mike.","g0001");

        // meeting_record
        MeetingRecord meetingRecord1 = new MeetingRecord("m0001",10,"Welcome ceremony",Timestamp.valueOf("2020-08-16 12:00:00"),Timestamp.valueOf("2020-08-16 14:00:00"),"g0001");
        MeetingRecord meetingRecord2 = new MeetingRecord("m0001",5,"team discussion",Timestamp.valueOf("2020-08-19 12:00:00"),Timestamp.valueOf("2020-08-19 13:00:00"),"g0001");
        MeetingRecord meetingRecord3 = new MeetingRecord("m0001",6,"project discussion",Timestamp.valueOf("2020-09-10 12:00:00"),Timestamp.valueOf("2020-08-16 16:00:00"),"g0001");
        MeetingRecord meetingRecord4 = new MeetingRecord("m0001",7,"Welcome ceremony",Timestamp.valueOf("2020-08-16 12:00:00"),Timestamp.valueOf("2020-08-16 14:00:00"),"g0002");
        MeetingRecord meetingRecord5 = new MeetingRecord("m0001",4,"random chatting",Timestamp.valueOf("2020-08-20 17:00:00"),Timestamp.valueOf("2020-08-16 18:00:00"),"g0002");

        // schedule_record
        ScheduleRecord scheduleRecord1 = new ScheduleRecord("s0001",Timestamp.valueOf("2020-08-16 12:00:00"),"event1","0001");
        ScheduleRecord scheduleRecord2 = new ScheduleRecord("s0002",Timestamp.valueOf("2020-08-17 14:00:00"),"event2","0001");
        ScheduleRecord scheduleRecord3 = new ScheduleRecord("s0003",Timestamp.valueOf("2020-08-18 16:00:00"),"event3","0002");
        ScheduleRecord scheduleRecord4 = new ScheduleRecord("s0004",Timestamp.valueOf("2020-08-19 16:00:00"),"event4","0002");
        ScheduleRecord scheduleRecord5 = new ScheduleRecord("s0005",Timestamp.valueOf("2020-08-20 17:00:00"),"event5","0003");

        // task_status
        TaskStatus taskStatus1 = new TaskStatus(Timestamp.valueOf("2020-08-16 12:00:00"),0);
        TaskStatus taskStatus2 = new TaskStatus(Timestamp.valueOf("2020-08-17 14:00:00"),1);
        TaskStatus taskStatus3 = new TaskStatus(Timestamp.valueOf("2020-08-18 16:00:00"),1);
        TaskStatus taskStatus4 = new TaskStatus(Timestamp.valueOf("2020-08-19 16:00:00"),1);
        TaskStatus taskStatus5 = new TaskStatus(Timestamp.valueOf("2020-08-20 17:00:00"),0);

        // contain_task
        ContainTask containTask1 = new ContainTask("Finish Milestone1",1,"s0001");
        ContainTask containTask2 = new ContainTask("Finish Milestone2",3,"s0002");
        ContainTask containTask3 = new ContainTask("Finish Milestone3",2,"s0003");
        ContainTask containTask4 = new ContainTask("Finish Milestone4",4,"s0004");
        ContainTask containTask5 = new ContainTask("Finish Milestone5",2,"s0005");
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

    // Set up the mini program table
    private void miniProgramSetUp() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE mini_program (" +
                    "miniid varchar2(10), " +
                    "mediaid varchar2(20)," +
                    "type varchar2(20) " +
                    "PRIMARY KEY (miniid))");

            stmt.close();
        } catch (SQLException e) {
            // TODO: delete
            System.out.println("Debug: miniProgramSetUp()");

            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }


    // Set up the mini program record table
    private void miniProgramRecordSetUp() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE miniprogram_record (" +
                    "u_id varchar2(10), " +
                    "miniid varchar2(20)," +
                    "time TIMESTAMP " +
                    "PRIMARY KEY (u_id , miniid,time)," +
                    "FOREIGN KEY (u_id) REFERENCES user_info," +
                    "FOREIGN KEY (miniid) REFERENCES mini_program)");

            stmt.close();
        } catch (SQLException e) {
            // TODO: delete
            System.out.println("Debug: miniProgramRecordSetUp()");

            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    // Set up the group member table
    private void groupMemberSetUp() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE group_member" +
                    "(nickname varchar2(10)," +
                    "u_id varchar2(10)," +
                    "PRIMARY KEY (u_id)," +
                    "FOREIGN KEY (u_id) REFERENCES User_info)");

            stmt.close();
        } catch (SQLException e) {
            // TODO: delete
            System.out.println("Debug: groupMemberSetUp()");

            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    // Set up the group admin table
    private void groupadministratorSetUp() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE group_administrator" +
                    "(nickname varchar2(10)," +
                    "u_id varchar2(10)," +
                    "PRIMARY KEY (u_id)," +
                    "FOREIGN KEY (u_id) REFERENCES User_info)");

            stmt.close();
        } catch (SQLException e) {
            // TODO: delete
            System.out.println("Debug: groupadministratorSetUp()");

            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    // Set up the group record table
    private void groupRecordSetUp() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE group_record" +
                    "(gid varchar2(10)," +
                    "gname varchar2(10)," +
                    "PRIMARY KEY (gid)");

            stmt.close();
        } catch (SQLException e) {
            // TODO: delete
            System.out.println("Debug: groupRecordSetUp()");

            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    // Set up the group joins table
    private void groupJoinsSetUp() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE group_joins" +
                    "(join_time TIMESTAMP," +
                    "u_id varchar2(10)," +
                    "gid varchar2(10)," +
                    "PRIMARY KEY (u_id,gid)," +
                    "FOREIGN KEY (u_id) REFERENCES user_info," +
                    "FOREIGN KEY (gid) REFERENCES group_record)");

            stmt.close();
        } catch (SQLException e) {
            // TODO: delete
            System.out.println("Debug: groupJoinsSetUp()");

            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    // Set up the group creates table
    private void groupCreatesSetUp() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE group_joins" +
                    "(create_time TIMESTAMP," +
                    "u_id varchar2(10)," +
                    "gid varchar2(10)," +
                    "PRIMARY KEY (u_id,gid)," +
                    "FOREIGN KEY (u_id) REFERENCES user_info," +
                    "FOREIGN KEY (gid) REFERENCES group_record)");

            stmt.close();
        } catch (SQLException e) {
            // TODO: delete
            System.out.println("Debug: groupCreatesSetUp()");

            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    // Set up the group chat record table
    private void groupChatRecordSetUp() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE groupchat_record" +
                    "(time TIMESTAMP," +
                    "content varchar2(100)," +
                    "gid varchar2(10)," +
                    "PRIMARY KEY (u_id,gid, time)," +
                    "FOREIGN KEY (u_id) REFERENCES user_info," +
                    "FOREIGN KEY (gid) REFERENCES group_record ON DELETE CASCADE)");

            stmt.close();
        } catch (SQLException e) {
            // TODO: delete
            System.out.println("Debug: groupChatRecordSetUp()");

            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    // Set up the meeting record table
    private void meetingRecordSetUp() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE groupchat_record" +
                    "(mid varchar2(10)," +
                    "attendance INTEGER," +
                    "topic varchar2(50)," +
                    "start_time TIMESTAMP," +
                    "end_time TIMESTAMP," +
                    "gid varchar2(10) NOT NULL," +
                    "PRIMAR KEY (mid)" +
                    "FOREIGN KEY (gid) REFERENCES group_record ON DELETE CASCADE)");

            stmt.close();
        } catch (SQLException e) {
            // TODO: delete
            System.out.println("Debug: meetingRecordSetUp()");

            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    // Set up the task status table
    private void taskStatusSetUp() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE task_status" +
                    "(stime varchar2(10)," +
                    "passed INTEGER," +
                    "PRIMARY KEY (stime))");

            stmt.close();
        } catch (SQLException e) {
            // TODO: delete
            System.out.println("Debug: taskStatusSetUp()");

            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    // Set up the schedule record table
    private void scheduleRecordSetUp() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE schedule_record" +
                    "(sid varchar2(10)," +
                    "stime TIMESTAMP," +
                    "event varchar2(50)," +
                    "u_id varchar2(10) NOT NULL," +
                    "PRIMARY KEY (sid)," +
                    "FOREIGN KEY (u_id) REFERENCES user_info," +
                    "FOREIGN KEY (gid) REFERENCES task_status)");

            stmt.close();
        } catch (SQLException e) {
            // TODO: delete
            System.out.println("Debug: scheduleRecordSetUp()");

            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    // Set up contain_task table
    private void containTaskSetUp() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE contain_task" +
                    "(tname varchar2(10)," +
                    "priority INTEGER," +
                    "sid varchar2(20)," +
                    "PRIMARY KEY (sid,tname)," +
                    "FOREIGN KEY (sid) REFERENCES schedule_record ON DELETE CASCADE)");

            stmt.close();
        } catch (SQLException e) {
            // TODO: delete
            System.out.println("Debug: containTaskSetUp()");

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