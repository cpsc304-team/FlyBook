package ui.user;

import main.Application;
import model.user.User;
import ui.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountPanel extends JPanel implements ActionListener {
    UI ui;
    User user;

    private Integer TEXT_SPACE = 18;

    public AccountPanel(UI ui, User user) {
        this.ui = ui;
        this.user = user;

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
        JLabel title = new JLabel("Account  ");
        title.setForeground(new Color(53, 120, 139));
        title.setFont(new Font("Helvetica", Font.BOLD + Font.ITALIC, 20));

        pane.add(backButton());
        pane.add(Box.createHorizontalGlue());
        pane.add(title);

        add(pane);
        pane.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel info = new JPanel(new FlowLayout());
        info.add(labelPanel());
        info.add(infoPanel());

        // a sub-panel contains the buttons
        JPanel button = new JPanel();
        button.setLayout(new FlowLayout());
        button.add(generateButton("Reset Password"));
        button.add(generateButton("Change Profile"));
        button.add(generateButton("Log Out"));
        button.add(generateButton("Delete Account"));

        add(info);
        info.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(button);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
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

    // TODO
    // Customize button
    private JButton generateButton(String s) {
        JButton button = new JButton(s);

        button.setActionCommand(s);
        button.addActionListener(this);

        return button;
    }

    private JPanel labelPanel() {
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.PAGE_AXIS));

        JLabel userid = generateText("User ID");
        labelPanel.add(userid);
        userid.setAlignmentX(Component.RIGHT_ALIGNMENT);
        labelPanel.add(Box.createRigidArea(new Dimension(0, TEXT_SPACE)));

        JLabel name = generateText("Name");
        userid.setAlignmentX(Component.RIGHT_ALIGNMENT);
        labelPanel.add(name);
        labelPanel.add(Box.createRigidArea(new Dimension(0, TEXT_SPACE)));

        JLabel city = generateText("City");
        userid.setAlignmentX(Component.RIGHT_ALIGNMENT);
        labelPanel.add(city);
        labelPanel.add(Box.createRigidArea(new Dimension(0, TEXT_SPACE)));

        JLabel timeZone = generateText("Time Zone");
        userid.setAlignmentX(Component.RIGHT_ALIGNMENT);
        labelPanel.add(timeZone);
        labelPanel.add(Box.createRigidArea(new Dimension(0, TEXT_SPACE)));

        JLabel email = generateText("Email");
        userid.setAlignmentX(Component.RIGHT_ALIGNMENT);
        labelPanel.add(email);

        return labelPanel;
    }

    private JLabel generateText(String s) {
        JLabel text = new JLabel(s);
//        text.setFont(new Font("Serif", Font.PLAIN, 14));
        return text;
    }

    private JPanel infoPanel() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JLabel userid = generateText(user.getUserid());
        userid.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(userid);
        infoPanel.add(Box.createRigidArea(new Dimension(0, TEXT_SPACE)));

        JLabel name = generateText(user.getName());
        userid.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(name);
        infoPanel.add(Box.createRigidArea(new Dimension(0, TEXT_SPACE)));

        JLabel city = generateText(user.getTimezone().getCity());
        userid.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(city);
        infoPanel.add(Box.createRigidArea(new Dimension(0, TEXT_SPACE)));

        JLabel timeZone = generateText(user.getTimezone().getZoneCode());
        userid.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(timeZone);
        infoPanel.add(Box.createRigidArea(new Dimension(0, TEXT_SPACE)));

        JLabel email = generateText(user.getEmail());
        userid.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(email);

        return infoPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Back")) {
            ui.switchPanel("Main");
        } else if (e.getActionCommand().equals("Reset Password")) {
            ui.switchPanel("Reset Password");
        } else if (e.getActionCommand().equals("Change Profile")){
            ui.switchPanel("Change Profile");
        } else if (e.getActionCommand().equals("Log Out")) {
            ui.switchPanel("Login");
        } else {
                Application application = ui.getApplication();
                application.deleteAccount();
                ui.switchPanel("Login");
        }
    }
}
