package org.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;

public class PlayerInitializerFrame extends JFrame
{
    Color _PlayerColor;

    String _PlayerName;

    JColorChooser _ColorChooser;

    JTextField _TextBox;

    JButton _SubmissionButton;

    boolean _IsSubmitted = false;

    public PlayerInitializerFrame()
    {
        _ColorChooser = new JColorChooser();

        _TextBox = new JTextField();

        _SubmissionButton = new JButton();

        AbstractColorChooserPanel[] panels = _ColorChooser.getChooserPanels();

        _ColorChooser.setChooserPanels(new AbstractColorChooserPanel[]
                {
                    panels[1]
                });

        _SubmissionButton.setText("Finish Player");

        this.add(_ColorChooser, BorderLayout.PAGE_START);
        this.add(_TextBox, BorderLayout.CENTER);
        this.add(_SubmissionButton, BorderLayout.AFTER_LAST_LINE);

        _SubmissionButton.addActionListener(e -> _IsSubmitted = true);

        _IsSubmitted = false;

        this.setTitle("Player initialization");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(500, 400);

        this.setLocationRelativeTo(null);

        this.setResizable(false);

    }



    public BoardLayersListener.PlayerDetails PromptPlayerCreation()
    {
        setVisible(true);

        while (!_IsSubmitted)
        {
            _PlayerColor = _ColorChooser.getColor();
            _PlayerName = _TextBox.getText();
        }

        setVisible(false);

        BufferedImage img;

        try {
            img = ImageIO.read(new File("src/main/java/org/assets/dice/DieBase.png"));

            for(int y = 0; y < img.getHeight(); ++y)
            {
                for(int x = 0; x < img.getWidth(); ++x)
                {
                    //img.getRGB()
                    Color c = new Color(img.getRGB(x, y), true);

                    float[] pxlHSB = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
                    float[] plrHSB = Color.RGBtoHSB(_PlayerColor.getRed(), _PlayerColor.getGreen(), _PlayerColor.getBlue(), null);

                    Color nc = new Color(Color.HSBtoRGB(plrHSB[0], plrHSB[1], pxlHSB[2]));

                    Color pc = new Color(nc.getRed(), nc.getGreen(), nc.getBlue(), c.getAlpha());

                    img.setRGB(x, y, pc.getRGB());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new BoardLayersListener.PlayerDetails(_PlayerName, _PlayerColor, new ImageIcon(img));
    }
}
