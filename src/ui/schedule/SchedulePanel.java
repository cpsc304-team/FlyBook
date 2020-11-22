package ui.schedule;

import main.Application;
import model.meeting.MeetingRecord;
import model.schedule.ScheduleRecord;
import ui.UI;
import ui.meeting.SubMeetingListPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class SchedulePanel extends JPanel implements ActionListener, ItemListener {
    UI ui;

    public SchedulePanel(UI ui) {
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
        JLabel title = new JLabel("Schedule  ");
        title.setForeground(new Color(53, 120, 139));
        title.setFont(new Font("Helvetica", Font.BOLD + Font.ITALIC, 20));

        pane.add(backButton());
        pane.add(Box.createHorizontalGlue());
        pane.add(title);

        add(pane);
        pane.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTabbedPane scheduleList = scheduleList();
        add(scheduleList);

        JButton newSchedule = new JButton("New Event");
        newSchedule.addActionListener(this);
        newSchedule.setActionCommand("New");
        add(newSchedule);
    }

    private JTabbedPane scheduleList() {
        JTabbedPane tabbedPane = new JTabbedPane();
//        ImageIcon icon = createImageIcon("images/middle.gif");

        JScrollPane events = events();
        tabbedPane.addTab("Events", events);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
//
//        JScrollPane tasks = tasks();
//        tabbedPane.addTab("Tasks", tasks);
//        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        return tabbedPane;
    }

    private JScrollPane events() {
        JPanel panel = new JPanel();
        JScrollPane events = new JScrollPane(panel);

        panel.setLayout(new CardLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Application app = ui.getApplication();

//        JPanel comboBoxPane = new JPanel();
//        JComboBox timeFrame = new JComboBox(new String[]{"Today", "Within a week", "Passed", "All"});
//        timeFrame.setEditable(false);
//        timeFrame.addItemListener(this);
//        comboBoxPane.add(timeFrame);
//
//        JPanel cards = new JPanel(new CardLayout());
//        cards.add(scheduleList("Today"), "Today");
//        cards.add(scheduleList("Within a week"), "Within a week");
//        cards.add(scheduleList("Passed"), "Passed");
//        cards.add(scheduleList("All"), "All");
//
//        events.add(comboBoxPane);
//        events.add(cards);

        events.add(scheduleList("All"));

        return events;
    }

    public JPanel scheduleList(String type) {
        JPanel scheduleList = new JPanel();
        scheduleList.setLayout(new BoxLayout(scheduleList, BoxLayout.PAGE_AXIS));

        Application app = ui.getApplication();
        ScheduleRecord[] schedules;

        if (type.equals("All")) {
            schedules = app.getSchedulesByID();
        } else if (type.equals("Today")) {
            schedules = app.getSchedulesToday();
        } else if (type.equals("Passed")) {
            schedules = app.getSchedulesPassed();
        } else {
            schedules = app.getSchedulesThisWeek();
        }

        for (int i = 0; i < schedules.length; i++) {
            JPanel schedule = new SubSchedulePanel(ui, schedules[i]);
            scheduleList.add(schedule);
            scheduleList.add(Box.createRigidArea(new Dimension(0, 20)));

            schedule.setAlignmentX(LEFT_ALIGNMENT);
            schedule.setAlignmentY(TOP_ALIGNMENT);
        }

        return scheduleList;
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
            // TODO: add new schedule
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }
}
