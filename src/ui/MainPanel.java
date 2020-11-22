package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel implements ActionListener {
    UI ui;

    public MainPanel(UI ui) {
        this.ui = ui;

        setLayout(new GridLayout(0, 2));
        add(generateButton("Colleague"));
        add(generateButton("Group"));
        add(generateButton("Post"));
        add(generateButton("Meeting"));
        add(generateButton("Schedule"));
        add(generateButton("Account"));
    }

    // TODO
    // Customize button
    private JButton generateButton(String s) {
        JButton button = new JButton(s);

        button.setActionCommand(s);
        button.addActionListener(this);

        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ui.switchPanel(e.getActionCommand());
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
