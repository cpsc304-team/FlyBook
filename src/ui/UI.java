package ui;

import main.Application;
import ui.colleague.ColleaguePanel;
import ui.group.GroupListPanel;
import ui.meeting.MeetingListPanel;
import ui.post.NewPostPanel;
import ui.post.PostPanel;
import ui.schedule.SchedulePanel;
import ui.user.*;

import javax.swing.*;
import java.awt.*;

// This class is used to handle the transition between difference interfaces
public class UI extends JFrame {
    Application application;

    public UI(Application application) {
        super("FlyBook");
        this.application = application;
    }

    // Show the frame and the login panel as the first panel
    public void showFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(430, 600));

        // TODO: delete testing statements below
//        setContentPane(new AccountPanel(this, application.getCurrentUser()));

        // Start with the login panel
        JPanel loginPane = new LoginPanel(this);
        setContentPane(loginPane);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // Switch to different panels according to the keyword
    public void switchPanel(String s) {
        switch (s) {
            case "Register":
                setContentPane(new RegisterPanel(this));
                break;
            case "Login":
                setContentPane(new LoginPanel(this));
                break;
            case "Main":
                setContentPane(new MainPanel(this));
                break;
            case "Colleague":
                setContentPane(new ColleaguePanel(this));
                break;
            case "Group":
                setContentPane(new GroupListPanel(this));
                break;
            case "Post":
                setContentPane(new PostPanel(this));
                break;
            case "Meeting":
                setContentPane(new MeetingListPanel(this));
                break;
            case "Schedule":
                setContentPane(new SchedulePanel(this));
                break;
            case "Account":
                setContentPane(new AccountPanel(this, application.getCurrentUser()));
                break;
            case "Reset Password":
                setContentPane(new PasswordPanel(this));
                break;
            case "New Post":
                setContentPane(new NewPostPanel(this));
                break;
        }
        revalidate();
    }

    public Application getApplication() {
        return application;
    }
}
