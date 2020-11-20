package ui.user;

import main.Application;
import ui.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class EditProfilePanel extends JPanel implements ActionListener {
    UI ui;

    private Integer TEXT_SPACE = 18;
    private Integer ENTRY_SPACE = 9;

    JTextField nameField = new JTextField(10);
    JComboBox cityField;
    JTextField emailField = new JTextField(10);

    public EditProfilePanel(UI ui) {
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
        JLabel title = new JLabel("Edit Profile  ");
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
        add(generateButton("Submit"));
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

        JLabel name = generateText("Name");
        name.setAlignmentX(Component.RIGHT_ALIGNMENT);
        textPanel.add(name);
        textPanel.add(Box.createRigidArea(new Dimension(0, TEXT_SPACE)));

        JLabel city = generateText("City");
        city.setAlignmentX(Component.RIGHT_ALIGNMENT);
        textPanel.add(city);
        textPanel.add(Box.createRigidArea(new Dimension(0, TEXT_SPACE)));

        JLabel email = generateText("E-mail");
        email.setAlignmentX(Component.RIGHT_ALIGNMENT);
        textPanel.add(email);

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

        entryPanel.add(nameField);
        entryPanel.add(Box.createRigidArea(new Dimension(0, ENTRY_SPACE)));
        cityField = city();
        entryPanel.add(cityField);
        entryPanel.add(Box.createRigidArea(new Dimension(0, ENTRY_SPACE)));
        entryPanel.add(emailField);

        return entryPanel;
    }

    private JComboBox city() {
        Application application = ui.getApplication();
        String[] cities = application.getCities();
        return new JComboBox(cities);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Submit")) {
            String name = nameField.getText();
            String city = (String) Objects.requireNonNull(cityField.getSelectedItem());
            String email = emailField.getText();
            Application app = ui.getApplication();
            app.updateUser(name, city, email);
            ui.switchPanel("Account");
        } else {
            ui.switchPanel("Account");
        }
    }
}
