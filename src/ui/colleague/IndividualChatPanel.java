package ui.colleague;

import main.Application;
import model.IndividualChat;
import model.User;
import ui.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;

public class IndividualChatPanel extends JPanel {
    UI ui;
    IndividualChatWindow window;
    String uid1; // current user
    String uid2; // another colleague

    public IndividualChatPanel(UI ui, IndividualChatWindow window, String uid1, String uid2) {
        this.ui = ui;
        this.uid1 = uid1;
        this.uid2 = uid2;
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
        User user = application.getUserByID(uid2);
        JLabel title = new JLabel(user.getName());
        title.setForeground(new Color(53, 120, 139));
        title.setFont(new Font("Helvetica", Font.BOLD + Font.ITALIC, 20));

        pane.add(Box.createHorizontalGlue());
        pane.add(title);

        add(pane);
        pane.setAlignmentX(Component.LEFT_ALIGNMENT);

        JScrollPane scrPane = chatHistory();
        add(scrPane);
        scrPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel entry = new IndividualChatEntry(this);
        add(entry);
        entry.setAlignmentX(LEFT_ALIGNMENT);
    }

    private JScrollPane chatHistory() {
        JPanel panel = new JPanel();
        JScrollPane chatHistory = new JScrollPane(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Application app = ui.getApplication();
        IndividualChat[] history = app.getIndividualChatHistory(uid1, uid2);

        for (int i = 0; i < history.length; i++) {
            JPanel chat = new IndividualChatRecord(history[i], uid1);
            panel.add(chat);

            chat.setAlignmentX(LEFT_ALIGNMENT);
            chat.setAlignmentY(TOP_ALIGNMENT);

            chat.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        return chatHistory;
    }

    public void addEntry(String content) {
        Application application = ui.getApplication();

        IndividualChat record = new IndividualChat(
                uid1,
                uid2,
                uid1,
                application.getUserByID(uid1).getName(),
                content,
                new Timestamp(System.currentTimeMillis()));

        System.out.println(record.getContent());
        application.addIndividualChar(record);
        window.setContentPane(new IndividualChatPanel(ui, window, uid1, uid2));
    }
}
