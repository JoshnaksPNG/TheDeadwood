package org.view;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        return new BoardLayersListener.PlayerDetails(_PlayerName, _PlayerColor);
    }
}
