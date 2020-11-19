package ui;

import main.Application;
import ui.colleague.ColleaguePanel;
import ui.colleague.IndividualChatPanel;
import ui.colleague.IndividualChatWindow;
import ui.post.NewPostPanel;
import ui.post.PostPanel;
import ui.user.*;

import javax.swing.*;
import java.awt.*;

// This class is used to handle the transition between difference interfaces
public class UI extends JFrame {
    Application application;

    public UI(Application application) {
        // TODO: Change the application name
        super("FlyBook");
        this.application = application;
    }

    // Show the frame and the login panel as the first panel
    public void showFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(430, 600));

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
            setContentPane(new PostPanel(this));
        } else if (s.equals("Mini-Program")) {

        } else if (s.equals("Meeting")) {

        } else if (s.equals("Schedule")) {

        } else if (s.equals("Account")) {
            setContentPane(new AccountPanel(this, application.getCurrentUser()));
        } else if (s.equals("Reset Password")) {
            setContentPane(new PasswordPanel(this));
        } else if (s.equals("Change Profile")) {
            setContentPane(new EditProfilePanel(this));
        } else if (s.equals("New Post")) {
            setContentPane(new NewPostPanel(this));
        }
        revalidate();
    }

    public Application getApplication() {
        return application;
    }

    public JButton setbackButton(JButton backbtn) {
//        ImageIcon i1 = new ImageIcon("images/Back Button.png");
//        ImageIcon i2 = new ImageIcon(i1.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
//        JButton back = new JButton(i2);

        backbtn.setOpaque(false);
        backbtn.setContentAreaFilled(false);
        backbtn.setBorderPainted(false);
        backbtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        return backbtn;
    }
}
