package ui.group;

import main.Application;
import model.group.Group;
import model.group.GroupMember;
import ui.utilities.SuccessMessage;
import ui.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;

public class SubGroupListPanel extends JPanel implements ActionListener {
    UI ui;
    Group group;

    public SubGroupListPanel(UI ui, Group group) {
        this.ui = ui;
        this.group = group;

        setLayout(new FlowLayout());

        JLabel name = ui.generateLabel(group.getName());
        add(name);

        Application app = ui.getApplication();
        if (app.isMember(group.getGroupid())) {
            add(ui.generateButton("Open", this));
            add(ui.generateButton("About", this));
        } else {
            add(ui.generateButton("Join", this));
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Application app = ui.getApplication();
        if (e.getActionCommand().equals("Open")) {
            new GroupChatWindow(ui, group);
        } else if (e.getActionCommand().equals("About")) {
            ui.setContentPane(new GroupInfoPanel(ui, group));
            ui.revalidate();
        } else {
            GroupMember member = new GroupMember(
                    new Timestamp(System.currentTimeMillis()),
                    app.getCurrentUser(),
                    group,
                    // TODO: customize the input dialog
                    JOptionPane.showInputDialog("Enter a nickname"));
            app.joinGroup(member);
            new SuccessMessage("You joined " + group.getName() + " successfully!");
            ui.switchPanel("Group");
        }
    }
}
