package ui.user;

import main.Application;
import ui.utilities.ErrorMessage;
import ui.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class RegisterPanel extends JPanel implements ActionListener {
    UI ui;

    private Integer TEXT_SPACE = 18;
    private Integer ENTRY_SPACE = 9;

    JTextField useridField = new JTextField(10);
    JPasswordField passwordField = new JPasswordField(10);
    JPasswordField reEnterField = new JPasswordField(10);
    JTextField nameField = new JTextField(10);
    JComboBox cityField;
    JTextField emailField = new JTextField(10);
//    JTextField timezoneField = new JTextField(10);

    public RegisterPanel(UI ui) {
        this.ui = ui;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // a sub-panel contains the registration info
        JPanel info = new JPanel(new FlowLayout());
        info.add(textPanel());
        info.add(entryPanel());

        // a sub-panel contains the buttons
        JPanel button = new JPanel();
        button.setLayout(new BoxLayout(button, BoxLayout.PAGE_AXIS));
        button.add(generateButton("Register"));
        JLabel label = new JLabel("Already have an account?");
        button.add(label);
        button.add(generateButton("Log In"));

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

    private JPanel textPanel() {
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel userid = generateText("User ID");
        userid.setAlignmentX(Component.RIGHT_ALIGNMENT);
        textPanel.add(userid);
        textPanel.add(Box.createRigidArea(new Dimension(0, TEXT_SPACE)));

        JLabel password = generateText("Password");
        password.setAlignmentX(Component.RIGHT_ALIGNMENT);
        textPanel.add(password);
        textPanel.add(Box.createRigidArea(new Dimension(0, TEXT_SPACE)));

        JLabel reEnter = generateText("Re-enter Password");
        reEnter.setAlignmentX(Component.RIGHT_ALIGNMENT);
        textPanel.add(reEnter);
        textPanel.add(Box.createRigidArea(new Dimension(0, TEXT_SPACE)));

        JLabel name = generateText("Name");
        name.setAlignmentX(Component.RIGHT_ALIGNMENT);
        textPanel.add(name);
        textPanel.add(Box.createRigidArea(new Dimension(0, TEXT_SPACE)));

        JLabel city = generateText("City");
        city.setAlignmentX(Component.RIGHT_ALIGNMENT);
        textPanel.add(city);
        textPanel.add(Box.createRigidArea(new Dimension(0, TEXT_SPACE)));

        // time zone will be generated automatically so no need for user to enter
//        JLabel timezone = generateText("TimeZone");
//        city.setAlignmentX(Component.RIGHT_ALIGNMENT);
//        textPanel.add(timezone);
//        textPanel.add(Box.createRigidArea(new Dimension(0, TEXT_SPACE)));

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

        passwordField.setEchoChar('*');
        reEnterField.setEchoChar('*');

        entryPanel.add(useridField);
        entryPanel.add(Box.createRigidArea(new Dimension(0, ENTRY_SPACE)));
        entryPanel.add(passwordField);
        entryPanel.add(Box.createRigidArea(new Dimension(0, ENTRY_SPACE)));
        entryPanel.add(reEnterField);
        entryPanel.add(Box.createRigidArea(new Dimension(0, ENTRY_SPACE)));
        entryPanel.add(nameField);
        entryPanel.add(Box.createRigidArea(new Dimension(0, ENTRY_SPACE)));
        cityField = city();
        entryPanel.add(cityField);
        entryPanel.add(Box.createRigidArea(new Dimension(0, ENTRY_SPACE)));
        entryPanel.add(emailField);

//        entryPanel.add(Box.createRigidArea(new Dimension(0, ENTRY_SPACE)));
//        entryPanel.add(timezoneField);

        return entryPanel;
    }

    private JComboBox city() {
        Application application = ui.getApplication();
        String[] cities = application.getCities();
        return new JComboBox(cities);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Register")) {
            String userid = useridField.getText();
            String password = String.valueOf(passwordField.getPassword());
            String reEnter = String.valueOf(reEnterField.getPassword());
            String name = nameField.getText();
            String city = (String) Objects.requireNonNull(cityField.getSelectedItem());
            String email = emailField.getText();
//            String timezone = timezoneField.getText();

            if (userid.isEmpty()) {
                new ErrorMessage("You must create a user ID.");
            } else if (password.isEmpty()) {
                new ErrorMessage("You must create a password.");
            } else if (reEnter.isEmpty()) {
                new ErrorMessage("You must re-enter the password.");
            } else if (!(password.equals(reEnter))) {
                new ErrorMessage("The two passwords do not match.");
            } else if (name.isEmpty()) {
                new ErrorMessage("You must enter your name.");
            } else if (city.isEmpty()) {
                new ErrorMessage("You must select your city.");
            } else if (email.isEmpty()) {
                new ErrorMessage("You must enter your email.");
            }
//            else if (timezone.isEmpty()) {
//                new ErrorMessage("time zone cannot be empty");
//            }
            else {
                Application app = ui.getApplication();
                app.userRegister(userid, password, name, city, email);
            }
        } else {
            ui.switchPanel("Login");
        }
    }

    // TODO: Customize the background
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        try {
//            Image i = ImageIO.read(new File("images/Background.png"));
//            g.drawImage(i, 0, 0, null);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
