package ui.utilities;

import ui.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// This class is used to generate the header for most pages
public class Header extends JPanel implements ActionListener {
    protected UI ui;
    protected String heading;

    public Header(UI ui, String heading) {
        this.ui = ui;
        this.heading = heading;

        setBackground(new Color(15, 85, 130));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JLabel title = new JLabel(heading + "    ");
        title.setForeground(Color.white);
        title.setFont(new Font("Avenir", Font.BOLD, 20));

        add(backButton());
        add(Box.createHorizontalGlue());
        add(title);
    }

    public Header() {
    }

    private JButton backButton() {
        JButton back = new JButton("‹");
        back.setFont(new Font("Avenir", Font.PLAIN, 30));
        back.setForeground(Color.white);
        back.addActionListener(this);
        back.setActionCommand("back");
        back.setPreferredSize(new Dimension(55, 40));
        back.setContentAreaFilled(false);
        back.setBorderPainted(false);
        return back;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ui.switchPanel("Main");
    }
}
