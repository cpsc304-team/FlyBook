package main;

import model.TimeZone;
import model.User;
import database.DatabaseConnection;
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
//        // TODO: test
//        System.out.println("success!");
        app.start();
    }

    public String getCurrentUser() {
        return currentUser;
    }

    // Start the program by opening the application ui frame
    private void start() {
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

    public User[] getUserList() {
        return dbConnection.getUser();
    }

    public String[] getCities() { return dbConnection.getTimeZoneCities(); }
}
