package ui.post;

import main.Application;
import model.post.SharePost;
import ui.UI;
import ui.utilities.Header;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PostPanel extends JPanel implements ActionListener {
    UI ui;

    public PostPanel(UI ui) {
        this.ui = ui;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        add(new Header(ui, "Posts"));

        JScrollPane scrPane = postList();
        add(scrPane);
        scrPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton newPost = ui.generateButton("Write a post",this);
        newPost.setActionCommand("Post");
        add(newPost);
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
        if (e.getActionCommand().equals("back")) {
            ui.switchPanel("Main");
        } else if (e.getActionCommand().equals("Write a post")) {
            ui.switchPanel("New Post");
        }
    }
}
