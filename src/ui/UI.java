package ui;

import main.Application;
import ui.colleague.ColleaguePanel;
import ui.colleague.IndividualChatPanel;
import ui.colleague.IndividualChatWindow;
import ui.user.AccountPanel;
import ui.user.LoginPanel;
import ui.user.PasswordPanel;
import ui.user.RegisterPanel;

import javax.swing.*;
import java.awt.*;

// This class is used to handle the transition between difference interfaces
public class UI extends JFrame {
    Application application;

    public UI(Application application) {
        // TODO: Change the application name
        super("Application");
        this.application = application;
    }

    // Show the frame and the login panel as the first panel
    public void showFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(430, 600));

        // Start with the login panel
        JPanel loginPane = new LoginPanel(this);

        // TODO: delete
        setContentPane(loginPane);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // Switch to different panels according to the keyword
    public void switchPanel(String s) {
        if (s.equals("Register")) {
            setContentPane(new RegisterPanel(this));
        } else if (s.equals("Login")) {
            setContentPane(new LoginPanel(this));
        } else if (s.equals("Main")) {
            setContentPane(new MainPanel(this));
        } else if (s.equals("Colleague")) {
            setContentPane(new ColleaguePanel(this));
        } else if (s.equals("Group")) {

        } else if (s.equals("Post")) {

        } else if (s.equals("Mini-Program")) {

        } else if (s.equals("Meeting")) {

        } else if (s.equals("Schedule")) {

        } else if (s.equals("Account")) {
            setContentPane(new AccountPanel(this, application.getCurrentUser()));
        } else if (s.equals("Reset Password")) {
            setContentPane(new PasswordPanel(this));
        } else if (s.equals("Change Profile")) {

        }
        revalidate();
    }

    public Application getApplication() {
        return application;
    }
}
