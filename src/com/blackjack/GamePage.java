package com.blackjack;

import java.awt.*;
import javax.swing.*;

public class GamePage extends JPanel{
    GridLayout gridLayout = new GridLayout(4, 1);

    JPanel dealerPanel = new JPanel();
    JPanel actionPanel = new JPanel();
    JPanel playerPanel = new JPanel();
    JPanel playerInfoPanel = new JPanel();

    JButton standButton = new JButton("スタンド");
    JButton hitButton = new JButton("ヒット");
    JButton doubleDownButton = new JButton("ダブルダウン");
    JButton splitButton = new JButton("スプリット");
    JButton insuranceButton = new JButton("インシュアランス");

    public GamePage() {
        pageInitialize();
        contentInitialize();
    }

    private void pageInitialize() {
        setOpaque(false);
        setLayout(gridLayout);
    }

    private void contentInitialize() {
        add(dealerPanel);
        add(actionPanel);
        add(playerPanel);
        add(playerInfoPanel);

        actionPanel.add(standButton);
        actionPanel.add(hitButton);
        actionPanel.add(doubleDownButton);
        actionPanel.add(splitButton);
        actionPanel.add(insuranceButton);

        dealerPanel.setOpaque(false);
        actionPanel.setOpaque(false);
        playerPanel.setOpaque(false);
        playerInfoPanel.setOpaque(false);

        standButton.setFocusPainted(false);
        hitButton.setFocusPainted(false);
        doubleDownButton.setFocusPainted(false);
        splitButton.setFocusPainted(false);
        insuranceButton.setFocusPainted(false);
    }
}