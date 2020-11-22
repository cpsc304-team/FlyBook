package ui.user;

import main.Application;
import model.user.User;
import ui.UI;
import ui.utilities.Header;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

// This page shows all the user info and allows the user to edit the profile
public class AccountPanel extends JPanel implements ActionListener {
    UI ui;
    User user;

    private Integer TEXT_SPACE = 18;

    public AccountPanel(UI ui, User user) {
        this.ui = ui;
        this.user = user;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // add header
        add(new Header(ui, "Account"));
        add(Box.createRigidArea(new Dimension(0,20)));

        // a sub-panel contains the info
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.X_AXIS));
        info.setOpaque(false);
        info.add(labelPanel());
        info.add(Box.createRigidArea(new Dimension(20,0)));
        info.add(infoPanel());
        info.add(changePanel());

        add(info);
        info.setAlignmentX(Component.CENTER_ALIGNMENT);

        // a sub-panel contains the buttons
        JPanel button = new JPanel();
        button.setLayout(new BoxLayout(button, BoxLayout.Y_AXIS));
        button.setOpaque(false);
        button.add(generateButton("Reset Password"));
        button.add(generateButton("Log Out"));
        button.add(generateButton("Delete Account"));


        add(button);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    // Customizes button
    private JButton generateButton(String s) {
        JButton button = new JButton(s);

        button.setFont(new Font("Avenir", Font.BOLD, 14));
        button.setForeground(Color.DARK_GRAY);
        button.setMinimumSize(new Dimension(170, 35));
        button.setMaximumSize(new Dimension(170, 35));
        button.addActionListener(this);
        button.setActionCommand(s);

        return button;
    }

    // Shows all the labels of the personal profile
    private JPanel labelPanel() {
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        labelPanel.setOpaque(false);

        JLabel userid = generateTitle("User ID");
        labelPanel.add(userid);
        userid.setAlignmentX(Component.LEFT_ALIGNMENT);
        labelPanel.add(Box.createRigidArea(new Dimension(0, TEXT_SPACE)));

        JLabel name = generateTitle("Name");
        labelPanel.add(name);
        labelPanel.add(Box.createRigidArea(new Dimension(0, TEXT_SPACE)));

        JLabel city = generateTitle("City");
        labelPanel.add(city);
        labelPanel.add(Box.createRigidArea(new Dimension(0, TEXT_SPACE)));

        JLabel timeZone = generateTitle("Time Zone");
        labelPanel.add(timeZone);
        labelPanel.add(Box.createRigidArea(new Dimension(0, TEXT_SPACE)));

        JLabel email = generateTitle("Email");
        labelPanel.add(email);

        labelPanel.setAlignmentY(TOP_ALIGNMENT);

        return labelPanel;
    }

    // Shows all the detailed info of the personal profile
    private JPanel infoPanel() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        JLabel userid = generateText(user.getUserid());
        userid.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(userid);
        infoPanel.add(Box.createRigidArea(new Dimension(0, TEXT_SPACE)));

        JLabel name = generateText(user.getName());
        infoPanel.add(name);
        infoPanel.add(Box.createRigidArea(new Dimension(0, TEXT_SPACE)));

        JLabel city = generateText(user.getTimezone().getCity());
        infoPanel.add(city);
        infoPanel.add(Box.createRigidArea(new Dimension(0, TEXT_SPACE)));

        JLabel timeZone = generateText(user.getTimezone().getZoneCode());
        infoPanel.add(timeZone);
        infoPanel.add(Box.createRigidArea(new Dimension(0, TEXT_SPACE)));

        JLabel email = generateText(user.getEmail());
        infoPanel.add(email);

        infoPanel.setAlignmentY(TOP_ALIGNMENT);
        infoPanel.setMinimumSize(new Dimension(195, 200));
        infoPanel.setMaximumSize(new Dimension(195, 200));

        return infoPanel;
    }

    // Shows all the buttons used for changing profile
    private JPanel changePanel() {
        JPanel changePanel = new JPanel();
        changePanel.setLayout(new BoxLayout(changePanel, BoxLayout.Y_AXIS));
        changePanel.setOpaque(false);

        changePanel.add(Box.createRigidArea(new Dimension(0,33)));
        JButton name = changeButton();
        name.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Object[] options = ui.getApplication().getCities();
                String name = (String)JOptionPane.showInputDialog(ui, "Enter your name:",
                        "Change name",
                        JOptionPane.PLAIN_MESSAGE,
                        null, null, null);
                if (name != null) {
                    ui.getApplication().changeName(name);
                    ui.switchPanel("Account");
                }
            }
        });
        changePanel.add(name);
        changePanel.add(Box.createRigidArea(new Dimension(0,5)));

        JButton city = changeButton();
        city.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Object[] options = ui.getApplication().getCities();
                String city = (String)JOptionPane.showInputDialog(ui, "Choose your city:",
                        "Change city",
                        JOptionPane.PLAIN_MESSAGE,
                        null, options, ui.getApplication().getCurrentUser().getTimezone().getCity());
                if (city != null) {
                    ui.getApplication().changeCity(city);
                    ui.switchPanel("Account");
                }
            }
        });
        changePanel.add(city);
        changePanel.add(Box.createRigidArea(new Dimension(0,44)));

        JButton email = changeButton();
        email.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Object[] options = ui.getApplication().getCities();
                String email = (String)JOptionPane.showInputDialog(ui, "Enter your email:",
                        "Change email",
                        JOptionPane.PLAIN_MESSAGE,
                        null, null, null);
                if (email != null) {
                    ui.getApplication().changeEmail(email);
                    ui.switchPanel("Account");
                }
            }
        });
        changePanel.add(email);

        changePanel.setAlignmentY(TOP_ALIGNMENT);
        changePanel.setPreferredSize(new Dimension(80, 200));

        return changePanel;
    }

    // Generate label text
    private JLabel generateTitle(String s) {
        JLabel text = new JLabel(s);
        text.setFont(new Font("Avenir", Font.PLAIN, 14));
        return text;
    }

    // Generate info text
    private JLabel generateText(String s) {
        JLabel text = new JLabel(s);
        text.setFont(new Font("Avenir", Font.BOLD, 14));
        text.setForeground(new Color(15, 85, 130));
        return text;
    }

    // Generate the button used to edit the profile
    private JButton changeButton() {
        ImageIcon i1 = new ImageIcon("images/Edit.png");
        ImageIcon i2 = new ImageIcon(i1.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        JButton change = new JButton(i2);

        change.setBorderPainted(false);
        change.setOpaque(false);
        change.setContentAreaFilled(false);

        return change;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Back")) {
            ui.switchPanel("Main");
        } else if (e.getActionCommand().equals("Reset Password")) {
            ui.switchPanel("Reset Password");
        } else if (e.getActionCommand().equals("Log Out")) {
            ui.switchPanel("Login");
        } else {
            Object[] options = {"Yes",
                    "Cancel"};
            int n = JOptionPane.showOptionDialog(ui,
                    "Do you want to delete this account?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[1]);
            if (n == 0) {
                Application application = ui.getApplication();
                application.deleteAccount();
                ui.switchPanel("Login");
            }
        }
    }

    // Customize the background
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            Image i = ImageIO.read(new File("images/Background.png"));
            g.drawImage(i, 0, 0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
