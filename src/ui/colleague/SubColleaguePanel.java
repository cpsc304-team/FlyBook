package ui.colleague;

import model.User;
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
        JLabel name = new JLabel(user.getName());
//        name.setFont(new Font("Serif", Font.PLAIN, 20));
        userInfo.add(name);
        userInfo.add(Box.createRigidArea(new Dimension(5, 0)));
        JLabel city = new JLabel(user.getTimezone().getCity() + " | " + user.getTimezone().getZoneCode());
//        name.setFont(new Font("Serif", Font.PLAIN, 14));
        userInfo.add(city);

        infoPanel.add(userInfo);
        userInfo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel email = new JLabel(user.getEmail());
        infoPanel.add(email);
        email.setAlignmentX(Component.LEFT_ALIGNMENT);

        return infoPanel;
    }

    private JPanel buttonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));

        if (!(currentUser.equals(user.getUserid()))) {
            JButton chat = generateButton("Chat");
            buttonPanel.add(chat);
            chat.setAlignmentX(Component.CENTER_ALIGNMENT);
        } else {
            JLabel me = new JLabel("Me");
            buttonPanel.add(me);
            me.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        JButton posts = generateButton("Posts");
        buttonPanel.add(posts);
        posts.setAlignmentX(Component.CENTER_ALIGNMENT);

        return buttonPanel;
    }

    // TODO
    // Customize button
    private JButton generateButton(String s) {
        JButton button = new JButton(s);

        button.setActionCommand(s);
        button.addActionListener(this);

        return button;
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
