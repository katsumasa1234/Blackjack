package com.blackjack;

import java.awt.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.*;
import com.blackjack.trump.*;
import com.blackjack.trump.Trump.Mark;

public class GamePage extends JPanel{

    GridLayout gridLayout = new GridLayout(5, 1);
    GridLayout playerTrumpGridLayout = new GridLayout(1, 2);
    CardLayout playerTrumpCardLayout = new CardLayout();

    JPanel dealerInfoPanel = new JPanel();
    JPanel dealerPanel = new JPanel();
    JPanel actionPanel = new JPanel();
    JPanel playerPanel = new JPanel();
    JPanel playerTrumpPanel = new JPanel();
    JPanel playerSplitPanel = new JPanel();
    JPanel playerSplitPanel1 = new JPanel();
    JPanel playerSplitPanel2 = new JPanel();
    JPanel playerInfoPanel = new JPanel();

    JButton dealButton = new JButton("ディール");
    JButton standButton = new JButton("スタンド");
    JButton hitButton = new JButton("ヒット");
    JButton doubleDownButton = new JButton("ダブルダウン");
    JButton splitButton = new JButton("スプリット");
    JButton insuranceButton = new JButton("インシュアランス");
    JButton continueButton = new JButton("続ける");
    JButton finishButton = new JButton("終了する");

    JFormattedTextField dealField = new JFormattedTextField(NumberFormat.getInstance());

    final TrumpGroup deckTrump = new TrumpGroup();
    final TrumpGroup dealerTrump = new TrumpGroup();
    final TrumpGroup playerTrump = new TrumpGroup();
    final TrumpGroup playerSplitTrump = new TrumpGroup();
    final TrumpGroup trashTrump = new TrumpGroup();

    public GameManager gameManager = new GameManager();

    public GamePage() {
        pageInitialize();
        contentInitialize();
    }

    private void pageInitialize() {
        setOpaque(false);
        setLayout(gridLayout);
    }

    private void contentInitialize() {
        add(dealerInfoPanel);
        add(dealerPanel);
        add(actionPanel);
        add(playerPanel);
        add(playerInfoPanel);

        actionPanel.add(dealButton);
        actionPanel.add(standButton);
        actionPanel.add(hitButton);
        actionPanel.add(doubleDownButton);
        actionPanel.add(splitButton);
        actionPanel.add(insuranceButton);
        actionPanel.add(continueButton);
        actionPanel.add(finishButton);

        playerPanel.setLayout(playerTrumpCardLayout);
        playerPanel.add(playerTrumpPanel, "single");
        playerPanel.add(playerSplitPanel, "split");
        playerTrumpCardLayout.show(playerPanel, "single");

        playerSplitPanel.add(playerSplitPanel1);
        playerSplitPanel.add(playerSplitPanel2);
        playerSplitPanel.setLayout(playerTrumpGridLayout);

        playerInfoPanel.add(dealField);

        dealerInfoPanel.setOpaque(false);
        dealerPanel.setOpaque(false);
        actionPanel.setOpaque(false);
        playerPanel.setOpaque(false);
        playerTrumpPanel.setOpaque(false);
        playerSplitPanel.setOpaque(false);
        playerSplitPanel1.setOpaque(false);
        playerSplitPanel2.setOpaque(false);
        playerInfoPanel.setOpaque(false);
        continueButton.setOpaque(false);
        finishButton.setOpaque(false);

        dealButton.setFocusPainted(false);
        standButton.setFocusPainted(false);
        hitButton.setFocusPainted(false);
        doubleDownButton.setFocusPainted(false);
        splitButton.setFocusPainted(false);
        insuranceButton.setFocusPainted(false);
        continueButton.setFocusPainted(false);
        finishButton.setFocusPainted(false);

        dealField.setColumns(10);
        dealField.setHorizontalAlignment(JFormattedTextField.RIGHT);
        dealField.setText("0");
    }

    public void gameInitialize() {
        dealButton.setVisible(true);
        standButton.setVisible(false);
        hitButton.setVisible(false);
        doubleDownButton.setVisible(false);
        splitButton.setVisible(false);
        insuranceButton.setVisible(false);
        continueButton.setVisible(false);
        finishButton.setVisible(false);

        changePlayerDeck("single");

        deckTrump.clear();
        dealerTrump.clear();
        playerTrump.clear();
        trashTrump.clear();
        for (Color color : Trump.colors) for (Mark mark : Mark.values()) for (int i = 1; i <= 13; i++) {
            deckTrump.add(new Trump(i, color, mark));
        }
        deckTrump.shuffle();

        for (int i = 0; i < 20; i++) {
            Trump trump = deckTrump.draw();
            trump.flip(Trump.FRONT);
            playerTrump.add(trump);
        }
        for (int i = 0; i < 20; i++) {
            Trump trump = deckTrump.draw();
            trump.flip(Trump.FRONT);
            dealerTrump.add(trump);
        }
        trumpUpdate();
    }

    public void trumpUpdate() {
        playerTrumpPanel.removeAll();
        playerSplitPanel1.removeAll();
        playerSplitPanel2.removeAll();
        dealerPanel.removeAll();
        
        for (Trump trump : dealerTrump.trumps) dealerPanel.add(trump);
        if (playerTrumpPanel.isVisible()) {
            for (Trump trump : playerTrump.trumps) playerTrumpPanel.add(trump);
        } else {
            for (Trump trump : playerTrump.trumps) playerSplitPanel1.add(trump);
            for (Trump trump : playerSplitTrump.trumps) playerSplitPanel2.add(trump);
        }
    }

    public void changePlayerDeck(String deck) {
        playerTrumpCardLayout.show(playerPanel, deck);
    }

    public void addFinishCallback(ActionListener listener) {
        finishButton.addActionListener(listener);
    }
}