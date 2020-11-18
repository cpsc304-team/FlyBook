package ui.post;

import main.Application;
import model.Media;
import model.SharePost;
import ui.UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class IndividualPostRecord extends JPanel implements ActionListener {
    UI ui;
    IndividualPostWindow window;
    SharePost post;

    public IndividualPostRecord(UI ui, IndividualPostWindow window, SharePost post) {
        this.ui = ui;
        this.window = window;
        this.post = post;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.LINE_AXIS));

        JLabel sender = new JLabel(post.getUser().getName());
        sender.setFont(new Font("Helvetica", Font.BOLD, 15));
        JLabel time = new JLabel(post.getTime().toString());
        time.setForeground(Color.GRAY);
//        time.setFont(new Font("Helvetica", Font.ITALIC, 8));

        info.add(sender);
        sender.setAlignmentY(BOTTOM_ALIGNMENT);
        info.add(Box.createRigidArea(new Dimension(5, 0)));
        info.add(time);
        time.setAlignmentY(BOTTOM_ALIGNMENT);

        JPanel text = textPanel();

        add(info);
        info.setAlignmentX(LEFT_ALIGNMENT);
        add(text);
        text.setAlignmentX(LEFT_ALIGNMENT);
    }

    private JPanel textPanel() {
        JPanel text = new JPanel();
        JLabel content = new JLabel(post.getContent());
        text.setLayout(new BoxLayout(text, BoxLayout.PAGE_AXIS));
        text.add(content);
        content.setAlignmentX(LEFT_ALIGNMENT);

        // Show media
        Media media = post.getMedia();
        if (media != null) {
            if (media.getMediaType().equals("Image")) {
                URL url = null;
                try {
                    url = new URL(post.getMedia().getUrl());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                Image image = null;
                BufferedImage bimg = null;
                try {
                    image = ImageIO.read(url);
                    bimg = ImageIO.read(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(350, bimg.getHeight()/(bimg.getWidth()/300), Image.SCALE_DEFAULT));
                JLabel imageLabel = new JLabel(imageIcon);
                text.add(imageLabel);
                imageLabel.setAlignmentX(LEFT_ALIGNMENT);
            } else {
                JLabel hyperlink = new JLabel(post.getMedia().getMediaType());
                hyperlink.setForeground(Color.BLUE.darker());
                hyperlink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                String url = post.getMedia().getUrl();
                hyperlink.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            Desktop.getDesktop().browse(new URI(url));
                        } catch (IOException | URISyntaxException e1) {
                            e1.printStackTrace();
                        }
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        hyperlink.setText("<html><a href=''>" + post.getMedia().getMediaType() + "</a></html>");
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        hyperlink.setText(post.getMedia().getMediaType());
                    }
                });
                text.add(hyperlink);
                hyperlink.setAlignmentX(LEFT_ALIGNMENT);
            }
        }

        // Show delete button if the sender is the current user
        Application application = ui.getApplication();
        if (post.getUser().getUserid().equals(application.getCurrentUserID())) {
            text.add(Box.createRigidArea(new Dimension(0, 2)));
            JButton delete = new JButton("[Delete]");
            delete.setForeground(Color.GRAY);
            delete.addActionListener(this);
            delete.setOpaque(false);
            delete.setContentAreaFilled(false);
            delete.setBorderPainted(false);
            text.add(delete);
            delete.setAlignmentX(LEFT_ALIGNMENT);
        }

        return text;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Application application = ui.getApplication();
        application.deletePost(post);
        if (window != null) {
            window.refresh();
        } else {
            ui.switchPanel("Post");
        }
    }
}
