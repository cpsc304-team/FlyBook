package ui.schedule;

import main.Application;
import model.schedule.ScheduleRecord;
import ui.UI;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ScheduleListPanel extends JPanel implements ItemListener {
    UI ui;

    JPanel cards;
    final static String TODAY = "Today";
    final static String WEEK = "Within a week";
    final static String ALL = "All";
    final static String PASSED = "Passed";

    public ScheduleListPanel(UI ui) {
        this.ui = ui;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);

        // a sub-panel stores the combobox
        JPanel comboBoxPane = new JPanel();
        comboBoxPane.setOpaque(false);
        String comboBoxItems[] = {TODAY, WEEK, ALL, PASSED};
        JComboBox cb = new JComboBox(comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(this);
        comboBoxPane.add(cb);

        // a sub-panel for cards
        cards = new JPanel(new CardLayout());
        cards.setOpaque(false);
        cards.add(card(TODAY), TODAY);
        cards.add(card(WEEK), WEEK);
        cards.add(card(ALL), ALL);
        cards.add(card(PASSED), PASSED);

        add(comboBoxPane, BorderLayout.PAGE_START);
        add(cards, BorderLayout.CENTER);
    }

    private JScrollPane card(String type) {
        JPanel card = new JPanel();
        JScrollPane scheduleList = new JScrollPane(card);
        scheduleList.setOpaque(false);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        // Get schedules based on different time period selected
        Application app = ui.getApplication();
        ScheduleRecord[] schedules;

        if (type.equals(TODAY)) {
            schedules = app.getSchedulesToday();
        } else if (type.equals(WEEK)) {
            schedules = app.getSchedulesThisWeek();
        } else if (type.equals(ALL)) {
            schedules = app.getSchedulesByID();
        } else {
            schedules = app.getSchedulesPassed();
        }

        if (schedules.length == 0) {
            JLabel text = ui.generateLabel("There is no scheduled event in this time period.");
            card.add(text);
            text.setAlignmentX(CENTER_ALIGNMENT);
        } else {
            // Insert each schedule record
            for (int i = 0; i < schedules.length; i++) {
                JPanel schedule = new ScheduleRecordPanel(ui, schedules[i]);
                card.add(schedule);
                schedule.add(Box.createRigidArea(new Dimension(0, 20)));

                schedule.setAlignmentX(LEFT_ALIGNMENT);
                schedule.setAlignmentY(TOP_ALIGNMENT);
            }
        }

        return scheduleList;
    }

    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, (String) evt.getItem());
    }
}