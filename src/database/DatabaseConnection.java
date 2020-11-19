package database;

import model.*;

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

    // Drop all the tables
    public void dropTables() {
        dropTable("contain_task");
        dropTable("post_contains");
        dropTable("share_post");
        dropTable("miniprogram_record");
        dropTable("individual_chat");
        dropTable("group_member");
        dropTable("group_administrator");
        dropTable("group_joins");
        dropTable("group_creates");
        dropTable("groupchat_record");
        dropTable("meeting_record");
        dropTable("schedule_record");

        dropTable("media");
        dropTable("time_zone");
        dropTable("user_info");
        dropTable("mini_program");
        dropTable("group_record");
        dropTable("task_status");
    }

    // Set up the database
    public void databaseSetUp() {
        userInfoSetUp();
        timeZoneSetUp();
        individualChatSetUp();
        mediaSetUp();
        sharePostSetUp();
//        postContainsSetUp();
//        miniProgramSetUp();
//        miniProgramRecordSetUp();
//        groupMemberSetUp();
//        groupadministratorSetUp();
//        groupJoinsSetUp();
//        groupCreatesSetUp();
//        groupChatRecordSetUp();
//        meetingRecordSetUp();
//        taskStatusSetUp();
//        scheduleRecordSetUp();
//        containTaskSetUp();
    }

    // Load pre-set data
    public void loadData() {
        // time_zone
        TimeZone tz1 = new TimeZone("Vancouver", "GMT-8");
        insertTimeZone(tz1);
        TimeZone tz2 = new TimeZone("Toronto", "GMT-5");
        insertTimeZone(tz2);
        TimeZone tz3 = new TimeZone("Beijing", "GMT+8");
        insertTimeZone(tz3);
        TimeZone tz4 = new TimeZone("London", "GMT+1");
        insertTimeZone(tz4);
        TimeZone tz5 = new TimeZone("Seattle", "GMT-7");
        insertTimeZone(tz5);
        TimeZone tz6 = new TimeZone("Tokyo", "GMT+9");
        insertTimeZone(tz6);
        TimeZone tz7 = new TimeZone("New York", "GMT-5");
        insertTimeZone(tz7);


        // user_info
        User u1 = new User("0001", "1", "Gelila Zhang", tz4, "gelilaz@gmail.com");
        insertUser(u1);
        User u2 = new User("0002", "2", "Karry Yang", tz1, "kerryy@gmail.com");
        insertUser(u2);
        User u3 = new User("0003", "3", "Dora Ni", tz3, "doran@gmail.com");
        insertUser(u3);
        User u4 = new User("0004", "4", "John Doe", tz7, "johnd@gmail.com");
        insertUser(u4);
        User u5 = new User("0005", "5", "Jane Doe", tz2, "janed@gmail.com");
        insertUser(u5);

        // individual_chat
        IndividualChat chatG1 = new IndividualChat(u1, u2, "Hello!", Timestamp.valueOf("2020-11-17 11:23:08"));
        IndividualChat chatK1 = new IndividualChat(u2, u1, "Hello! I am karry", Timestamp.valueOf("2020-11-17 11:24:12"));
        IndividualChat chatD1 = new IndividualChat(u3, u1, "Hello! I am dora", Timestamp.valueOf("2020-11-17 11:23:10"));
        IndividualChat chatG2 = new IndividualChat(u1, u3, "Hi!", Timestamp.valueOf("2020-11-17 11:31:48"));
        IndividualChat chatK2 = new IndividualChat(u2, u3, "Nice to see you", Timestamp.valueOf("2020-11-17 21:23:08"));
        IndividualChat chatD2 = new IndividualChat(u3, u2, "Any recommended sushi?", Timestamp.valueOf("2020-11-18 11:23:08"));

        insertIndividualChat(chatG1);
        insertIndividualChat(chatG2);
        insertIndividualChat(chatK1);
        insertIndividualChat(chatK2);
        insertIndividualChat(chatD1);
        insertIndividualChat(chatD2);

        // media
        Media media1 = new Media("Image", "https://i.imgur.com/QwEq1g2.jpg");
        Media media2 = new Media("Music", "https://music.163.com/#/song?id=1405903472");
        Media media3 = new Media("Video", "https://www.youtube.com/watch?v=BoZ0Zwab6Oc");
        Media media4 = new Media("Image", "https://i.imgur.com/veHL0mf.jpg");
        Media media5 = new Media("Video", "https://www.youtube.com/watch?v=HXV3zeQKqGY");

        insertMedia(media1);
        insertMedia(media2);
        insertMedia(media3);
        insertMedia(media4);
        insertMedia(media5);

        // share_post
        SharePost sp1 = new SharePost("P1", Timestamp.valueOf("2020-11-17 12:00:00"),"My first post!!", u3, null);
        SharePost sp2 = new SharePost("P2", Timestamp.valueOf("2020-11-17 12:30:00"),"Hello everyone", u2, null);
        SharePost sp3 = new SharePost("P3", Timestamp.valueOf("2020-11-17 19:40:06"),"Hello! This is Gelila", u1, media2);
        SharePost sp4 = new SharePost("P4", Timestamp.valueOf("2020-11-18 11:09:25"),"Favourite sushi", u2, media1);
        SharePost sp5 = new SharePost("P5", Timestamp.valueOf("2020-11-18 18:05:20"),"Long time no see, Vancouver",u5, media4);

        insertSharePost(sp1);
        insertSharePost(sp2);
        insertSharePost(sp3);
        insertSharePost(sp4);
        insertSharePost(sp5);

//
//        // post_contains
//        PostContains postContains1 = new PostContains("p0001", "m0001");
//        PostContains postContains2 = new PostContains("p0002", "m0002");
//        PostContains postContains3 = new PostContains("p0003", "m0003");
//        PostContains postContains4 = new PostContains("p0004", "m0004");
//        PostContains postContains5 = new PostContains("p0005", "m0005");
//
//        insertPostContains(postContains1);
//        insertPostContains(postContains2);
//        insertPostContains(postContains3);
//        insertPostContains(postContains4);
//        insertPostContains(postContains5);
//
//        // mini_program
//        MiniProgram miniProgram1 = new MiniProgram("mi0001", "Payroll Check", "Work");
//        MiniProgram miniProgram2 = new MiniProgram("mi0002", "Monthly Report", "Work");
//        MiniProgram miniProgram3 = new MiniProgram("mi0003", "Announcement", "Work");
//        MiniProgram miniProgram4 = new MiniProgram("mi0004", "Payroll Check", "Work");
//        MiniProgram miniProgram5 = new MiniProgram("mi0005", "Payroll Check", "Work");
//
//        insertMiniProgram(miniProgram1);
//        insertMiniProgram(miniProgram2);
//        insertMiniProgram(miniProgram3);
//        insertMiniProgram(miniProgram4);
//        insertMiniProgram(miniProgram5);
//
//        // miniprogram_record
//        MiniProgramRecord miniProgramRecord1 = new MiniProgramRecord("0001","mi0001",Timestamp.valueOf("2020-01-15 12:00:00"));
//        MiniProgramRecord miniProgramRecord2 = new MiniProgramRecord("0002","mi002",Timestamp.valueOf("2020-01-15 12:30:00"));
//        MiniProgramRecord miniProgramRecord3 = new MiniProgramRecord("0003","mi003",Timestamp.valueOf("2020-01-16 12:00:00"));
//        MiniProgramRecord miniProgramRecord4 = new MiniProgramRecord("0004","mi004",Timestamp.valueOf("2020-01-17 12:00:00"));
//        MiniProgramRecord miniProgramRecord5 = new MiniProgramRecord("0005","mi005",Timestamp.valueOf("2020-01-18 12:00:00"));
//
//        insertMiniProgramRecord(miniProgramRecord1);
//        insertMiniProgramRecord(miniProgramRecord2);
//        insertMiniProgramRecord(miniProgramRecord3);
//        insertMiniProgramRecord(miniProgramRecord4);
//        insertMiniProgramRecord(miniProgramRecord5);
//
//
//        // group_member
//        GroupMemberAdministrator groupMemberAdministrator1 = new GroupMemberAdministrator("0001","FrozenCloud");
//        GroupMemberAdministrator groupMemberAdministrator2 = new GroupMemberAdministrator("0002","Doooora");
//        GroupMemberAdministrator groupMemberAdministrator3 = new GroupMemberAdministrator("0003","QQQ");
//        GroupMemberAdministrator groupMemberAdministrator4 = new GroupMemberAdministrator("0004","7k+");
//        GroupMemberAdministrator groupMemberAdministrator5 = new GroupMemberAdministrator("0005","magge");
//
//        insertGroupAdministrator(groupMemberAdministrator1);
//        insertGroupAdministrator(groupMemberAdministrator2);
//        insertGroupAdministrator(groupMemberAdministrator3);
//        insertGroupAdministrator(groupMemberAdministrator4);
//        insertGroupAdministrator(groupMemberAdministrator5);
//
//        insertGroupMember(groupMemberAdministrator1);
//        insertGroupMember(groupMemberAdministrator2);
//        insertGroupMember(groupMemberAdministrator3);
//        insertGroupMember(groupMemberAdministrator4);
//        insertGroupMember(groupMemberAdministrator5);
//
//
//        // group_joins
//        GroupJoins groupJoins1 = new GroupJoins(Timestamp.valueOf("2020-02-16 12:00:00"),"p0001","g0002");
//        GroupJoins groupJoins2 = new GroupJoins(Timestamp.valueOf("2020-02-17 12:00:00"),"p0002","g0001");
//        GroupJoins groupJoins3 = new GroupJoins(Timestamp.valueOf("2020-02-18 12:00:00"),"p0003","g0002");
//        GroupJoins groupJoins4 = new GroupJoins(Timestamp.valueOf("2020-02-19 12:00:00"),"p0004","g0003");
//        GroupJoins groupJoins5 = new GroupJoins(Timestamp.valueOf("2020-02-20 12:00:00"),"p0005","g0001");
//
//        insertGroupJoins(groupJoins1);
//        insertGroupJoins(groupJoins2);
//        insertGroupJoins(groupJoins3);
//        insertGroupJoins(groupJoins4);
//        insertGroupJoins(groupJoins5);
//
//        // group_creates
//        GroupCreate groupCreate1 = new GroupCreate(Timestamp.valueOf("2020-02-15 12:00:00"),"p0001","g0001");
//        GroupCreate groupCreate2 = new GroupCreate(Timestamp.valueOf("2020-02-17 12:00:00"),"p0002","g0002");
//        GroupCreate groupCreate3 = new GroupCreate(Timestamp.valueOf("2020-02-18 12:00:00"),"p0003","g0003");
//        GroupCreate groupCreate4 = new GroupCreate(Timestamp.valueOf("2020-02-19 12:00:00"),"p0004","g0004");
//        GroupCreate groupCreate5 = new GroupCreate(Timestamp.valueOf("2020-02-20 12:00:00"),"p0005","g0005");
//
//        insertGroupCreate(groupCreate1);
//        insertGroupCreate(groupCreate2);
//        insertGroupCreate(groupCreate3);
//        insertGroupCreate(groupCreate4);
//        insertGroupCreate(groupCreate5);
//
//        // group_record
//        GroupRecord groupRecord1 = new GroupRecord("g0001","project_team1");
//        GroupRecord groupRecord2 = new GroupRecord("g0002","project_team2");
//        GroupRecord groupRecord3 = new GroupRecord("g0003","project_team3");
//        GroupRecord groupRecord4 = new GroupRecord("g0004","project_team4");
//        GroupRecord groupRecord5 = new GroupRecord("g0005","project_team5");
//
//        insertGroupRecord(groupRecord1);
//        insertGroupRecord(groupRecord2);
//        insertGroupRecord(groupRecord3);
//        insertGroupRecord(groupRecord4);
//        insertGroupRecord(groupRecord5);
//
//
//        // groupchat_record
//        GroupChatRecord groupChatRecord1 = new GroupChatRecord(Timestamp.valueOf("2020-02-17 12:00:00"), "0001","Nice to see you guys! This is karry","g0001");
//        GroupChatRecord groupChatRecord2 = new GroupChatRecord(Timestamp.valueOf("2020-02-18 12:00:00"), "0002","Nice to see you guys! This is dora.","g0001");
//        GroupChatRecord groupChatRecord3 = new GroupChatRecord(Timestamp.valueOf("2020-02-19 12:00:00"), "0003","Nice to see you guys! This is gelila.","g0001");
//        GroupChatRecord groupChatRecord4 = new GroupChatRecord(Timestamp.valueOf("2020-02-20 12:00:00"), "0004","Nice to see you guys! This is tony.","g0001");
//        GroupChatRecord groupChatRecord5 = new GroupChatRecord(Timestamp.valueOf("2020-02-21 12:00:00"), "0005","Nice to see you guys! This is Mike.","g0001");
//
//        insertGroupChatRecord(groupChatRecord1);
//        insertGroupChatRecord(groupChatRecord2);
//        insertGroupChatRecord(groupChatRecord3);
//        insertGroupChatRecord(groupChatRecord4);
//        insertGroupChatRecord(groupChatRecord5);
//
//        // meeting_record
//        MeetingRecord meetingRecord1 = new MeetingRecord("m0001",10,"Welcome ceremony",Timestamp.valueOf("2020-08-16 12:00:00"),Timestamp.valueOf("2020-08-16 14:00:00"),"g0001");
//        MeetingRecord meetingRecord2 = new MeetingRecord("m0001",5,"team discussion",Timestamp.valueOf("2020-08-19 12:00:00"),Timestamp.valueOf("2020-08-19 13:00:00"),"g0001");
//        MeetingRecord meetingRecord3 = new MeetingRecord("m0001",6,"project discussion",Timestamp.valueOf("2020-09-10 12:00:00"),Timestamp.valueOf("2020-08-16 16:00:00"),"g0001");
//        MeetingRecord meetingRecord4 = new MeetingRecord("m0001",7,"Welcome ceremony",Timestamp.valueOf("2020-08-16 12:00:00"),Timestamp.valueOf("2020-08-16 14:00:00"),"g0002");
//        MeetingRecord meetingRecord5 = new MeetingRecord("m0001",4,"random chatting",Timestamp.valueOf("2020-08-20 17:00:00"),Timestamp.valueOf("2020-08-16 18:00:00"),"g0002");
//
//        insertMeetingRecord(meetingRecord1);
//        insertMeetingRecord(meetingRecord2);
//        insertMeetingRecord(meetingRecord3);
//        insertMeetingRecord(meetingRecord4);
//        insertMeetingRecord(meetingRecord5);
//
//        // schedule_record
//        ScheduleRecord scheduleRecord1 = new ScheduleRecord("s0001",Timestamp.valueOf("2020-08-16 12:00:00"),"event1","0001");
//        ScheduleRecord scheduleRecord2 = new ScheduleRecord("s0002",Timestamp.valueOf("2020-08-17 14:00:00"),"event2","0001");
//        ScheduleRecord scheduleRecord3 = new ScheduleRecord("s0003",Timestamp.valueOf("2020-08-18 16:00:00"),"event3","0002");
//        ScheduleRecord scheduleRecord4 = new ScheduleRecord("s0004",Timestamp.valueOf("2020-08-19 16:00:00"),"event4","0002");
//        ScheduleRecord scheduleRecord5 = new ScheduleRecord("s0005",Timestamp.valueOf("2020-08-20 17:00:00"),"event5","0003");
//
//        insertScheduleRecord(scheduleRecord1);
//        insertScheduleRecord(scheduleRecord2);
//        insertScheduleRecord(scheduleRecord3);
//        insertScheduleRecord(scheduleRecord4);
//        insertScheduleRecord(scheduleRecord5);
//
//
//        // task_status
//        TaskStatus taskStatus1 = new TaskStatus(Timestamp.valueOf("2020-08-16 12:00:00"),0);
//        TaskStatus taskStatus2 = new TaskStatus(Timestamp.valueOf("2020-08-17 14:00:00"),1);
//        TaskStatus taskStatus3 = new TaskStatus(Timestamp.valueOf("2020-08-18 16:00:00"),1);
//        TaskStatus taskStatus4 = new TaskStatus(Timestamp.valueOf("2020-08-19 16:00:00"),1);
//        TaskStatus taskStatus5 = new TaskStatus(Timestamp.valueOf("2020-08-20 17:00:00"),0);
//
//        insertTaskStatus(taskStatus1);
//        insertTaskStatus(taskStatus2);
//        insertTaskStatus(taskStatus3);
//        insertTaskStatus(taskStatus4);
//        insertTaskStatus(taskStatus5);
//
//        // contain_task
//        ContainTask containTask1 = new ContainTask("Finish Milestone1",1,"s0001");
//        ContainTask containTask2 = new ContainTask("Finish Milestone2",3,"s0002");
//        ContainTask containTask3 = new ContainTask("Finish Milestone3",2,"s0003");
//        ContainTask containTask4 = new ContainTask("Finish Milestone4",4,"s0004");
//        ContainTask containTask5 = new ContainTask("Finish Milestone5",2,"s0005");
//
//        insertContainTask(containTask1);
//        insertContainTask(containTask2);
//        insertContainTask(containTask3);
//        insertContainTask(containTask4);
//        insertContainTask(containTask5);
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
            System.out.println("Debug: drop " + tableName);
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

    // Set up the user_info table
    private void userInfoSetUp() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE user_info (" +
                    "user_id varchar2(10), " +
                    "password varchar2(10) NOT NULL, " +
                    "name varchar2(20) NOT NULL, " +
                    "city varchar2(20), " +
                    "email varchar2(100), " +
                    "PRIMARY KEY (user_id))");

            stmt.close();
        } catch (SQLException e) {
            // TODO: delete
            System.out.println("Debug: userInfoSetUp()");

            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    // Set up the time_zone table
    private void timeZoneSetUp() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE time_zone (" +
                    "city varchar2(20), " +
                    "time_zone varchar2(5) NOT NULL, " +
                    "PRIMARY KEY (city))");

            stmt.close();
        } catch (SQLException e) {
            // TODO: delete
            System.out.println("Debug: timeZoneSetUp()");

            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    // Set up the individual_chat table
    private void individualChatSetUp() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE individual_chat (" +
                    "time TIMESTAMP, " +
                    "sender varchar2(10), " +
                    "receiver varchar2(10), " +
                    "content varchar2(100), " +
                    "PRIMARY KEY (sender, receiver, time), " +
                    "FOREIGN KEY (sender) REFERENCES user_info ON DELETE CASCADE, " +
                    "FOREIGN KEY (receiver) REFERENCES user_info ON DELETE CASCADE)");

            stmt.close();
        } catch (SQLException e) {
            // TODO: delete
            System.out.println("Debug: individualChatSetUp()");

            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    // Set up the Media table
    private void mediaSetUp() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE media (" +
                    "media_type varchar2(20), " +
                    "url varchar2(1000), " +
                    "PRIMARY KEY (url))");

            stmt.close();
        } catch (SQLException e) {
            // TODO: delete
            System.out.println("Debug: MediaSetUp()");

            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    // Set up the individual_chat table
    private void sharePostSetUp() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE share_post (" +
                    "post_id varchar2(10), " +
                    "time TIMESTAMP, " +
                    "user_id varchar2(10) NOT NULL, " +
                    "content varchar2(100), " +
                    "media_url varchar2(1000), " +
                    "PRIMARY KEY (post_id), " +
                    "FOREIGN KEY (user_id) REFERENCES user_info ON DELETE CASCADE, " +
                    "FOREIGN KEY (media_url) REFERENCES media)");

            stmt.close();
        } catch (SQLException e) {
            // TODO: delete
            System.out.println("Debug: SharePostSetUp()");

            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }


