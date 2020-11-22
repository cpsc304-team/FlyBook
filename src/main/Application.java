package main;

import model.*;
import database.DatabaseConnection;
import model.group.Group;
import model.group.GroupChat;
import model.group.GroupMember;
import model.meeting.MeetingRecord;
import model.post.Media;
import model.post.SharePost;
import model.schedule.ScheduleRecord;
import model.schedule.Task;
import model.user.TimeZone;
import model.user.User;
import ui.utilities.ErrorMessage;
import ui.utilities.SuccessMessage;
import ui.UI;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Application {
    private DatabaseConnection dbConnection;
    private UI ui;
    private String currentUser; // user ID of the current user

    public Application() {
        dbConnection = new DatabaseConnection();
    }

    public static void main(String args[]) {
        Application app = new Application();
        app.oracleLogin();

        app.start();
    }

    public String getCurrentUserID() {
        return currentUser;
    }

    // Start the program by opening the application ui frame
    private void start() {
        // TODO: test
//        currentUser = "0001";
//        System.out.println("success!");

        ui = new UI(this);
        ui.showFrame();
    }

    // Login into the Oracle server
    private void oracleLogin() {
        boolean connect = dbConnection.login();

        if (connect) {
            // drop tables
            dbConnection.dropTables();

            // set up database
            dbConnection.databaseSetUp();

            // load pre-set data
            dbConnection.loadData();
        } else {
            System.out.println("Login failed.");
        }
    }

    // Check if the user can login
    public void userLogin(String userid, String password) {
        if (dbConnection.containLoginInfo(userid, password)) {
            User user = dbConnection.getUserByID(userid);
            new SuccessMessage("Welcome, <" + user.getName() + ">!");
            currentUser = userid;
            ui.switchPanel("Main");
        } else {
            new ErrorMessage("User ID or password is incorrect.");
        }
    }

    public void userRegister(String userid, String password, String name, String city, String email) {
        if (dbConnection.userExist(userid)) {
            new ErrorMessage("This user ID has been used.");
        } else {
            TimeZone timezone = dbConnection.getTimeZoneByCity(city);
            User user = new User(userid, password, name, timezone, email);
            dbConnection.insertUser(user);
            new SuccessMessage("You registered an account successfully!");
            ui.switchPanel("Login");
        }
    }

    public User getCurrentUser() { return dbConnection.getUserByID(currentUser); }

    public User getUserByID(String uid) { return dbConnection.getUserByID(uid); }

    public User[] getUserList() {
        return dbConnection.getUser();
    }

    public String[] getCities() { return dbConnection.getTimeZoneCities(); }

    public void resetPassword(String password) {
        dbConnection.updatePassword(currentUser, password);
        new SuccessMessage("You reset your password successfully!");
    }

    public void deleteAccount() {
        dbConnection.deleteAccount(currentUser);
        new SuccessMessage("The account is deleted. You will return back to the log in menu.");
    }

    public IndividualChat[] getIndividualChatHistory(String uid1, String uid2) {
        return dbConnection.getIndividualChatHistory(uid1, uid2);
    }

    public void addIndividualChat(IndividualChat record) {
        dbConnection.insertIndividualChat(record);
    }

    public SharePost[] getIndividualPost(String uid) {
        return dbConnection.getIndividualPost(uid);
    }

    public SharePost[] getPosts() {
        return dbConnection.getPosts();
    }

    public SharePost getPostByID(String pid) {
        return dbConnection.getPostByID(pid);
    }

    public void deletePost(SharePost post) {
        dbConnection.deletePost(post);
    }

    public Media[] getMediaList() {
        return dbConnection.getMedia();
    }

    public Media getMediaByUrl(String url) {
        return dbConnection.getMediaByUrl(url);
    }

    public void addPost(SharePost post) {
        dbConnection.insertSharePost(post);
    }

    public void addMedia(Media media) {
        dbConnection.insertMedia(media);
    }

    public void updateUser(String name, String city, String email) {
        if (!(name.isEmpty())) {
            dbConnection.updateUserName(currentUser, name);
        }
        if (!(city.isEmpty())) {
            dbConnection.updateUserCity(currentUser, city);
        }
        if (!(email.isEmpty())) {
            dbConnection.updateUserEmail(currentUser, email);
        }
    }

    public Group[] getCurrentUsersGroups() {
        return dbConnection.getGroupsByUser(currentUser);
    }

    public boolean isAdmin(String gid) {
        return dbConnection.isAdmin(currentUser, gid);
    }

    public boolean isMember(String gid) {
        GroupMember[] members = dbConnection.getGroupMembers(gid);
        for (int i = 0; i < members.length; i++) {
            if (members[i].getUser().getUserid().equals(currentUser)) {
                return true;
            }
        }
        return false;
    }

    public Group[] getGroups() {
        return dbConnection.getGroups();
    }

    public GroupChat[] getGroupChatHistory(String gid) {
        return dbConnection.getGroupChatHistory(gid);
    }

    public GroupMember getGroupMemberByID(String userid, String groupid) {
        return dbConnection.getGroupMemberByID(userid, groupid);
    }

    public void addGroupChat(GroupChat record) {
        dbConnection.insertGroupChat(record);
    }

    public void joinGroup(GroupMember member) {
        dbConnection.insertGroupJoins(member);
    }

    public void updateGroupName(String gid, String name) {
        dbConnection.updateGroupName(gid, name);
    }

    public void updateNickname(String gid, String name) {
        dbConnection.updateNickname(gid, currentUser, name);
    }

    public MeetingRecord[] getPastMeetingsByID() {
        return dbConnection.getPastMeetingsByID(currentUser);
    }

    public MeetingRecord[] getCurrentMeetingsByID() {
        return dbConnection.getCurrentMeetingsByID(currentUser);
    }

    public void joinMeeting(MeetingRecord meeting) {
        if (!(dbConnection.hasJoined(meeting.getMeetingid(), currentUser))) {
            dbConnection.insertMeetingJoins(meeting, getCurrentUser());
        }
    }

    public void endMeeting(MeetingRecord meeting) {
        Integer attendance = dbConnection.countAttendance(meeting.getMeetingid());
        dbConnection.updateMeetingInfo(new Timestamp(System.currentTimeMillis()), attendance, meeting.getMeetingid());
    }

    public MeetingRecord getMeetingByID(String mid) {
        return dbConnection.getMeetingByID(mid);
    }

    public Group[] getAdminGroups() {
        return dbConnection.getAdminGroups(currentUser);
    }

    public int countMeetings() {
        return dbConnection.countMeetings();
    }

    public Group getGroupByID(String gid) {
        return dbConnection.getGroupByID(gid);
    }

    public void addMeeting(MeetingRecord meeting) {
        dbConnection.insertMeetingRecord(meeting);
    }

    public ScheduleRecord[] getSchedulesByID() {
        return dbConnection.getSchedulesByID(currentUser);
    }

    public ScheduleRecord[] getSchedulesThisWeek() {
        Calendar cal = Calendar.getInstance();
        String today = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()) + " 00:00:00";
        cal.add(Calendar.DATE, 7);
        String after = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()) + " 00:00:00";
        return dbConnection.getSchedulesWithinPeriod(Timestamp.valueOf(today), Timestamp.valueOf(after), currentUser);
    }

    public ScheduleRecord[] getSchedulesToday() {
        Calendar cal = Calendar.getInstance();
        String today = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()) + " 00:00:00";
        cal.add(Calendar.DATE, 1);
        String tomorrow = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()) + " 00:00:00";
        return dbConnection.getSchedulesWithinPeriod(Timestamp.valueOf(today), Timestamp.valueOf(tomorrow), currentUser);
    }

    public ScheduleRecord[] getSchedulesPassed() {
        Calendar cal = Calendar.getInstance();
        String today = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()) + " 00:00:00";
        return dbConnection.getSchedulesPassed(Timestamp.valueOf(today), currentUser);
    }

    public Task[] getTasksBySchedule(ScheduleRecord schedule) {
        return dbConnection.getTasksBySchedule(schedule.getScheduleid());
    }

    public void updateTaskStatus(Task task, int i) {
        dbConnection.updateTaskStatus(task, i);
    }

    public int countSchedules() {
        return dbConnection.countSchedules();
    }

    public void addSchedule(ScheduleRecord schedule) {
        dbConnection.insertScheduleRecord(schedule);
    }
}
