package ui.meeting;

import main.Application;
import model.group.Group;
import model.meeting.MeetingRecord;
import ui.UI;
import ui.group.SubGroupListPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MeetingListPanel extends JPanel implements ActionListener {
    UI ui;

    public MeetingListPanel(UI ui) {
        this.ui = ui;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));



        JTabbedPane meetingList = meetingList();
        add(meetingList);

        JButton newMeeting = ui.generateButton("New Meeting",this);
        newMeeting.addActionListener(this);
        newMeeting.setActionCommand("New");
        add(newMeeting);
    }

    private JTabbedPane meetingList() {
        JTabbedPane tabbedPane = new JTabbedPane();
//        ImageIcon icon = createImageIcon("images/middle.gif");

        JScrollPane current = currentMeetings();
        tabbedPane.addTab("Current Meetings", current);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        JScrollPane past = pastMeetings();
        tabbedPane.addTab("Past Meetings", past);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        return tabbedPane;
    }

    private JScrollPane pastMeetings() {
        JPanel panel = new JPanel();
        JScrollPane pastMeetings = new JScrollPane(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Application app = ui.getApplication();
        MeetingRecord[] meetings = app.getPastMeetingsByID();

        for (int i = meetings.length - 1; i >= 0; i--) {
            JPanel meeting = new SubMeetingListPanel(ui, meetings[i]);
            panel.add(meeting);
            meeting.add(Box.createRigidArea(new Dimension(0, 20)));

            meeting.setAlignmentX(LEFT_ALIGNMENT);
            meeting.setAlignmentY(TOP_ALIGNMENT);
        }

        return pastMeetings;
    }

    private JScrollPane currentMeetings() {
        JPanel panel = new JPanel();
        JScrollPane myGroup = new JScrollPane(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Application app = ui.getApplication();
        MeetingRecord[] meetings = app.getCurrentMeetingsByID();

        for (int i = meetings.length - 1; i >= 0; i--) {
            JPanel meeting = new SubMeetingListPanel(ui, meetings[i]);
            panel.add(meeting);
            meeting.add(Box.createRigidArea(new Dimension(0, 20)));

            meeting.setAlignmentX(LEFT_ALIGNMENT);
            meeting.setAlignmentY(TOP_ALIGNMENT);
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
        if (e.getActionCommand().equals("Back")) {
            ui.switchPanel("Main");
        } else {
            ui.setContentPane(new NewMeetingPanel(ui));
            ui.revalidate();
        }
    }
}
