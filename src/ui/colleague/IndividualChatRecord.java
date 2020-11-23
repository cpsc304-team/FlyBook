package ui.colleague;

import model.user.IndividualChat;

import javax.swing.*;
import java.awt.*;

public class IndividualChatRecord extends JPanel {
    public IndividualChatRecord(IndividualChat chat, String currentUser) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.LINE_AXIS));

        JLabel content = new JLabel(chat.getContent());
        JLabel sender = new JLabel(chat.getSender().getName());
        sender.setFont(new Font("Helvetica", Font.BOLD, 15));
        if (currentUser.equals(chat.getSender().getUserid())) {
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
