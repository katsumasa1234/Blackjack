package com.blackjack;

import java.awt.*;
import javax.swing.*;
import com.blackjack.trump.*;
import com.blackjack.trump.Trump.Mark;

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
    JButton continueButton = new JButton("続ける");
    JButton finishButton = new JButton("終了する");

    final TrumpGroup deckTrump = new TrumpGroup();
    final TrumpGroup dealerTrump = new TrumpGroup();
    final TrumpGroup playerTrump = new TrumpGroup();
    final TrumpGroup trashTrump = new TrumpGroup();

    public GamePage() {
        pageInitialize();
        contentInitialize();
        gameInitialize();
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
        actionPanel.add(continueButton);
        actionPanel.add(finishButton);

        dealerPanel.setOpaque(false);
        actionPanel.setOpaque(false);
        playerPanel.setOpaque(false);
        playerInfoPanel.setOpaque(false);
        continueButton.setOpaque(false);
        finishButton.setOpaque(false);

        standButton.setFocusPainted(false);
        hitButton.setFocusPainted(false);
        doubleDownButton.setFocusPainted(false);
        splitButton.setFocusPainted(false);
        insuranceButton.setFocusPainted(false);
        continueButton.setFocusPainted(false);
        finishButton.setFocusPainted(false);
    }

    public void gameInitialize() {
        standButton.setVisible(false);
        hitButton.setVisible(false);
        doubleDownButton.setVisible(false);
        splitButton.setVisible(false);
        insuranceButton.setVisible(false);
        continueButton.setVisible(false);
        finishButton.setVisible(false);

        deckTrump.clear();
        dealerTrump.clear();
        playerTrump.clear();
        trashTrump.clear();
        for (Color color : Trump.colors) for (Mark mark : Mark.values()) for (int i = 1; i <= 13; i++) {
            deckTrump.add(new Trump(i, color, mark));
        }
        deckTrump.shuffle();
    }

    public void drawPage() {
        playerPanel.removeAll();
        for (Trump trump : playerTrump.trumps) playerPanel.add(trump);
    }
}