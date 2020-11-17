package ui.user;

import ui.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountPanel extends JPanel implements ActionListener {
    UI ui;

    private Integer TEXT_SPACE = 18;
    private Integer ENTRY_SPACE = 9;

    JTextField useridField = new JTextField(10);
    JPasswordField passwordField = new JPasswordField(10);
    JPasswordField reEnterField = new JPasswordField(10);
    JTextField nameField = new JTextField(10);
    JComboBox cityField;
    JTextField emailField = new JTextField(10);

    public AccountPanel(UI ui) {
        this.ui = ui;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JPanel info = new JPanel(new FlowLayout());
        info.add(labelPanel());
        info.add(infoPanel());
//
//        // a sub-panel contains the buttons
//        JPanel button = new JPanel();
//        button.setLayout(new BoxLayout(button, BoxLayout.PAGE_AXIS));
//        button.add(generateButton("Register"));
//        JLabel label = new JLabel("Already have an account?");
//        button.add(label);
//        button.add(generateButton("Log In"));
//
//        add(info);
//        add(button);
    }

    private JPanel labelPanel() {
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));

//        JLabel userid = generateText("User ID");
//        userid.setAlignmentX(Component.RIGHT_ALIGNMENT);
//        labelPanel.add(userid);
//        labelPanel.add(Box.createRigidArea(new Dimension(0, TEXT_SPACE)));


        return labelPanel;
    }

    private JLabel generateText(String user_id) {
        JLabel text = new JLabel();

        return text;
    }

    private JPanel infoPanel() {
        JPanel infoPanel = new JPanel();

        return infoPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
