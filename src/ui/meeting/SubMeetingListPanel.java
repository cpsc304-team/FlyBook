package ui.meeting;

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

    public SubMeetingListPanel(UI ui, MeetingRecord meeting) {
        this.ui = ui;
        this.meeting = meeting;

        setLayout(new FlowLayout());

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

        JLabel topic = new JLabel(meeting.getTopic());
        info.add(topic);
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
}