//    // Set up the PostContains table
//    private void postContainsSetUp() {
//        try {
//            Statement stmt = connection.createStatement();
//            stmt.executeUpdate("CREATE TABLE post_contains (" +
//                    "postid varchar2(10), " +
//                    "mediaid varchar2(20), " +
//                    "PRIMARY KEY (postid,mediaid))," +
//                    "FOREIGN KEY (postid) REFERENCES Share_Post," +
//                    "FOREIGN KEY (mediaid) REFERENCES Media)");
//
//            stmt.close();
//        } catch (SQLException e) {
//            // TODO: delete
//            System.out.println("Debug: PostContainsSetUp()");
//
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//        }
//    }
//
//    // Set up the mini program table
//    private void miniProgramSetUp() {
//        try {
//            Statement stmt = connection.createStatement();
//            stmt.executeUpdate("CREATE TABLE mini_program (" +
//                    "miniid varchar2(10), " +
//                    "mediaid varchar2(20)," +
//                    "type varchar2(20) " +
//                    "PRIMARY KEY (miniid))");
//
//            stmt.close();
//        } catch (SQLException e) {
//            // TODO: delete
//            System.out.println("Debug: miniProgramSetUp()");
//
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//        }
//    }
//
//    // Set up the mini program record table
//    private void miniProgramRecordSetUp() {
//        try {
//            Statement stmt = connection.createStatement();
//            stmt.executeUpdate("CREATE TABLE miniprogram_record (" +
//                    "u_id varchar2(10), " +
//                    "miniid varchar2(20)," +
//                    "time TIMESTAMP " +
//                    "PRIMARY KEY (u_id , miniid,time)," +
//                    "FOREIGN KEY (u_id) REFERENCES user_info," +
//                    "FOREIGN KEY (miniid) REFERENCES mini_program)");
//
//            stmt.close();
//        } catch (SQLException e) {
//            // TODO: delete
//            System.out.println("Debug: miniProgramRecordSetUp()");
//
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//        }
//    }
//
//    // Set up the group member table
//    private void groupMemberSetUp() {
//        try {
//            Statement stmt = connection.createStatement();
//            stmt.executeUpdate("CREATE TABLE group_member" +
//                    "(nickname varchar2(10)," +
//                    "u_id varchar2(10)," +
//                    "PRIMARY KEY (u_id)," +
//                    "FOREIGN KEY (u_id) REFERENCES User_info)");
//
//            stmt.close();
//        } catch (SQLException e) {
//            // TODO: delete
//            System.out.println("Debug: groupMemberSetUp()");
//
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//        }
//    }
//
//    // Set up the group admin table
//    private void groupadministratorSetUp() {
//        try {
//            Statement stmt = connection.createStatement();
//            stmt.executeUpdate("CREATE TABLE group_administrator" +
//                    "(nickname varchar2(10)," +
//                    "u_id varchar2(10)," +
//                    "PRIMARY KEY (u_id)," +
//                    "FOREIGN KEY (u_id) REFERENCES User_info)");
//
//            stmt.close();
//        } catch (SQLException e) {
//            // TODO: delete
//            System.out.println("Debug: groupadministratorSetUp()");
//
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//        }
//    }
//
//    // Set up the group record table
//    private void groupRecordSetUp() {
//        try {
//            Statement stmt = connection.createStatement();
//            stmt.executeUpdate("CREATE TABLE group_record" +
//                    "(gid varchar2(10)," +
//                    "gname varchar2(10)," +
//                    "PRIMARY KEY (gid)");
//
//            stmt.close();
//        } catch (SQLException e) {
//            // TODO: delete
//            System.out.println("Debug: groupRecordSetUp()");
//
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//        }
//    }
//
//    // Set up the group joins table
//    private void groupJoinsSetUp() {
//        try {
//            Statement stmt = connection.createStatement();
//            stmt.executeUpdate("CREATE TABLE group_joins" +
//                    "(join_time TIMESTAMP," +
//                    "u_id varchar2(10)," +
//                    "gid varchar2(10)," +
//                    "PRIMARY KEY (u_id,gid)," +
//                    "FOREIGN KEY (u_id) REFERENCES user_info," +
//                    "FOREIGN KEY (gid) REFERENCES group_record)");
//
//            stmt.close();
//        } catch (SQLException e) {
//            // TODO: delete
//            System.out.println("Debug: groupJoinsSetUp()");
//
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//        }
//    }
//
//    // Set up the group creates table
//    private void groupCreatesSetUp() {
//        try {
//            Statement stmt = connection.createStatement();
//            stmt.executeUpdate("CREATE TABLE group_creates" +
//                    "(create_time TIMESTAMP," +
//                    "u_id varchar2(10)," +
//                    "gid varchar2(10)," +
//                    "PRIMARY KEY (u_id,gid)," +
//                    "FOREIGN KEY (u_id) REFERENCES user_info," +
//                    "FOREIGN KEY (gid) REFERENCES group_record)");
//
//            stmt.close();
//        } catch (SQLException e) {
//            // TODO: delete
//            System.out.println("Debug: groupCreatesSetUp()");
//
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//        }
//    }
//
//    // Set up the group chat record table
//    private void groupChatRecordSetUp() {
//        try {
//            Statement stmt = connection.createStatement();
//            stmt.executeUpdate("CREATE TABLE groupchat_record" +
//                    "(time TIMESTAMP," +
//                    "content varchar2(100)," +
//                    "gid varchar2(10)," +
//                    "PRIMARY KEY (u_id,gid, time)," +
//                    "FOREIGN KEY (u_id) REFERENCES user_info," +
//                    "FOREIGN KEY (gid) REFERENCES group_record ON DELETE CASCADE)");
//
//            stmt.close();
//        } catch (SQLException e) {
//            // TODO: delete
//            System.out.println("Debug: groupChatRecordSetUp()");
//
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//        }
//    }
//
//    // Set up the meeting record table
//    private void meetingRecordSetUp() {
//        try {
//            Statement stmt = connection.createStatement();
//            stmt.executeUpdate("CREATE TABLE groupchat_record" +
//                    "(mid varchar2(10)," +
//                    "attendance INTEGER," +
//                    "topic varchar2(50)," +
//                    "start_time TIMESTAMP," +
//                    "end_time TIMESTAMP," +
//                    "gid varchar2(10) NOT NULL," +
//                    "PRIMAR KEY (mid)" +
//                    "FOREIGN KEY (gid) REFERENCES group_record ON DELETE CASCADE)");
//
//            stmt.close();
//        } catch (SQLException e) {
//            // TODO: delete
//            System.out.println("Debug: meetingRecordSetUp()");
//
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//        }
//    }
//
//    // Set up the task status table
//    private void taskStatusSetUp() {
//        try {
//            Statement stmt = connection.createStatement();
//            stmt.executeUpdate("CREATE TABLE task_status" +
//                    "(stime varchar2(10)," +
//                    "passed INTEGER," +
//                    "PRIMARY KEY (stime))");
//
//            stmt.close();
//        } catch (SQLException e) {
//            // TODO: delete
//            System.out.println("Debug: taskStatusSetUp()");
//
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//        }
//    }
//
//    // Set up the schedule record table
//    private void scheduleRecordSetUp() {
//        try {
//            Statement stmt = connection.createStatement();
//            stmt.executeUpdate("CREATE TABLE schedule_record" +
//                    "(sid varchar2(10)," +
//                    "stime TIMESTAMP," +
//                    "event varchar2(50)," +
//                    "u_id varchar2(10) NOT NULL," +
//                    "PRIMARY KEY (sid)," +
//                    "FOREIGN KEY (u_id) REFERENCES user_info," +
//                    "FOREIGN KEY (gid) REFERENCES task_status)");
//
//            stmt.close();
//        } catch (SQLException e) {
//            // TODO: delete
//            System.out.println("Debug: scheduleRecordSetUp()");
//
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//        }
//    }
//
//    // Set up contain_task table
//    private void containTaskSetUp() {
//        try {
//            Statement stmt = connection.createStatement();
//            stmt.executeUpdate("CREATE TABLE contain_task" +
//                    "(tname varchar2(10)," +
//                    "priority INTEGER," +
//                    "sid varchar2(20)," +
//                    "PRIMARY KEY (sid,tname)," +
//                    "FOREIGN KEY (sid) REFERENCES schedule_record ON DELETE CASCADE)");
//
//            stmt.close();
//        } catch (SQLException e) {
//            // TODO: delete
//            System.out.println("Debug: containTaskSetUp()");
//
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//        }
//    }



    /*
     * ==================================================================
     *
     * Below are all the methods for insert statements
     *
     * ==================================================================
     * */

    // Insert User
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

    // Insert TimeZone
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

    // Insert IndividualChat
    public void insertIndividualChat(IndividualChat chat) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO individual_chat VALUES (?,?,?,?)");

            ps.setTimestamp(1, chat.getTime());
            ps.setString(2, chat.getSender().getUserid());
            ps.setString(3, chat.getReceiver().getUserid());
            ps.setString(4, chat.getContent());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    // Insert Media
    public void insertMedia(Media media) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO media VALUES (?,?)");

            ps.setString(1, media.getMediaType());
            ps.setString(2, media.getUrl());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    // Insert SharePost
    public void insertSharePost(SharePost sharePost) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO share_post VALUES (?,?,?,?,?)");

            ps.setString(1, sharePost.getPostid());
            ps.setTimestamp(2, sharePost.getTime());
            ps.setString(3, sharePost.getUser().getUserid());
            ps.setString(4, sharePost.getContent());
            if (sharePost.getMedia() == null) {
                ps.setNull(5, java.sql.Types.CHAR);
            } else {
                ps.setString(5, sharePost.getMedia().getUrl());
            }

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println("debug insertSharePost()");
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }


