package ui.meeting;

import main.Application;
import model.group.Group;
import model.meeting.MeetingRecord;
import ui.UI;
import ui.utilities.ErrorMessage;
import ui.utilities.SuccessMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class NewMeetingPanel extends JPanel implements ActionListener {
    UI ui;

    private Integer TEXT_SPACE = 18;
    private Integer ENTRY_SPACE = 9;

    JTextField topicField = new JTextField(10);
    JComboBox groupList;

    public NewMeetingPanel(UI ui) {
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
        JLabel title = new JLabel("New Meeting  ");
        title.setForeground(new Color(53, 120, 139));
        title.setFont(new Font("Helvetica", Font.BOLD + Font.ITALIC, 20));

        pane.add(backButton());
        pane.add(Box.createHorizontalGlue());
        pane.add(title);

        add(pane);
        pane.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel info = new JPanel(new FlowLayout());
        info.add(textPanel());
        info.add(entryPanel());

        JPanel button = new JPanel();
        button.setLayout(new BoxLayout(button, BoxLayout.PAGE_AXIS));
        button.add(generateButton("Start Meeting"));

        add(info);
        add(button);
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

    private JPanel textPanel() {
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel topic = generateText("Topic");
        topic.setAlignmentX(Component.RIGHT_ALIGNMENT);
        textPanel.add(topic);
        textPanel.add(Box.createRigidArea(new Dimension(0, TEXT_SPACE)));

        JLabel group = generateText("Group");
        group.setAlignmentX(Component.RIGHT_ALIGNMENT);
        textPanel.add(group);

        return textPanel;
    }

    // TODO
    // Customize text font
    private JLabel generateText(String s) {
        JLabel text = new JLabel(s);
//        text.setFont(new Font("Serif", Font.PLAIN, 14));
        return text;
    }

    private JPanel entryPanel() {
        JPanel entryPanel = new JPanel();
        entryPanel.setLayout(new BoxLayout(entryPanel, BoxLayout.Y_AXIS));

        entryPanel.add(topicField);
        entryPanel.add(Box.createRigidArea(new Dimension(0, ENTRY_SPACE)));
        groupList = group();
        entryPanel.add(groupList);

        return entryPanel;
    }

    private JComboBox group() {
        Application application = ui.getApplication();
        Group[] groups = application.getAdminGroups();
        String[] names = new String[groups.length];
        for (int i = 0; i < groups.length; i++) {
            names[i] = groups[i].getGroupid() + ": " + groups[i].getName();
        }
        return new JComboBox(names);
    }

    private static ArrayList<String> splitOnSpace(String line) {
        String[] splits = line.split(": ");
        return new ArrayList<>(Arrays.asList(splits));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Start Meeting")) {
            String topic = topicField.getText();
            String group = null;
            if (groupList.getSelectedItem() != null) {
                group = (String) groupList.getSelectedItem();
            }

            if (topic.isEmpty()) {
                new ErrorMessage("You must enter a meeting topic.");
            } else if (group == null) {
                new ErrorMessage("Only group administrators can initiate a meeting.");
            } else {
                ArrayList<String> partsOfLine = splitOnSpace(group);
                String gid = partsOfLine.get(0);
                int num = ui.getApplication().countMeetings() + 1;
                String mid = "M" + num;
                MeetingRecord meeting = new MeetingRecord(
                        mid,
                        0,
                        topic,
                        new Timestamp(System.currentTimeMillis()),
                        null,
                        ui.getApplication().getGroupByID(gid)
                );
                ui.getApplication().addMeeting(meeting);
                ui.getApplication().joinMeeting(meeting);
                new SuccessMessage("You just started a new meeting!");
                ui.switchPanel("Meeting");
            }
        } else {
            ui.switchPanel("Meeting");
        }
    }

    // TODO: Customize the background
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        try {
//            Image i = ImageIO.read(new File("images/Background.png"));
//            g.drawImage(i, 0, 0, null);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
