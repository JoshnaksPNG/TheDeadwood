package org.view;

import javax.swing.*;
import java.awt.*;

public class DicePanel extends JPanel
{
    ImageIcon _Icon;

    public DicePanel(ImageIcon icon)
    {
        _Icon = icon;
    }



    @Override
    public void paintComponent(Graphics g)
    {
        System.out.println("painting die...");
        setOpaque(false);
        g.drawImage(_Icon.getImage(), this.getX(), this.getY(), null);
    }
}