//    // Insert PostContains
//    public void insertPostContains(PostContains postContains) {
//        try {
//            PreparedStatement ps = connection.prepareStatement("INSERT INTO share_post VALUES (?,?)");
//
//            ps.setString(1, postContains.getPostid());
//            ps.setString(2, postContains.getMediaid());
//
//            ps.executeUpdate();
//            connection.commit();
//
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//            rollbackConnection();
//        }
//    }
//
//    // Insert MiniProgram
//    public void insertMiniProgram(MiniProgram miniProgram) {
//        try {
//            PreparedStatement ps = connection.prepareStatement("INSERT INTO share_post VALUES (?,?)");
//
//            ps.setString(1, miniProgram.getPid());
//            ps.setString(2, miniProgram.getPname());
//
//            ps.executeUpdate();
//            connection.commit();
//
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//            rollbackConnection();
//        }
//    }
//
//    // Insert MiniProgramRecord
//    public void insertMiniProgramRecord(MiniProgramRecord miniProgramRecord) {
//        try {
//            PreparedStatement ps = connection.prepareStatement("INSERT INTO share_post VALUES (?,?,?)");
//
//            ps.setString(1, miniProgramRecord.getUid());
//            ps.setString(2, miniProgramRecord.getPid());
//            ps.setString(3, miniProgramRecord.getTime().toString());
//
//            ps.executeUpdate();
//            connection.commit();
//
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//            rollbackConnection();
//        }
//    }
//
//    // Insert GroupMember
//    public void insertGroupMember(GroupMemberAdministrator groupMember) {
//        try {
//            PreparedStatement ps = connection.prepareStatement("INSERT INTO share_post VALUES (?,?)");
//
//            ps.setString(1, groupMember.getNickname());
//            ps.setString(2, groupMember.getUid());
//
//            ps.executeUpdate();
//            connection.commit();
//
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//            rollbackConnection();
//        }
//    }
//
//    // Insert GroupAdministrator
//    public void insertGroupAdministrator(GroupMemberAdministrator groupAdministrator) {
//        try {
//            PreparedStatement ps = connection.prepareStatement("INSERT INTO share_post VALUES (?,?)");
//
//            ps.setString(1, groupAdministrator.getNickname());
//            ps.setString(2, groupAdministrator.getUid());
//
//            ps.executeUpdate();
//            connection.commit();
//
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//            rollbackConnection();
//        }
//    }
//
//    // Insert GroupRecord
//    public void insertGroupRecord(GroupRecord groupRecord) {
//        try {
//            PreparedStatement ps = connection.prepareStatement("INSERT INTO share_post VALUES (?,?)");
//
//            ps.setString(1, groupRecord.getGid());
//            ps.setString(2, groupRecord.getGname());
//
//            ps.executeUpdate();
//            connection.commit();
//
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//            rollbackConnection();
//        }
//    }
//
//    // Insert GroupJoins
//    public void insertGroupJoins(GroupJoins groupJoins) {
//        try {
//            PreparedStatement ps = connection.prepareStatement("INSERT INTO share_post VALUES (?,?,?)");
//
//            ps.setString(1, groupJoins.getJoinTime().toString());
//            ps.setString(2, groupJoins.getUid());
//            ps.setString(3, groupJoins.getGid());
//
//            ps.executeUpdate();
//            connection.commit();
//
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//            rollbackConnection();
//        }
//    }
//
//    // Insert GroupCreate
//    public void insertGroupCreate(GroupCreate groupCreate) {
//        try {
//            PreparedStatement ps = connection.prepareStatement("INSERT INTO share_post VALUES (?,?,?)");
//
//            ps.setString(1, groupCreate.getCreateTime().toString());
//            ps.setString(2, groupCreate.getUid());
//            ps.setString(3, groupCreate.getGid());
//
//            ps.executeUpdate();
//            connection.commit();
//
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//            rollbackConnection();
//        }
//    }
//
//    // Insert groupchat record
//    public void insertGroupChatRecord(GroupChatRecord gcr) {
//        try {
//            PreparedStatement ps = connection.prepareStatement("INSERT INTO groupchat_record VALUES (?,?,?)");
//
//            ps.setString(1, gcr.getTime().toString());
//            ps.setString(2, gcr.getContent());
//            ps.setString(3, gcr.getGid());
//
//            ps.executeUpdate();
//            connection.commit();
//
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//            rollbackConnection();
//        }
//    }


    // Insert meeting record
