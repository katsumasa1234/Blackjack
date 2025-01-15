package com.blackjack;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class StartPage extends JPanel{
    GridLayout gridLayout = new GridLayout(2, 1);
    JLabel titleLabel = new JLabel("ブラックジャック");
    JButton startButton = new JButton("スタート");
    JPanel titlePanel = new JPanel();
    JPanel buttonPanel = new JPanel();

    public StartPage() {
        pageInitialize();
        contentInitialize();
    }

    private void pageInitialize() {
        setOpaque(false);
        setLayout(gridLayout);
    }

    private void contentInitialize() {
        titlePanel.add(titleLabel);
        buttonPanel.add(startButton);

        add(titlePanel);
        add(buttonPanel);

        titlePanel.setOpaque(false);
        buttonPanel.setOpaque(false);

        titleLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 48));

        startButton.setFocusPainted(false);
    }

    public void addStartButtonCallback(ActionListener listener) {
        startButton.addActionListener(listener);
    }
}
