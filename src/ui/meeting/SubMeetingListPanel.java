package ui.meeting;

import model.group.Group;
import model.meeting.MeetingRecord;
import ui.UI;
import ui.utilities.SuccessMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SubMeetingListPanel extends JPanel implements ActionListener {
    private UI ui;
    private MeetingRecord meeting;
    private Group mostattendedgroup;

    public SubMeetingListPanel(UI ui, MeetingRecord meeting, Group group) {
        this.ui = ui;
        this.meeting = meeting;
        this.mostattendedgroup = group;

        setLayout(new FlowLayout());

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

        JLabel topic = new JLabel(meeting.getTopic());
        info.add(topic);
        topic.setAlignmentX(LEFT_ALIGNMENT);


        JLabel groupinfo = getLabelWithStar(meeting);
        info.add(groupinfo);

        topic.setAlignmentX(LEFT_ALIGNMENT);

        if (meeting.getAttendance() != 0) {
            JLabel attendance = new JLabel("Attendance: " + meeting.getAttendance());
            info.add(attendance);
            attendance.setAlignmentX(LEFT_ALIGNMENT);
        }
        JLabel start = new JLabel("Start Time: " + meeting.getStartTime().toString());
        info.add(start);
        start.setAlignmentX(LEFT_ALIGNMENT);
        if (meeting.getEndTime() != null) {
            JLabel end = new JLabel("End Time: " + meeting.getEndTime().toString());
            info.add(end);
            end.setAlignmentX(LEFT_ALIGNMENT);
        }

        add(info);

        JPanel button = new JPanel();
        button.setLayout(new GridLayout(0,1));
        if (meeting.getAttendance() == 0) {
            JButton join = new JButton("Join Meeting");
            join.addActionListener(this);
            join.setActionCommand("Join");
            button.add(join);

            if (ui.getApplication().isAdmin(meeting.getGroup().getGroupid())) {
                JButton end = new JButton("End Meeting");
                end.addActionListener(this);
                end.setActionCommand("End");
                button.add(end);
            }
        }
        add(button);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Join")) {
            ui.getApplication().joinMeeting(meeting);
            // TODO: add a fake meeting window if have time
            new SuccessMessage("You just joined the meeting!");
        } else {
            ui.getApplication().endMeeting(meeting);
            new SuccessMessage("The meeting is ended.");
            ui.switchPanel("Meeting");
        }
    }

    private JLabel getLabelWithStar(MeetingRecord meeting) {
        String name = meeting.getGroup().getName();
        JLabel groupname =  new JLabel("Group: " + name);
        if (mostattendedgroup.getName().equals(name)) {
            groupname  = new JLabel("Group: " + name + "[*]");
        }
        return groupname;
    }
}