//    public void insertMeetingRecord(MeetingRecord mr) {
//        try {
//            PreparedStatement ps = connection.prepareStatement("INSERT INTO meeting_record VALUES (?,?,?,?,?,?)");
//
//            ps.setString(1, mr.getMid());
//            ps.setString(2, Integer.toString(mr.getAttendance()));
//            ps.setString(3, mr.getTopic());
//            ps.setString(4, mr.getStartTime().toString());
//            ps.setString(5, mr.getEndTime().toString());
//            ps.setString(6, mr.getGid());
//
//            ps.executeUpdate();
//            connection.commit();
//
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//            rollbackConnection();
//        }
//    }


    // Insert task status
//    public void insertTaskStatus(TaskStatus ts) {
//        try {
//            PreparedStatement ps = connection.prepareStatement("INSERT INTO task_status VALUES (?,?)");
//
//            ps.setString(1, ts.getStime().toString());
//            ps.setString(2, Integer.toString(ts.getPassed()));
//
//            ps.executeUpdate();
//            connection.commit();
//
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//            rollbackConnection();
//        }
//    }

    // Insert schedule record
//    public void insertScheduleRecord(ScheduleRecord sr) {
//        try {
//            PreparedStatement ps = connection.prepareStatement("INSERT INTO schedule_record VALUES (?,?,?,?)");
//
//            ps.setString(1, sr.getSid());
//            ps.setString(2, sr.getStime().toString());
//            ps.setString(3, sr.getEvent());
//            ps.setString(4, sr.getUid());
//
//            ps.executeUpdate();
//            connection.commit();
//
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//            rollbackConnection();
//        }
//    }

    // Insert contain task
