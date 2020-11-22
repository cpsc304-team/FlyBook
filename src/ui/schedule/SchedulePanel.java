package ui.schedule;

import main.Application;
import model.schedule.ScheduleRecord;
import ui.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SchedulePanel extends JPanel implements ActionListener {
    UI ui;

    final static String TODAY = "Today";
    final static String WEEK = "Within a week";
    final static String ALL = "All";
    final static String PASSED = "Passed";

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

        tabbedPane.addTab("Events", new ScheduleListPanel(ui));
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
//
//        JScrollPane tasks = tasks();
//        tabbedPane.addTab("Tasks", tasks);
//        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        return tabbedPane;
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
            ui.setContentPane(new NewSchedulePanel(ui));
            ui.validate();
        }
    }
}
