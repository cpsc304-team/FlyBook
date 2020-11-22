package ui.user;

import main.Application;
import ui.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel implements ActionListener {
    // The main ui frame
    UI ui;

    // Component of the login panel:
    JTextField usernameField;
    JPasswordField passwordField;

    public LoginPanel(UI ui) {
        this.ui = ui;

        JLabel useridLabel = ui.generateLabel("Enter user ID: ");
        JLabel passwordLabel = ui.generateLabel("Enter password: ");

        usernameField = new JTextField(10);
        passwordField = new JPasswordField(10);
        passwordField.setEchoChar('*');

        JButton loginButton = ui.generateButton("Log In", this);
        JButton registerButton = ui.generateButton("Register", this);

        // layout components using the GridBag layout manager
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        setLayout(gb);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // place the username label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(useridLabel, c);
        add(useridLabel);

        // place the text field for the username
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(usernameField, c);
        add(usernameField);

        // place password label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(0, 10, 10, 0);
        gb.setConstraints(passwordLabel, c);
        add(passwordLabel);

        // place the password field
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 0, 10, 10);
        gb.setConstraints(passwordField, c);
        add(passwordField);

        // place the login button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(loginButton, c);
        add(loginButton);

        // place the register button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(registerButton, c);
        add(registerButton);

        // register buttons with action event handlers
        loginButton.setActionCommand("Log In");
        registerButton.setActionCommand("Register");
        loginButton.addActionListener(this);
        registerButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Log In")) {
            String userid = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());
            Application app = ui.getApplication();
            app.userLogin(userid, password);
        } else {
            ui.switchPanel("Register");
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ui.generateBackground(g);
    }
}
