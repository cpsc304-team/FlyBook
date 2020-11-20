package ui.group;

import main.Application;
import model.group.Group;
import ui.UI;
import ui.post.IndividualPostRecord;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class GroupListPanel extends JPanel implements ActionListener {
    UI ui;

    public GroupListPanel(UI ui) {
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
        JLabel title = new JLabel("Group  ");
        title.setForeground(new Color(53, 120, 139));
        title.setFont(new Font("Helvetica", Font.BOLD + Font.ITALIC, 20));

        pane.add(backButton());
        pane.add(Box.createHorizontalGlue());
        pane.add(title);

        add(pane);
        pane.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTabbedPane groupList = groupList();
        add(groupList);
    }

    private JTabbedPane groupList() {
        JTabbedPane tabbedPane = new JTabbedPane();
//        ImageIcon icon = createImageIcon("images/middle.gif");

        JScrollPane myGroups = myGroups();
        tabbedPane.addTab("My groups", myGroups);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        JScrollPane allGroups = allGroups();
        tabbedPane.addTab("All groups", allGroups);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        return tabbedPane;
    }

    private JScrollPane allGroups() {
        JPanel panel = new JPanel();
        JScrollPane myGroup = new JScrollPane(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Application app = ui.getApplication();
        Group[] groups = app.getGroups();

        for (int i = 0; i < groups.length; i++) {
            JPanel group = new SubGroupListPanel(ui, groups[i]);
            panel.add(group);
            group.add(Box.createRigidArea(new Dimension(0, 20)));

            group.setAlignmentX(LEFT_ALIGNMENT);
            group.setAlignmentY(TOP_ALIGNMENT);
        }

        return myGroup;
    }

    private JScrollPane myGroups() {
        JPanel panel = new JPanel();
        JScrollPane myGroup = new JScrollPane(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Application app = ui.getApplication();
        Group[] groups = app.getCurrentUsersGroups();

        for (int i = 0; i < groups.length; i++) {
            JPanel group = new SubGroupListPanel(ui, groups[i]);
            panel.add(group);
            group.add(Box.createRigidArea(new Dimension(0, 20)));

            group.setAlignmentX(LEFT_ALIGNMENT);
            group.setAlignmentY(TOP_ALIGNMENT);
        }

        return myGroup;
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


    @Override
    public void actionPerformed(ActionEvent e) {
        ui.switchPanel("Main");
    }
}
