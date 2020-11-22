package ui.schedule;

import model.schedule.ScheduleRecord;
import model.schedule.Task;
import ui.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class SubSchedulePanel extends JPanel implements ActionListener {
    UI ui;
    ScheduleRecord schedule;

    public SubSchedulePanel(UI ui, ScheduleRecord schedule) {
        this.ui = ui;
        this.schedule = schedule;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JPanel timePanel = new JPanel(new FlowLayout());
        String timestamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(schedule.getTime());
        ArrayList<String> partsOfLine = splitOnSpace(timestamp);
        JLabel date = new JLabel(partsOfLine.get(0) + " | ");
//        date.setFont(new Font(Font.BOLD));
        timePanel.add(date);
        JLabel time = new JLabel(partsOfLine.get(1));
        timePanel.add(time);

        add(timePanel);

        JLabel event = new JLabel(schedule.getEvent());
        add(event);

        JPanel tasksPanel = new JPanel();
        tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.PAGE_AXIS));
        Task[] tasks = ui.getApplication().getTasksBySchedule(schedule);

        for (int i = 0; i < tasks.length; i++) {
            JPanel task = taskPanel(tasks[i]);
            tasksPanel.add(task);
            task.setAlignmentX(LEFT_ALIGNMENT);
        }
        add(tasksPanel);
    }

    private JPanel taskPanel(Task task) {
        JPanel taskPanel = new JPanel(new FlowLayout());

//        if (task.getStatus() == 0) {
//            JButton finish = new JButton("Check");
//            finish.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    ui.getApplication().updateTaskStatus(task, 1);
//                    // TODO: change button
//                }
//            });
//            taskPanel.add(finish);
//        } else {
//            JButton finish = new JButton("Uncheck");
//            finish.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    ui.getApplication().updateTaskStatus(task, 0);
//                }
//            });
//            taskPanel.add(finish);
//        }

        JLabel name = new JLabel(task.getTaskName() + " | " + task.getPriority());
        taskPanel.add(name);

        return taskPanel;
    }

    private static ArrayList<String> splitOnSpace(String line) {
        String[] splits = line.split(" ");
        return new ArrayList<>(Arrays.asList(splits));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
