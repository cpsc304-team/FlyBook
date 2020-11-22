package ui.schedule;

import main.Application;
import model.schedule.ScheduleRecord;
import ui.UI;

import java.awt.*;
import java.awt.event.*;
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
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JPanel comboBoxPane = new JPanel();
        String comboBoxItems[] = {TODAY, WEEK, ALL, PASSED};
        JComboBox cb = new JComboBox(comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(this);
        comboBoxPane.add(cb);

        cards = new JPanel(new CardLayout());
        cards.add(card(TODAY), TODAY);
        cards.add(card(WEEK), WEEK);
        cards.add(card(ALL), ALL);
        cards.add(card(PASSED), PASSED);

        add(comboBoxPane, BorderLayout.PAGE_START);
        add(cards, BorderLayout.CENTER);
    }

    private JPanel card(String type) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.PAGE_AXIS));

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

        for (int i = 0; i < schedules.length; i++) {
            JPanel schedule = new ScheduleRecordPanel(ui, schedules[i]);
            card.add(schedule);
            card.add(Box.createRigidArea(new Dimension(0, 20)));

            schedule.setAlignmentX(LEFT_ALIGNMENT);
            schedule.setAlignmentY(TOP_ALIGNMENT);
        }

        return card;
    }

    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, (String) evt.getItem());
    }
}