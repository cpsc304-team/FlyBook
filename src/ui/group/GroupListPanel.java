package ui.group;

import main.Application;
import model.group.Group;
import ui.UI;
import ui.post.IndividualPostRecord;
import ui.utilities.Header;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class GroupListPanel extends JPanel implements ActionListener {
    UI ui;

    public GroupListPanel(UI ui) {
        this.ui = ui;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        add(new Header(ui, "Group"));

        JTabbedPane groupList = groupList();
        add(groupList);
        groupList.setAlignmentX(Component.CENTER_ALIGNMENT);
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


    @Override
    public void actionPerformed(ActionEvent e) {
        ui.switchPanel("Main");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ui.generateBackground(g);
    }
}
