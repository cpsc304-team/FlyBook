package ui.group;

import main.Application;
import model.IndividualChat;
import model.group.Group;
import model.group.GroupChat;
import model.user.User;
import ui.UI;
import ui.colleague.IndividualChatRecord;
import ui.colleague.IndividualChatWindow;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;

public class GroupChatPanel extends JPanel {
    UI ui;
    GroupChatWindow window;
    Group group;

    public GroupChatPanel(UI ui, GroupChatWindow window, Group group) {
        this.ui = ui;
        this.group = group;
        this.window = window;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel pane = new JPanel();
//        JPanel pane = new JPanel() {
//            @Override
//            protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                try {
//                    Image i = ImageIO.read(new File("images/Background2.png"));
//                    g.drawImage(i, 0, 0, null);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
        pane.setLayout(new BoxLayout(pane, BoxLayout.LINE_AXIS));
        pane.setOpaque(false);

        Application application = ui.getApplication();
        JLabel title = new JLabel(group.getName());
        title.setForeground(new Color(53, 120, 139));
        title.setFont(new Font("Helvetica", Font.BOLD + Font.ITALIC, 20));

        pane.add(Box.createHorizontalGlue());
        pane.add(title);

        add(pane);
        pane.setAlignmentX(Component.LEFT_ALIGNMENT);

        JScrollPane scrPane = chatHistory();
        add(scrPane);
        scrPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel entry = new GroupChatEntry(this);
        add(entry);
        entry.setAlignmentX(LEFT_ALIGNMENT);
    }

    private JScrollPane chatHistory() {
        JPanel panel = new JPanel();
        JScrollPane chatHistory = new JScrollPane(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Application app = ui.getApplication();
        GroupChat[] history = app.getGroupChatHistory(group.getGroupid());

        for (int i = 0; i < history.length; i++) {
            JPanel chat = new GroupChatRecord(history[i], app.getCurrentUser().getUserid());
            panel.add(chat);

            chat.setAlignmentX(LEFT_ALIGNMENT);
            chat.setAlignmentY(TOP_ALIGNMENT);

            chat.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        return chatHistory;
    }

    public void addEntry(String content) {
        Application application = ui.getApplication();

        GroupChat record = new GroupChat(
                new Timestamp(System.currentTimeMillis()),
                application.getGroupMemberByID(application.getCurrentUser().getUserid(), group.getGroupid()),
                content,
                group);

        application.addGroupChat(record);
        window.refresh(ui, group);
    }
}
