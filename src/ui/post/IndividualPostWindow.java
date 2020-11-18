package ui.post;

import main.Application;
import model.IndividualChat;
import model.SharePost;
import model.User;
import ui.UI;
import ui.colleague.IndividualChatEntry;
import ui.colleague.IndividualChatPanel;
import ui.colleague.IndividualChatRecord;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// This panel shows all the posts posted by a specific user
public class IndividualPostWindow extends JFrame implements ActionListener {
    UI ui;
    String uid;

    public IndividualPostWindow(UI ui, String uid) {
        this.ui = ui;
        this.uid = uid;

        setPreferredSize(new Dimension(430, 600));

        setContentPane(IndividualPostPanel());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    private JPanel IndividualPostPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

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
        User user = application.getUserByID(uid);
        JLabel title = new JLabel(user.getName());
        title.setForeground(new Color(53, 120, 139));
        title.setFont(new Font("Helvetica", Font.BOLD + Font.ITALIC, 20));

        pane.add(Box.createHorizontalGlue());
        pane.add(title);

        panel.add(pane);
        pane.setAlignmentX(Component.LEFT_ALIGNMENT);

        JScrollPane scrPane = postList();
        panel.add(scrPane);
        scrPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        return panel;
    }

    private JScrollPane postList() {
        JPanel panel = new JPanel();
        JScrollPane postList = new JScrollPane(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Application app = ui.getApplication();
        SharePost[] posts = app.getIndividualPost(uid);

        for (int i = posts.length - 1; i >= 0; i--) {
            JPanel post = new IndividualPostRecord(ui, this, posts[i]);
            panel.add(post);

            post.add(Box.createRigidArea(new Dimension(0, 20)));

            post.setAlignmentX(LEFT_ALIGNMENT);
            post.setAlignmentY(TOP_ALIGNMENT);
        }

        return postList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void refresh() {
        setContentPane(IndividualPostPanel());
        validate();
    }
}
