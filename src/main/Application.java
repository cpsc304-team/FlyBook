package main;

import model.IndividualChat;
import database.DatabaseConnection;
import model.TimeZone;
import model.User;
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
//        dbConnection.print();

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
        dbConnection.resetPassword(currentUser, password);
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
}
