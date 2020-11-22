package ui.colleague;

import main.Application;
import model.user.User;
import ui.UI;
import ui.utilities.Header;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColleaguePanel extends JPanel implements ActionListener {
    UI ui;

    public ColleaguePanel(UI ui) {
        this.ui = ui;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(new Header(ui, "Colleague"));

        JScrollPane scrPane = userList();
        add(scrPane);
        scrPane.setAlignmentX(Component.CENTER_ALIGNMENT);

    }



    private JScrollPane userList() {
        JPanel panel = new JPanel();
        JScrollPane userList = new JScrollPane(panel);
        panel.setLayout(new GridLayout(0,1));

        Application app = ui.getApplication();
        User[] users = app.getUserList();
        String currentUser = app.getCurrentUserID();

        for (int i = 0; i < users.length; i++) {
            JPanel user = new SubColleaguePanel(ui, users[i], currentUser);
            panel.add(user);
        }

        return userList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ui.switchPanel("Main");
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ui.generateBackground(g);
    }
}
