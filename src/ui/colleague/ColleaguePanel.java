package ui.colleague;

import main.Application;
import model.User;
import ui.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColleaguePanel extends JPanel implements ActionListener {
    UI ui;

    public ColleaguePanel(UI ui) {
        this.ui = ui;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JScrollPane scrPane = userList();
        add(scrPane);
        scrPane.setAlignmentX(Component.LEFT_ALIGNMENT);

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

        JButton back = new JButton("←");
        back.addActionListener(this);
        ui.setbackButton(back);


        JLabel title = new JLabel("Colleague  ");
        title.setForeground(new Color(53, 120, 139));
        title.setFont(new Font("Helvetica", Font.BOLD + Font.ITALIC, 20));

        pane.add(back);
        pane.add(Box.createHorizontalGlue());
        pane.add(title);

        add(pane);
        pane.setAlignmentX(Component.LEFT_ALIGNMENT);
    }



    private JScrollPane userList() {
        JPanel panel = new JPanel();
        JScrollPane userList = new JScrollPane(panel);
        panel.setLayout(new GridLayout(0,1));

        Application app = ui.getApplication();
        User[] users = app.getUserList();
        String currentUser = app.getCurrentUserID();

        for (int i = 0; i < users.length; i++) {
            JPanel user = new SubColleaguePanel(ui, users[i], currentUser);
            panel.add(user);
        }

        return userList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ui.switchPanel("Main");
    }
}
