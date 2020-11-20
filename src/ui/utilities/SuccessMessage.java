package ui.utilities;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SuccessMessage extends JFrame {
    public SuccessMessage(String s) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 40, 20, 40));

        JLabel title = new JLabel("√ Success");
        title.setForeground(new Color(101, 150, 93));
        title.setFont(new Font("Helvetica", Font.BOLD, 15));
        panel.add(title);
        panel.add(new JLabel(s));

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
}
