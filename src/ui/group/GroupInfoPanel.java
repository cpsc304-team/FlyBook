package ui.group;

import main.Application;
import model.group.Group;
import ui.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class GroupInfoPanel extends JPanel implements ActionListener {
    UI ui;
    Group group;
    Application app;

    private Integer TEXT_SPACE = 18;
    private Integer ENTRY_SPACE = 9;

    public GroupInfoPanel(UI ui, Group group) {
        this.ui = ui;
        this.group = group;
        this.app = ui.getApplication();

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
        JLabel title = ui.generateLabel(group.getName());
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

        add(info);

        
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

        JLabel name = ui.generateLabel("Group Name:");
        name.setAlignmentX(Component.RIGHT_ALIGNMENT);
        textPanel.add(name);
        textPanel.add(Box.createRigidArea(new Dimension(0, TEXT_SPACE)));

        JLabel city = ui.generateLabel("Your Nickname:");
        city.setAlignmentX(Component.RIGHT_ALIGNMENT);
        textPanel.add(city);

        return textPanel;
    }

    private JPanel entryPanel() {
        JPanel entryPanel = new JPanel();
        entryPanel.setLayout(new BoxLayout(entryPanel, BoxLayout.Y_AXIS));

        entryPanel.add(Box.createRigidArea(new Dimension(0, TEXT_SPACE)));
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
        JLabel name = ui.generateLabel(group.getName());
        name.setAlignmentX(Component.LEFT_ALIGNMENT);
        namePanel.add(name);

        if (app.isAdmin(group.getGroupid())) {
            JButton changeName = new JButton("Change");
            changeName.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String name = JOptionPane.showInputDialog("Enter a new name");
                    app.updateGroupName(group.getGroupid(), name);
                    group.setName(name);
                    ui.setContentPane(new GroupInfoPanel(ui, group));
                    ui.revalidate();
                }
            });
            namePanel.add(changeName);
            changeName.setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        entryPanel.add(namePanel);
        namePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
//        entryPanel.add(Box.createRigidArea(new Dimension(0, TEXT_SPACE)));

        JPanel nicknamePanel = new JPanel();
        nicknamePanel.setLayout(new BoxLayout(nicknamePanel, BoxLayout.X_AXIS));
        JLabel nickname = ui.generateLabel(app.getGroupMemberByID(app.getCurrentUserID(), group.getGroupid()).getNickname());
        name.setAlignmentX(Component.LEFT_ALIGNMENT);
        nicknamePanel.add(nickname);

        JButton changeNickname = new JButton("Change");
        changeNickname.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Enter a new nickname");
                app.updateNickname(group.getGroupid(), name);
                ui.setContentPane(new GroupInfoPanel(ui, group));
                ui.revalidate();
            }
        });
        nicknamePanel.add(changeNickname);
        changeNickname.setAlignmentX(Component.LEFT_ALIGNMENT);

        entryPanel.add(nicknamePanel);
        entryPanel.add(Box.createRigidArea(new Dimension(0, TEXT_SPACE)));

        return entryPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ui.switchPanel("Group");
    }
}
