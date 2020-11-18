package ui.colleague;

import main.Application;
import model.IndividualChat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;

public class IndividualChatEntry extends JPanel implements ActionListener {
    JTextField textEntry = new JTextField(25);
    IndividualChatPanel panel;

    public IndividualChatEntry(IndividualChatPanel panel) {
        this.panel = panel;
        setLayout(new FlowLayout());

        add(textEntry);

        JButton send = new JButton("Send");
        add(send);
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("received");

        String content = textEntry.getText();
        panel.addEntry(content);
    }
}