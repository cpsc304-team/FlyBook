package ui.colleague;

import model.user.User;
import ui.UI;
import ui.post.IndividualPostWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SubColleaguePanel extends JPanel implements ActionListener {
    UI ui;
    User user;
    String currentUser;

    public SubColleaguePanel(UI ui, User user, String currentUser) {
        this.ui = ui;
        this.user = user;
        this.currentUser = currentUser;

        setLayout(new FlowLayout());

        JPanel info = infoPanel();
        add(info);
        info.setAlignmentY(Component.CENTER_ALIGNMENT);

        JPanel button = buttonPanel();
        add(button);
        button.setAlignmentY(Component.CENTER_ALIGNMENT);
    }

    private JPanel infoPanel() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JPanel userInfo = new JPanel();
        userInfo.setLayout(new FlowLayout());
        JLabel name = ui.generateLabel(user.getName());
        userInfo.add(name);
        userInfo.add(Box.createRigidArea(new Dimension(5, 0)));
        JLabel city = ui.generateLabel(user.getTimezone().getCity() + " | " + user.getTimezone().getZoneCode());
        userInfo.add(city);

        infoPanel.add(userInfo);
        userInfo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel email = ui.generateLabel(user.getEmail());
        infoPanel.add(email);
        email.setAlignmentX(Component.LEFT_ALIGNMENT);

        return infoPanel;
    }

    private JPanel buttonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));

        if (!(currentUser.equals(user.getUserid()))) {
            JButton chat = ui.generateButton("Chat",this);
            buttonPanel.add(chat);
            chat.setAlignmentX(Component.CENTER_ALIGNMENT);
        } else {
            JLabel me = ui.generateLabel("Me");
            buttonPanel.add(me);
            me.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        JButton posts = ui.generateButton("Posts",this);
        buttonPanel.add(posts);
        posts.setAlignmentX(Component.CENTER_ALIGNMENT);

        return buttonPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Posts")) {
            new IndividualPostWindow(ui, user.getUserid());
        } else {
            new IndividualChatWindow(ui, currentUser, user.getUserid());
        }
    }
}