//    public void insertContainTask(ContainTask ct) {
//        try {
//            PreparedStatement ps = connection.prepareStatement("INSERT INTO contain_task VALUES (?,?,?)");
//
//            ps.setString(1, ct.getTname());
//            ps.setString(2, Integer.toString(ct.getPriorityVal()));
//            ps.setString(3, ct.getSid());
//
//            ps.executeUpdate();
//            connection.commit();
//
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//            rollbackConnection();
//        }
//    }





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

    // Get all users' info combined with time_zone info
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

    // Reset user's password
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

    // Delete a user
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

    // Get all the individual chat records between two users
    public IndividualChat[] getIndividualChatHistory(String uid1, String uid2) {
        ArrayList<IndividualChat> result = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM individual_chat, user_info " +
                    "WHERE ((sender = \'" + uid1 + "\' AND receiver = \'" + uid2 + "\') " +
                    "OR (sender = \'" + uid2 + "\' AND receiver = \'" + uid1 + "\')) " +
                    "AND (sender = user_id) ORDER BY time");

            while(rs.next()) {
                IndividualChat chat = new IndividualChat(
                        getUserByID(rs.getString("sender")),
                        getUserByID(rs.getString("receiver")),
                        rs.getString("content"),
                        Timestamp.valueOf(rs.getString("time")));
                result.add(chat);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new IndividualChat[result.size()]);
    }

    // Get all the posts posted by a user
    public SharePost[] getIndividualPost(String uid) {
        ArrayList<SharePost> result = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM share_post " +
                    "WHERE user_id = \'" + uid + "\' ORDER BY time");

            while(rs.next()) {
                SharePost post = new SharePost(
                        rs.getString("post_id"),
                        Timestamp.valueOf(rs.getString("time")),
                        rs.getString("content"),
                        getUserByID(rs.getString("user_id")),
                        getMediaByUrl(rs.getString("media_url")));
                result.add(post);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Debug: getIndividualPost()");
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new SharePost[result.size()]);
    }

    // Get all the posts in share_post table
    public SharePost[] getPosts() {
        ArrayList<SharePost> result = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM share_post ");

            while(rs.next()) {
                SharePost post = new SharePost(
                        rs.getString("post_id"),
                        Timestamp.valueOf(rs.getString("time")),
                        rs.getString("content"),
                        getUserByID(rs.getString("user_id")),
                        getMediaByUrl(rs.getString("media_url")));
                result.add(post);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new SharePost[result.size()]);
    }

    // Get a post by post_id
    public SharePost getPostByID(String post_id) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM share_post " +
                    "WHERE post_id = \'" + post_id + "\'");

            while(rs.next()) {
                SharePost post = new SharePost(
                        rs.getString("post_id"),
                        Timestamp.valueOf(rs.getString("time")),
                        rs.getString("content"),
                        getUserByID(rs.getString("user_id")),
                        getMediaByUrl(rs.getString("media_url")));
                return post;
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return null;
    }

    // Delete a post
    public void deletePost(SharePost post) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM share_post WHERE post_id = ?");
            ps.setString(1, post.getPostid());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    // Get all the media
    public Media[] getMedia() {
        ArrayList<Media> result = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM media ");

            while(rs.next()) {
                Media media = new Media(
                        rs.getString("media_type"),
                        rs.getString("url"));
                result.add(media);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new Media[result.size()]);
    }

    // Get a media given its url
    public Media getMediaByUrl(String url) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM media " +
                    "WHERE url = \'" + url + "\'");

            while(rs.next()) {
                Media media = new Media(
                        rs.getString("media_type"),
                        rs.getString("url"));
                return media;
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return null;
    }



    // TODO: delete
    // Testing tables
    public void print() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM share_post");

            while(rs.next()) {
                System.out.println(rs.getString("post_id") +
                        " | " + rs.getString("user_id") +
                        ": " + rs.getString("content") +
                        " | " + rs.getString("time"));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void updateUserName(String uid, String input) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE user_info SET name = ? WHERE user_id = ?");
            ps.setString(1, input);
            ps.setString(2, uid);

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void updateUserCity(String uid, String input) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE user_info SET city = ? WHERE user_id = ?");
            ps.setString(1, input);
            ps.setString(2, uid);

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void updateUserEmail(String uid, String input) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE user_info SET email = ? WHERE user_id = ?");
            ps.setString(1, input);
            ps.setString(2, uid);

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }
}