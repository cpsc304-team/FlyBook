package ui.schedule;

import main.Application;
import model.schedule.ScheduleRecord;
import ui.UI;
import ui.utilities.ErrorMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

public class NewSchedulePanel extends JPanel implements ActionListener {
    UI ui;

    private Integer TEXT_SPACE = 18;
    private Integer ENTRY_SPACE = 9;

    JTextField timeField = new JTextField(10);
    JTextField eventField = new JPasswordField(10);

    public NewSchedulePanel(UI ui) {
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
        JLabel title = new JLabel("New Schedule  ");
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
        button.add(generateButton("Submit"));

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

        JLabel time = generateText("Time (YYYY-MM-DD HH:MM:SS)");
        time.setAlignmentX(Component.RIGHT_ALIGNMENT);
        textPanel.add(time);
        textPanel.add(Box.createRigidArea(new Dimension(0, TEXT_SPACE)));

        JLabel event = generateText("Event");
        event.setAlignmentX(Component.RIGHT_ALIGNMENT);
        textPanel.add(event);

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

        entryPanel.add(timeField);
        entryPanel.add(Box.createRigidArea(new Dimension(0, ENTRY_SPACE)));
        entryPanel.add(eventField);

        return entryPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Submit")) {
            String time = timeField.getText();
            String event = eventField.getText();

            if (time.isEmpty()) {
                new ErrorMessage("You must choose a time.");
            } else if (event.isEmpty()) {
                new ErrorMessage("Please enter the event detail.");
            } else {
                try {
                    Timestamp timestamp = Timestamp.valueOf(time);
                    int num = ui.getApplication().countSchedules() + 1;
                    while (ui.getApplication().getPostByID("S" + num) != null) {
                        num++;
                    }
                    String sid = "S" + num;
                    ScheduleRecord schedule = new ScheduleRecord (
                            sid,
                            timestamp,
                            event,
                            ui.getApplication().getCurrentUser()
                    );
                    ui.getApplication().addSchedule(schedule);
                    ui.switchPanel("Schedule");
                } catch (IllegalArgumentException exception) {
                    new ErrorMessage("The time must be in the format of \"YYYY-MM-DD HH:MM:SS\".");
                }
            }
        } else {
            ui.switchPanel("Main");
        }
    }

    // TODO: Customize the background
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        try {
//            Image i = ImageIO.read(new File("images/background.png"));
//            g.drawImage(i, 0, 0, null);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
