package main;

import model.*;
import database.DatabaseConnection;
import model.group.Group;
import model.group.GroupChat;
import model.group.GroupMember;
import model.post.Media;
import model.post.SharePost;
import model.user.TimeZone;
import model.user.User;
import ui.ErrorMessage;
import ui.SuccessMessage;
import ui.UI;

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
        // TODO: test
//        System.out.println("success!"); // for database setup

        app.start();
    }

    public String getCurrentUserID() {
        return currentUser;
    }

    // Start the program by opening the application ui frame
    private void start() {
        // TODO: test
//        currentUser = "0001";
//        Group[] groups = getCurrentUsersGroups();
//        for (int i = 0; i < groups.length; i++) {
//            Group group = groups[i];
//            System.out.println(group.getGroupid() + group.getName() + group.getCreator().getName() + group.getCreationTime().toString());
//        }
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
}
