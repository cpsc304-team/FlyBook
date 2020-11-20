package ui.group;

import model.IndividualChat;
import model.group.GroupChat;

import javax.swing.*;
import java.awt.*;

public class GroupChatRecord extends JPanel {
    public GroupChatRecord(GroupChat chat, String currentUser) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.LINE_AXIS));

        JLabel content = new JLabel(chat.getContent());

        String name = null;
        if (chat.getSender().getNickname() != null) {
            name = chat.getSender().getNickname();
        } else {
            name = chat.getSender().getUser().getName();
        }
        JLabel sender = new JLabel(name);
        sender.setFont(new Font("Helvetica", Font.BOLD, 15));
        if (currentUser.equals(chat.getSender().getUser().getUserid())) {
            sender.setForeground(Color.BLUE);
        }
        JLabel time = new JLabel(chat.getTime().toString());
        time.setForeground(Color.GRAY);
//        time.setFont(new Font("Helvetica", Font.ITALIC, 8));

        info.add(sender);
        sender.setAlignmentY(BOTTOM_ALIGNMENT);
        info.add(Box.createRigidArea(new Dimension(5, 0)));
        info.add(time);
        time.setAlignmentY(BOTTOM_ALIGNMENT);

        JPanel text = new JPanel();
        text.setLayout(new BoxLayout(text, BoxLayout.LINE_AXIS));
        text.add(content);
        content.setAlignmentX(LEFT_ALIGNMENT);

        add(info);
        info.setAlignmentX(LEFT_ALIGNMENT);
        add(text);
        text.setAlignmentX(LEFT_ALIGNMENT);
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

}
