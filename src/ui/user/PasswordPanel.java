package ui.user;

import main.Application;
import ui.utilities.ErrorMessage;
import ui.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PasswordPanel extends JPanel implements ActionListener {
    UI ui;

    private Integer TEXT_SPACE = 18;
    private Integer ENTRY_SPACE = 9;

    JPasswordField passwordField = new JPasswordField(10);
    JPasswordField reEnterField = new JPasswordField(10);

    public PasswordPanel(UI ui) {
        this.ui = ui;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JPanel info = new JPanel(new FlowLayout());

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        JLabel password = generateText("New Password");
        textPanel.add(password);
        password.setAlignmentX(Component.RIGHT_ALIGNMENT);
        textPanel.add(Box.createRigidArea(new Dimension(0, TEXT_SPACE)));
        JLabel reEnter = generateText("Re-enter New Password");
        textPanel.add(reEnter);
        reEnter.setAlignmentX(Component.RIGHT_ALIGNMENT);
        info.add(textPanel);

        JPanel entryPanel = new JPanel();
        entryPanel.setLayout(new BoxLayout(entryPanel, BoxLayout.Y_AXIS));
        passwordField.setEchoChar('*');
        reEnterField.setEchoChar('*');
        entryPanel.add(passwordField);
        entryPanel.add(Box.createRigidArea(new Dimension(0, ENTRY_SPACE)));
        entryPanel.add(reEnterField);
        info.add(entryPanel);

        JPanel button = new JPanel();
        button.setLayout(new FlowLayout());
        button.add(generateButton("Change"));

        add(info);
        add(button);
    }

    // TODO
    // Customize button
    private JButton generateButton(String s) {
        JButton button = new JButton(s);

        button.setActionCommand(s);
        button.addActionListener(this);

        return button;
    }

    private JLabel generateText(String s) {
        JLabel text = new JLabel(s);
//        text.setFont(new Font("Serif", Font.PLAIN, 14));
        return text;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String password = String.valueOf(passwordField.getPassword());
        String reEnter = String.valueOf(reEnterField.getPassword());

        if (password.isEmpty()) {
            new ErrorMessage("You must create a password.");
        } else if (reEnter.isEmpty()) {
            new ErrorMessage("You must re-enter the password.");
        } else if (!(password.equals(reEnter))) {
            new ErrorMessage("The two passwords do not match.");
        } else {
            Application application = ui.getApplication();
            application.resetPassword(password);
            ui.switchPanel("Account");
        }
    }
}
