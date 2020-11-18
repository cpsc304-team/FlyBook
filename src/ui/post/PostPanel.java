package ui.post;

import main.Application;
import model.SharePost;
import model.User;
import ui.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PostPanel extends JPanel implements ActionListener {
    UI ui;

    public PostPanel(UI ui) {
        this.ui = ui;
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
        JLabel title = new JLabel("Post  ");
        title.setForeground(new Color(53, 120, 139));
        title.setFont(new Font("Helvetica", Font.BOLD + Font.ITALIC, 20));

        pane.add(backButton());
        pane.add(Box.createHorizontalGlue());
        pane.add(title);

        add(pane);
        pane.setAlignmentX(Component.LEFT_ALIGNMENT);

        JScrollPane scrPane = postList();
        add(scrPane);
        scrPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton newPost = new JButton("Write a post");
        newPost.addActionListener(this);
        newPost.setActionCommand("Post");
        add(newPost);
    }

    private JButton backButton() {
//        ImageIcon i1 = new ImageIcon("images/Back Button.png");
//        ImageIcon i2 = new ImageIcon(i1.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
//        JButton back = new JButton(i2);
        JButton back = new JButton("←");
        back.addActionListener(this);
        back.setActionCommand("Back");
        back.setOpaque(false);
        back.setContentAreaFilled(false);
        back.setBorderPainted(false);
        back.setAlignmentX(Component.LEFT_ALIGNMENT);
        return back;
    }

    private JScrollPane postList() {
        JPanel panel = new JPanel();
        JScrollPane postList = new JScrollPane(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Application app = ui.getApplication();
        SharePost[] posts = app.getPosts();

        for (int i = posts.length - 1; i >= 0; i--) {
            JPanel post = new IndividualPostRecord(ui, null, posts[i]);
            panel.add(post);

            post.add(Box.createRigidArea(new Dimension(0, 20)));

            post.setAlignmentX(LEFT_ALIGNMENT);
            post.setAlignmentY(TOP_ALIGNMENT);
        }

        return postList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Back")) {
            ui.switchPanel("Main");
        } else {
            ui.switchPanel("New Post");
        }
    }
}
