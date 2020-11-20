package database;

import model.*;
import model.group.*;
import model.meeting.MeetingRecord;
import model.post.Media;
import model.post.SharePost;
import model.user.TimeZone;
import model.user.User;

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
        dropTable("meeting_joins");
        dropTable("contain_task");
        dropTable("schedule_record");
        dropTable("task_status");
        dropTable("meeting_record");
        dropTable("group_chat");
        dropTable("group_admin");
        dropTable("group_joins");
        dropTable("group_creates");
        dropTable("share_post");
        dropTable("media");
        dropTable("miniprogram_record");
        dropTable("mini_program");
        dropTable("group_record");
        dropTable("individual_chat");
        dropTable("time_zone");
        dropTable("user_info");
    }

    // Set up the database
    public void databaseSetUp() {
        userInfoSetUp();
        timeZoneSetUp();
        individualChatSetUp();
        mediaSetUp();
        sharePostSetUp();
        groupRecordSetUp();
        groupAdminSetUp();
        groupJoinsSetUp();
        groupCreatesSetUp();
        groupChatSetUp();
        meetingRecordSetUp();
        MeetingJoinsSetUp();
//        taskStatusSetUp();
//        scheduleRecordSetUp();
//        containTaskSetUp();
//        miniProgramSetUp();
//        miniProgramRecordSetUp();
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
        User admin = new User("0000", "0", "Admin", tz2, "admin@gmail.com");
        insertUser(admin);
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

        // group_record
        Group group1 = new Group("G1", Timestamp.valueOf("2020-11-17 11:00:00"), "CPSC 304", admin);
        Group group2 = new Group("G2", Timestamp.valueOf("2020-11-17 12:00:00"), "Project Team 1", u1);
        Group group3 = new Group("G3", Timestamp.valueOf("2020-11-18 12:01:00"), "Project Team 2", u4);

        insertGroupRecord(group1);
        insertGroupRecord(group2);
        insertGroupRecord(group3);

        // group_creates
        insertGroupCreates(group1);
        insertGroupCreates(group2);
        insertGroupCreates(group3);

        // group_admin
        insertGroupAdmin(admin, group1);
        insertGroupAdmin(u1, group2);
        insertGroupAdmin(u4, group3);

        // group_joins
        GroupMember m0 = new GroupMember(Timestamp.valueOf("2020-11-17 11:00:00"), admin, group1, null);
        GroupMember m1 = new GroupMember(Timestamp.valueOf("2020-11-17 11:00:01"), u1, group1, null);
        GroupMember m2 = new GroupMember(Timestamp.valueOf("2020-11-17 11:00:02"), u2, group1, null);
        GroupMember m3 = new GroupMember(Timestamp.valueOf("2020-11-17 11:00:03"), u3, group1, null);
        GroupMember m4 = new GroupMember(Timestamp.valueOf("2020-11-18 11:00:04"), u4, group2, null);
        GroupMember m5 = new GroupMember(Timestamp.valueOf("2020-11-18 11:00:05"), u5, group2, null);
        GroupMember member1 = new GroupMember(Timestamp.valueOf("2020-11-17 12:00:00"), u1, group2, null);
        GroupMember member2 = new GroupMember(Timestamp.valueOf("2020-11-17 12:00:01"), u2, group2, "Frozen Cloud");
        GroupMember member3 = new GroupMember(Timestamp.valueOf("2020-11-17 12:00:02"), u3, group2, "Doooora");
        GroupMember member4 = new GroupMember(Timestamp.valueOf("2020-11-18 12:01:00"), u4, group3, "7k+");
        GroupMember member5 = new GroupMember(Timestamp.valueOf("2020-11-18 12:01:01"), u5, group3, "magge");

        insertGroupJoins(m0);
        insertGroupJoins(m1);
        insertGroupJoins(m2);
        insertGroupJoins(m3);
        insertGroupJoins(m4);
        insertGroupJoins(m5);
        insertGroupJoins(member1);
        insertGroupJoins(member2);
        insertGroupJoins(member3);
        insertGroupJoins(member4);
        insertGroupJoins(member5);

        // group_chat
        GroupChat groupChat0 = new GroupChat(Timestamp.valueOf("2020-11-17 11:01:30"), member1,"Welcome to CPSC 304. Please create your own project team.", group1);
        GroupChat groupChat1 = new GroupChat(Timestamp.valueOf("2020-11-17 12:01:30"), member1,"Hello folks!", group2);
        GroupChat groupChat2 = new GroupChat(Timestamp.valueOf("2020-11-17 12:02:00"), member2,"Nice to see you guys! This is Karry.", group2);
        GroupChat groupChat3 = new GroupChat(Timestamp.valueOf("2020-11-17 12:03:00"), member3,"Hi, I am Dora!", group2);
        GroupChat groupChat4 = new GroupChat(Timestamp.valueOf("2020-11-18 12:01:30"), member4,"This is our project group", group3);
        GroupChat groupChat5 = new GroupChat(Timestamp.valueOf("2020-11-18 12:02:00"), member5,"Thanks for doing this!", group3);

        insertGroupChat(groupChat0);
        insertGroupChat(groupChat1);
        insertGroupChat(groupChat2);
        insertGroupChat(groupChat3);
        insertGroupChat(groupChat4);
        insertGroupChat(groupChat5);

        // meeting_record
        MeetingRecord meetingRecord1 = new MeetingRecord("M1",6,"Welcome Ceremony", Timestamp.valueOf("2020-11-17 18:00:00"), Timestamp.valueOf("2020-11-17 19:00:00"), group1);
        MeetingRecord meetingRecord2 = new MeetingRecord("M2",3,"Team Discussion", Timestamp.valueOf("2020-11-17 19:03:00"), Timestamp.valueOf("2020-11-17 20:00:00"), group2);
        MeetingRecord meetingRecord3 = new MeetingRecord("M3",2,"Project Meeting", Timestamp.valueOf("2020-11-19 12:00:00"), Timestamp.valueOf("2020-11-19 14:00:00"), group3);

        insertMeetingRecord(meetingRecord1);
        insertMeetingRecord(meetingRecord2);
        insertMeetingRecord(meetingRecord3);

        // meeting_joins
        insertMeetingJoins(meetingRecord1, admin);
        insertMeetingJoins(meetingRecord1, u1);
        insertMeetingJoins(meetingRecord1, u2);
        insertMeetingJoins(meetingRecord1, u3);
        insertMeetingJoins(meetingRecord1, u4);
        insertMeetingJoins(meetingRecord1, u5);
        insertMeetingJoins(meetingRecord2, u1);
        insertMeetingJoins(meetingRecord2, u2);
        insertMeetingJoins(meetingRecord2, u3);
        insertMeetingJoins(meetingRecord3, u4);
        insertMeetingJoins(meetingRecord3, u5);


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
//        // schedule_record
//        ScheduleRecord scheduleRecord1 = new ScheduleRecord("s0001","event1",u1,taskStatus1);
//        ScheduleRecord scheduleRecord2 = new ScheduleRecord("s0002","event2",u1,taskStatus2);
//        ScheduleRecord scheduleRecord3 = new ScheduleRecord("s0003","event3",u2,taskStatus3);
//        ScheduleRecord scheduleRecord4 = new ScheduleRecord("s0004","event4",u2,taskStatus4);
//        ScheduleRecord scheduleRecord5 = new ScheduleRecord("s0005","event5",u3,taskStatus5);
//
//        insertScheduleRecord(scheduleRecord1);
//        insertScheduleRecord(scheduleRecord2);
//        insertScheduleRecord(scheduleRecord3);
//        insertScheduleRecord(scheduleRecord4);
//        insertScheduleRecord(scheduleRecord5);
//
//
//
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
//
//        // mini_program
//        MiniProgram miniProgram1 = new MiniProgram("mi0001", "Payroll Check", "Work");
//        MiniProgram miniProgram2 = new MiniProgram("mi0002", "Monthly Report", "Work");
//        MiniProgram miniProgram3 = new MiniProgram("mi0003", "Announcement", "Work");
//        MiniProgram miniProgram4 = new MiniProgram("mi0004", "Dashboards", "Work");
//        MiniProgram miniProgram5 = new MiniProgram("mi0005", "Expenses", "Work");
//
//        insertMiniProgram(miniProgram1);
//        insertMiniProgram(miniProgram2);
//        insertMiniProgram(miniProgram3);
//        insertMiniProgram(miniProgram4);
//        insertMiniProgram(miniProgram5);
//
//        // miniprogram_record
//        MiniProgramRecord miniProgramRecord1 = new MiniProgramRecord("0001","mi0001",Timestamp.valueOf("2020-01-15 12:00:00"));
//        MiniProgramRecord miniProgramRecord2 = new MiniProgramRecord("0002","mi0002",Timestamp.valueOf("2020-01-15 12:30:00"));
//        MiniProgramRecord miniProgramRecord3 = new MiniProgramRecord("0003","mi0003",Timestamp.valueOf("2020-01-16 12:00:00"));
//        MiniProgramRecord miniProgramRecord4 = new MiniProgramRecord("0004","mi0004",Timestamp.valueOf("2020-01-17 12:00:00"));
//        MiniProgramRecord miniProgramRecord5 = new MiniProgramRecord("0005","mi0005",Timestamp.valueOf("2020-01-18 12:00:00"));
//
//        insertMiniProgramRecord(miniProgramRecord1);
//        insertMiniProgramRecord(miniProgramRecord2);
//        insertMiniProgramRecord(miniProgramRecord3);
//        insertMiniProgramRecord(miniProgramRecord4);
//        insertMiniProgramRecord(miniProgramRecord5);
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

    // Set up the Media_table
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

    // Set up the group_record table
    // This table also stores the group creation time,
    // each time when a user creates a group,
    // the user will automatically become the administrator (one record for group_admin),
    // and the user will also become a group member (one record for group_joins),
    // where the join_time is also the group creation time
    private void groupRecordSetUp() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE group_record (" +
                    "group_id varchar2(10)," +
                    "group_name varchar2(30), " +
                    "PRIMARY KEY (group_id))");

            stmt.close();
        } catch (SQLException e) {
            // TODO: delete
            System.out.println("Debug: groupRecordSetUp()");

            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    // Set up the group_creates table
    private void groupCreatesSetUp() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE group_creates (" +
                    "create_time TIMESTAMP," +
                    "user_id varchar2(10)," +
                    "group_id varchar2(10)," +
                    "PRIMARY KEY (user_id, group_id)," +
                    "FOREIGN KEY (user_id) REFERENCES user_info," +
                    "FOREIGN KEY (group_id) REFERENCES group_record ON DELETE CASCADE)");

            stmt.close();
        } catch (SQLException e) {
            // TODO: delete
            System.out.println("Debug: groupCreatesSetUp()");

            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    // Set up the group_admin table
    // This table is used to identify who are the administrators of each group
    // A group could have more than one group administrators
    private void groupAdminSetUp() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE group_admin (" +
                    "user_id varchar2(10)," +
                    "group_id varchar2(10)," +
                    "PRIMARY KEY (user_id, group_id)," +
                    "FOREIGN KEY (user_id) REFERENCES user_info ON DELETE CASCADE," +
                    "FOREIGN KEY (group_id) REFERENCES group_record ON DELETE CASCADE)");

            stmt.close();
        } catch (SQLException e) {
            // TODO: delete
            System.out.println("Debug: groupAdminSetUp()");

            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    // Set up the group_joins table
    // This tables also contains the group member info (nickname)
    // A group admin is also a group member,
    // so a group admin's info is also recorded here
    private void groupJoinsSetUp() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE group_joins (" +
                    "join_time TIMESTAMP," +
                    "user_id varchar2(10)," +
                    "group_id varchar2(10)," +
                    "nickname varchar2(20), " +
                    "PRIMARY KEY (user_id, group_id)," +
                    "FOREIGN KEY (user_id) REFERENCES user_info ON DELETE CASCADE," +
                    "FOREIGN KEY (group_id) REFERENCES group_record ON DELETE CASCADE)");

            stmt.close();
        } catch (SQLException e) {
            // TODO: delete
            System.out.println("Debug: groupJoinsSetUp()");

            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    // Set up the group_chat record table
    private void groupChatSetUp() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE group_chat (" +
                    "time TIMESTAMP," +
                    "user_id varchar2(10)," +
                    "content varchar2(100)," +
                    "group_id varchar2(10)," +
                    "PRIMARY KEY (user_id, group_id, time)," +
                    "FOREIGN KEY (user_id) REFERENCES user_info," +
                    "FOREIGN KEY (group_id) REFERENCES group_record ON DELETE CASCADE)");

            stmt.close();
        } catch (SQLException e) {
            // TODO: delete
            System.out.println("Debug: groupChatSetUp()");

            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    // Set up the meeting_record table
    private void meetingRecordSetUp() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE meeting_record (" +
                    "meeting_id varchar2(10)," +
                    "attendance INTEGER," +
                    "topic varchar2(50)," +
                    "start_time TIMESTAMP," +
                    "end_time TIMESTAMP," +
                    "group_id varchar2(10) NOT NULL," +
                    "PRIMARY KEY (meeting_id)," +
                    "FOREIGN KEY (group_id) REFERENCES group_record ON DELETE CASCADE)");

            stmt.close();
        } catch (SQLException e) {
            // TODO: delete
            System.out.println("Debug: meetingRecordSetUp()");

            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    // Set up the joint_meeting table
    private void MeetingJoinsSetUp() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE meeting_joins (" +
                    "meeting_id varchar2(10)," +
                    "user_id varchar2(10)," +
                    "PRIMARY KEY (meeting_id, user_id)," +
                    "FOREIGN KEY (user_id) REFERENCES user_info," +
                    "FOREIGN KEY (meeting_id) REFERENCES meeting_record ON DELETE CASCADE)");

            stmt.close();
        } catch (SQLException e) {
            // TODO: delete
            System.out.println("Debug: joinMeetingSetUp()");

            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

//    // Set up the task status table
//    private void taskStatusSetUp() {
//        try {
//            Statement stmt = connection.createStatement();
//            stmt.executeUpdate("CREATE TABLE task_status" +
//                    "(stime TIMESTAMP," +
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
//                    "(sid varchar2(10) PRIMARY KEY," +
//                    "stime TIMESTAMP," +
//                    "event varchar2(50)," +
//                    "u_id varchar2(10)," +
//                    "FOREIGN KEY (u_id) REFERENCES user_info," +
//                    "FOREIGN KEY (stime) REFERENCES task_status)");
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
//                    "(tname varchar2(50)," +
//                    "priority_val INTEGER," +
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

//    // Set up the mini program table
//    private void miniProgramSetUp() {
//        try {
//            Statement stmt = connection.createStatement();
//            stmt.executeUpdate("CREATE TABLE mini_program (" +
//                    "miniid varchar2(10), " +
//                    "mediaid varchar2(20)," +
//                    "type varchar2(20), " +
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
//                    "time TIMESTAMP," +
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

    // Insert GroupAdministrator
    public void insertGroupAdmin(User user, Group group) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO group_admin VALUES (?,?)");

            ps.setString(1, user.getUserid());
            ps.setString(2, group.getGroupid());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println("Debug: inserGroupAdmin()");
            System.out.println(EXCEPTION_TAG + " " + e.getCause() + " " + e.getMessage());
            rollbackConnection();
        }
    }

    // Insert GroupRecord
    public void insertGroupRecord(Group group) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO group_record VALUES (?,?)");

            ps.setString(1, group.getGroupid());
            ps.setString(2, group.getName());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println("Debug: inserGroupRecord()");
            System.out.println(EXCEPTION_TAG + " " + e.getCause() + " " + e.getMessage());
            rollbackConnection();
        }
    }

    // Insert GroupJoins
    public void insertGroupJoins(GroupMember groupMember) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO group_joins VALUES (?,?,?,?)");

            ps.setTimestamp(1, groupMember.getJoinTime());
            ps.setString(2, groupMember.getUser().getUserid());
            ps.setString(3, groupMember.getGroup().getGroupid());
            if (groupMember.getNickname() == null) {
                ps.setNull(4, java.sql.Types.CHAR);
            } else {
                ps.setString(4, groupMember.getNickname());
            }

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println("Debug: inserGroupJoins()");
            System.out.println(EXCEPTION_TAG + " " + e.getCause() + " " + e.getMessage());
            rollbackConnection();
        }
    }

    // Insert GroupCreates
    public void insertGroupCreates(Group group) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO group_creates VALUES (?,?,?)");

            ps.setTimestamp(1, group.getCreationTime());
            ps.setString(2, group.getCreator().getUserid());
            ps.setString(3, group.getGroupid());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println("Debug: insertGroupCreate()");
            System.out.println(EXCEPTION_TAG + " " + e.getCause() + " " + e.getMessage());
            rollbackConnection();
        }
    }

    // Insert GroupChat
    public void insertGroupChat(GroupChat gcr) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO group_chat VALUES (?,?,?,?)");

            ps.setTimestamp(1, gcr.getTime());
            ps.setString(2,gcr.getSender().getUser().getUserid());
            ps.setString(3, gcr.getContent());
            ps.setString(4, gcr.getGroup().getGroupid());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println("Debug: insertGroupChatRecord()");
            System.out.println(EXCEPTION_TAG + " " + e.getCause() + " " + e.getMessage());
            rollbackConnection();
        }
    }

    // Insert MeetingRecord
    public void insertMeetingRecord(MeetingRecord mr) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO meeting_record VALUES (?,?,?,?,?,?)");

            ps.setString(1, mr.getMeetingid());
            ps.setInt(2,mr.getAttendance());
            ps.setString(3, mr.getTopic());
            ps.setTimestamp(4, mr.getStartTime());
            ps.setTimestamp(5, mr.getEndTime());
            ps.setString(6, mr.getGroup().getGroupid());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println("Debug: insertMeetingRecord()");
            System.out.println(EXCEPTION_TAG + " " + e.getCause() + " " + e.getMessage());
            rollbackConnection();
        }
    }

    // Insert MeetingJoins
    public void insertMeetingJoins(MeetingRecord meeting, User user) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO meeting_joins VALUES (?,?)");

            ps.setString(1, meeting.getMeetingid());
            ps.setString(2, user.getUserid());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println("Debug: insertMeetingJoins()");
            System.out.println(EXCEPTION_TAG + " " + e.getCause() + " " + e.getMessage());
            rollbackConnection();
        }
    }


