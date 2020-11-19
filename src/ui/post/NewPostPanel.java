package ui.post;

import main.Application;
import model.Media;
import model.SharePost;
import ui.ErrorMessage;
import ui.SuccessMessage;
import ui.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Objects;

public class NewPostPanel extends JPanel implements ActionListener {
    UI ui;

    private Integer TEXT_SPACE = 18;
    private Integer ENTRY_SPACE = 9;

    JTextArea postEntry = new JTextArea(1,10);
    JTextField mediaEntry = new JTextField(10);
    JComboBox mediaType;

    public NewPostPanel(UI ui) {
        this.ui = ui;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

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
        JLabel title = new JLabel("New Post  ");
        title.setForeground(new Color(53, 120, 139));
        title.setFont(new Font("Helvetica", Font.BOLD + Font.ITALIC, 20));

        pane.add(backButton());
        pane.add(Box.createHorizontalGlue());
        pane.add(title);

        add(pane);
        pane.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel content = new JLabel("Share your thoughts...");
        add(content);
        content.setAlignmentX(LEFT_ALIGNMENT);
        add(postEntry);
        postEntry.setAlignmentX(LEFT_ALIGNMENT);

        JPanel media = new JPanel(new FlowLayout());
        media.add(textPanel());
        media.add(entryPanel());
        add(media);
        media.setAlignmentX(LEFT_ALIGNMENT);

        JButton post = new JButton("Post!");
        post.addActionListener(this);
        post.setActionCommand("Post");
        add(post);
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

    private JPanel entryPanel() {
        JPanel entryPanel = new JPanel();
        entryPanel.setLayout(new BoxLayout(entryPanel, BoxLayout.Y_AXIS));

        String[] types = {"", "Image", "Video", "Music"};
        mediaType = new JComboBox(types);
        entryPanel.add(mediaType);
        entryPanel.add(Box.createRigidArea(new Dimension(0, ENTRY_SPACE)));
        entryPanel.add(mediaEntry);

        return entryPanel;
    }

    private JPanel textPanel() {
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel userid = generateText("Add media (optional)");
        userid.setAlignmentX(Component.RIGHT_ALIGNMENT);
        textPanel.add(userid);
        textPanel.add(Box.createRigidArea(new Dimension(0, TEXT_SPACE)));

        JLabel password = generateText("URL");
        password.setAlignmentX(Component.RIGHT_ALIGNMENT);
        textPanel.add(password);

        return textPanel;
    }

    // TODO
    // Customize text font
    private JLabel generateText(String s) {
        JLabel text = new JLabel(s);
//        text.setFont(new Font("Serif", Font.PLAIN, 14));
        return text;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Back")) {
            ui.switchPanel("Post");
        } else {
            String content = postEntry.getText();
            String type = (String) Objects.requireNonNull(mediaType.getSelectedItem());
            String url = mediaEntry.getText();

            Application application = ui.getApplication();

            if (content.isEmpty()) {
                new ErrorMessage("You can not post an empty message.");
            } else {
                SharePost post;
                Media media = null;
                int numPost = application.getPosts().length + 1;

                if (!(url.isEmpty()) && !(type.isEmpty())) {
                    try {
                        new URL(url);

                        if (application.getMediaByUrl(url) != null) {
                            media = application.getMediaByUrl(url);
                        } else {
                            media = new Media(type, url);
                            application.addMedia(media);
                        }
                    } catch (MalformedURLException exception) {
                        new ErrorMessage("The url you add is invalid.");
                        return;
                    }
                }

                while (application.getPostByID("P" + numPost) != null) {
                    numPost++;
                }
                String pid = "P" + numPost;
                post = new SharePost(
                        pid,
                        new Timestamp(System.currentTimeMillis()),
                        content,
                        application.getCurrentUser(),
                        media);
                application.addPost(post);
                new SuccessMessage("The post is shared!");
                ui.switchPanel("Post");
            }
        }
    }
}