//    //  Insert task status
//    public void insertTaskStatus(TaskStatus ts) {
//        try {
//            PreparedStatement ps = connection.prepareStatement("INSERT INTO task_status VALUES (?,?)");
//
//            ps.setTimestamp(1, ts.getStime());
//            ps.setInt(2, ts.getPassed());
//
//            ps.executeUpdate();
//            connection.commit();
//
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getCause() + " " + e.getMessage());
//            rollbackConnection();
//        }
//    }
//
//    // Insert schedule record
//    public void insertScheduleRecord(ScheduleRecord sr) {
//        try {
//            PreparedStatement ps = connection.prepareStatement("INSERT INTO schedule_record VALUES (?,?,?,?)");
//
//            ps.setString(1, sr.getSid());
//            ps.setTimestamp(2, sr.getTaskStatus().getStime());
//            ps.setString(3, sr.getEvent());
//            ps.setString(4, sr.getUser().getUserid());
//
//            ps.executeUpdate();
//            connection.commit();
//
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getCause() + " " + e.getMessage());
//            rollbackConnection();
//        }
//    }
//
//    // Insert contain task
//    public void insertContainTask(ContainTask ct) {
//        try {
//            PreparedStatement ps = connection.prepareStatement("INSERT INTO contain_task VALUES (?,?,?)");
//
//            ps.setString(1, ct.getTname());
//            ps.setInt(2, ct.getPriorityVal());
//            ps.setString(3, ct.getSid());
//
//            ps.executeUpdate();
//            connection.commit();
//
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getCause() + " " + e.getMessage());
//            rollbackConnection();
//        }
//    }
//
//    // Insert MiniProgram
//    public void insertMiniProgram(MiniProgram miniProgram) {
//        try {
//            PreparedStatement ps = connection.prepareStatement("INSERT INTO mini_program VALUES (?,?,?)");
//
//            ps.setString(1, miniProgram.getPid());
//            ps.setString(2, miniProgram.getPname());
//            ps.setString(3, miniProgram.getType());
//
//            ps.executeUpdate();
//            connection.commit();
//
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getCause() + " " + e.getMessage());
//            rollbackConnection();
//        }
//    }
//
//    // Insert MiniProgramRecord
//    public void insertMiniProgramRecord(MiniProgramRecord miniProgramRecord) {
//        try {
//            PreparedStatement ps = connection.prepareStatement("INSERT INTO miniprogram_record VALUES (?,?,?)");
//
//            ps.setString(1, miniProgramRecord.getUid());
//            ps.setString(2, miniProgramRecord.getPid());
//            ps.setTimestamp(3, miniProgramRecord.getTime());
//
//            ps.executeUpdate();
//            connection.commit();
//
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getCause() + " " + e.getMessage());
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

    public void updatePassword(String userid, String password) {
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

    // get all the groups
    public Group[] getGroups() {
        ArrayList<Group> result = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT group_id FROM group_record");

            while(rs.next()) {
                result.add(getGroupByID(rs.getString("group_id")));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Debug: getGroups()");
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new Group[result.size()]);
    }

    // get all the groups that a user joined
    public Group[] getGroupsByUser(String userid) {
        ArrayList<Group> result = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT group_id FROM group_joins " +
                    "WHERE user_id = \'" + userid + "\'");

            while(rs.next()) {
                result.add(getGroupByID(rs.getString("group_id")));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Debug: getGroupsByUser()");
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new Group[result.size()]);
    }

    // get a group given the group ID
    private Group getGroupByID(String gid) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM group_record, group_creates " +
                    "WHERE group_record.group_id = \'" + gid + "\'");

            while(rs.next()) {
                Group group = new Group(
                        gid,
                        Timestamp.valueOf(rs.getString("create_time")),
                        rs.getString("group_name"),
                        getUserByID(rs.getString("user_id")));
                return group;
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Debug: getGroupByID()");
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return null;
    }

    public void updateGroupName(String gid, String name) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE group_record SET group_name = ? WHERE group_id = ?");
            ps.setString(1, name);
            ps.setString(2, gid);

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println("Debug: updateGroupName()");
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    // get all the group members in the group given the group ID
    public GroupMember[] getGroupMembers(String gid) {
        ArrayList<GroupMember> result = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM group_joins " +
                    "WHERE group_id = \'" + gid + "\'");

            while(rs.next()) {
                GroupMember member = new GroupMember(
                        Timestamp.valueOf(rs.getString("join_time")),
                        getUserByID(rs.getString("user_id")),
                        getGroupByID(rs.getString("group_id")),
                        rs.getString("nickname")
                );
                result.add(member);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Debug: getGroupMembers()");
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new GroupMember[result.size()]);
    }

    // Get a group member from a group given the group ID and the user ID
    public GroupMember getGroupMemberByID(String uid, String gid) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM group_joins " +
                    "WHERE group_id = \'" + gid + "\' " +
                    "AND user_id = \'" + uid + "\'");

            while(rs.next()) {
                GroupMember member = new GroupMember(
                        Timestamp.valueOf(rs.getString("join_time")),
                        getUserByID(rs.getString("user_id")),
                        getGroupByID(rs.getString("group_id")),
                        rs.getString("nickname"));
                return member;
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Debug: getGroupMemberByID()");
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return null;
    }

    public void updateNickname(String gid, String uid, String name) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE group_joins SET nickname = ? WHERE group_id = ? AND user_id = ?");
            ps.setString(1, name);
            ps.setString(2, gid);
            ps.setString(3, uid);

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println("Debug: updateNickname()");
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    // check if a user is the admin of the group
    public boolean isAdmin(String uid, String gid) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT group_id FROM group_admin " +
                    "WHERE user_id = \'" + uid + "\'" +
                    "AND group_id = \'" + gid + "\'");

            while(rs.next()) {
                return true;
            }

            rs.close();
            stmt.close();

            return false;
        } catch (SQLException e) {
            System.out.println("Debug: isAdmin()");
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return false;
        }
    }

    // get all the chat records in the group given the group ID
    public GroupChat[] getGroupChatHistory(String gid) {
        ArrayList<GroupChat> result = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM group_chat " +
                    "WHERE group_id = \'" + gid + "\'");

            while(rs.next()) {
                GroupChat chat = new GroupChat(
                        Timestamp.valueOf(rs.getString("time")),
                        getGroupMemberByID(rs.getString("user_id"), rs.getString("group_id")),
                        rs.getString("content"),
                        getGroupByID(rs.getString("group_id"))
                );
                result.add(chat);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Debug: getGroupChatHistory()");
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new GroupChat[result.size()]);
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


}