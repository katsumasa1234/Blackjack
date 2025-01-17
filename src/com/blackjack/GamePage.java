package com.blackjack;

import java.awt.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import javax.swing.*;
import javax.swing.border.LineBorder;
import com.blackjack.trump.*;
import com.blackjack.trump.Trump.Mark;

public class GamePage extends JPanel{
    //画面レイアウトのためのコンテンツの定義
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
    JPanel playerDeckInfoPanel = new JPanel();
    JPanel playerBetInfoPanel = new JPanel();
    JPanel playerPointInfoPanel = new JPanel();

    JLabel dealerDeckInfoLabel = new JLabel();
    JLabel playerDeckInfoLabel = new JLabel();
    JLabel playerBetInfoLabel = new JLabel("ベットするポイント");
    JLabel playerPointInfoLabel = new JLabel();

    JButton dealButton = new JButton("ディール");
    JButton standButton = new JButton("スタンド");
    JButton hitButton = new JButton("ヒット");
    JButton doubleDownButton = new JButton("ダブルダウン");
    JButton splitButton = new JButton("スプリット");
    JButton insuranceButton = new JButton("インシュアランス");
    JButton continueButton = new JButton("続ける");
    JButton finishButton = new JButton("終了する");

    JFormattedTextField dealField = new JFormattedTextField(NumberFormat.getInstance());

    //使用されるTrumpGroupの定義
    final TrumpGroup deckTrump = new TrumpGroup(); //山札
    final TrumpGroup dealerTrump = new TrumpGroup(); //ディーラーの手札
    final TrumpGroup playerTrump = new TrumpGroup(); //プレイヤーの手札
    final TrumpGroup playerSplitTrump = new TrumpGroup(); //スプリットがあった場合に使用される手札
    final TrumpGroup trashTrump = new TrumpGroup(); //使用済みのトランプの保存用

    //スプリットに対応するために、プレイヤーがフォーカスしている手札を保存する
    TrumpGroup playerFocusTrump = playerTrump;

    //プレイヤーの情報を保存するための変数
    public long playerPoint = 10000;
    public long playerBetPoint = 0;
    public boolean isInsurance = false;
    public boolean isDoubleDown = false;

    public GamePage() {
        pageInitialize();
        contentInitialize();
    }

    //gamePageの初期化関数
    private void pageInitialize() {
        setOpaque(false);
        setLayout(gridLayout);
    }

    //使用されるコンテンツの初期化関数
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

        playerSplitPanel.setLayout(playerTrumpGridLayout);
        playerSplitPanel.add(playerSplitPanel1);
        playerSplitPanel.add(playerSplitPanel2);

        playerInfoPanel.setLayout(new GridLayout(3, 1));
        playerInfoPanel.add(playerDeckInfoPanel);
        playerInfoPanel.add(playerBetInfoPanel);
        playerInfoPanel.add(playerPointInfoPanel);

        dealerInfoPanel.add(dealerDeckInfoLabel);
        playerDeckInfoPanel.add(playerDeckInfoLabel);
        playerBetInfoPanel.add(playerBetInfoLabel);
        playerBetInfoPanel.add(dealField);
        playerPointInfoPanel.add(playerPointInfoLabel);

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
        playerDeckInfoPanel.setOpaque(false);
        playerBetInfoPanel.setOpaque(false);
        playerPointInfoPanel.setOpaque(false);

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

        dealButton.addActionListener(_ -> deal());
        standButton.addActionListener(_ -> stand());
        hitButton.addActionListener(_ -> hit());
        doubleDownButton.addActionListener(_ -> doubleDown());
        insuranceButton.addActionListener(_ -> insurance());
        splitButton.addActionListener(_ -> split());
        continueButton.addActionListener(_ -> gameContinue());
    }

    //ゲーム情報を初期化する関数
    public void gameInitialize() {
        playerPoint = 10000;
        playerBetPoint = 0;

        dealButton.setVisible(true);
        standButton.setVisible(false);
        hitButton.setVisible(false);
        doubleDownButton.setVisible(false);
        splitButton.setVisible(false);
        insuranceButton.setVisible(false);
        continueButton.setVisible(false);
        finishButton.setVisible(false);

        dealField.setEnabled(true);
        dealField.setText("0");

        playerDeckInfoLabel.setText("");

        changePlayerDeck("single");

        deckTrump.clear();
        dealerTrump.clear();
        playerTrump.clear();
        trashTrump.clear();
        for (Color color : Trump.colors) for (Mark mark : Mark.values()) for (int i = 1; i <= 13; i++) {
            deckTrump.add(new Trump(i, color, mark));
        }
        deckTrump.shuffle();

        trumpUpdate();
        infoUpdate();
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

        dealerPanel.setVisible(false);
        dealerPanel.setVisible(true);
    }

    public void infoUpdate() {
        if (playerTrumpPanel.isVisible()) playerDeckInfoLabel.setText("Score : " + playerTrump.getPoint() + (playerTrump.getPoint() > 21 ? " burst" : ""));
        else playerDeckInfoLabel.setText("Score : " + playerTrump.getPoint() + (playerTrump.getPoint() > 21 ? " burst" : "") + " | Score : " + playerSplitTrump.getPoint() + (playerSplitTrump.getPoint() > 21 ? " burst" : ""));

        dealerDeckInfoLabel.setText("Score : " + dealerTrump.getPoint() + (dealerTrump.getPoint() > 21 ? " burst" : ""));

        playerPointInfoLabel.setText("所持ポイント : " + playerPoint);
    }

    public void changePlayerDeck(String deck) {
        playerTrumpCardLayout.show(playerPanel, deck);
    }

    public void addFinishCallback(ActionListener listener) {
        finishButton.addActionListener(listener);
    }

    private void showMessageDialog(String text) {
        JOptionPane.showMessageDialog(getParent(), text);
    }


    public void setDefaultDeck() {
        while (dealerTrump.getSize() != 0) trashTrump.add(dealerTrump.draw());
        while (playerTrump.getSize() != 0) trashTrump.add(playerTrump.draw());
        while (playerSplitTrump.getSize() != 0) trashTrump.add(playerSplitTrump.draw());

        playerSplitPanel1.setBorder(null);
        playerSplitPanel2.setBorder(null);

        changePlayerDeck("single");
        playerFocusTrump = playerTrump;
        isInsurance = false;
        isDoubleDown = false;

        Trump trump = draw();
        trump.flip(Trump.FRONT);
        if (trump.num == 1) insuranceButton.setVisible(true);
        dealerTrump.add(trump);
        trump = draw();
        trump.flip(Trump.BACK);
        dealerTrump.add(trump);

        trump = draw();
        trump.flip(Trump.FRONT);
        int num = trump.getPoint();
        playerTrump.add(trump);
        trump = draw();
        trump.flip(Trump.FRONT);
        if (num == trump.getPoint()) splitButton.setVisible(true);
        playerTrump.add(trump);

        dealButton.setVisible(false);
        standButton.setVisible(true);
        hitButton.setVisible(true);
        doubleDownButton.setVisible(true);
        dealField.setEnabled(false);

        trumpUpdate();
        infoUpdate();
        
        if (playerTrump.getPoint() == 21) {
            blackjack();
            return;
        }
    }

    public void deal() {
        try {
            playerBetPoint = Long.parseLong(dealField.getText().replace(",", ""));
        } catch (NumberFormatException e) {
            showMessageDialog("ベットしたポイントが不正です。");
            return;
        }

        if (playerBetPoint > playerPoint) {
            showMessageDialog("所持ポイントが足りません。");
            return;
        }

        if (playerBetPoint < 0) {
            showMessageDialog("0未満のポイントをかけることはできません。");
            return;
        }

        playerPoint -= playerBetPoint;

        setDefaultDeck();
    }

    public Trump draw() {
        try {
            return deckTrump.draw();
        } catch (IndexOutOfBoundsException e) {
            while (trashTrump.getSize() != 0) deckTrump.add(trashTrump.draw());
            deckTrump.shuffle();
            return deckTrump.draw();
        }
    }

    public void hit() {
        doubleDownButton.setVisible(false);
        insuranceButton.setVisible(false);
        splitButton.setVisible(false);

        Trump trump = draw();
        trump.flip(Trump.FRONT);
        playerFocusTrump.add(trump);

        trumpUpdate();
        infoUpdate();

        if (playerFocusTrump.getPoint() >= 21) stand();
    }

    public void stand() {
        if (playerSplitPanel.isVisible() && playerFocusTrump == playerTrump) {
            playerFocusTrump = playerSplitTrump;
            playerSplitPanel1.setBorder(null);
            playerSplitPanel2.setBorder(new LineBorder(Color.black));
            if (playerSplitTrump.getPoint() == 21) stand();
            trumpUpdate();
            infoUpdate();
            return;
        }
        standButton.setVisible(false);
        hitButton.setVisible(false);
        doubleDownButton.setVisible(false);
        insuranceButton.setVisible(false);
        splitButton.setVisible(false);

        dealerTrump.trumps.get(1).flip(Trump.FRONT);
        infoUpdate();
        trumpUpdate();

        if (playerSplitPanel.isVisible() && playerSplitTrump.getPoint() > 21) lose();
        if (playerSplitPanel.isVisible() && playerTrump.getPoint() > 21) lose();
        if (playerSplitPanel.isVisible() && playerSplitTrump.getPoint() > 21 && playerTrump.getPoint() > 21) return;
        if (!playerSplitPanel.isVisible() && playerTrump.getPoint() > 21) {
            lose();
            return;
        }

        if (dealerTrump.getPoint() == 21) {
            continueButton.setVisible(true);
            finishButton.setVisible(true);
            if (isInsurance) playerPoint += playerBetPoint / 2f * 3;
            infoUpdate();
            dealerDeckInfoLabel.setText("Score : 21 blackjack");
            return;
        }

        while (dealerTrump.getPoint() < 17) {
            Trump trump = draw();
            trump.flip(Trump.FRONT);
            dealerTrump.add(trump);
        }

        trumpUpdate();
        infoUpdate();

        if (playerTrump.getPoint() > 21) {}
        else if (dealerTrump.getPoint() > 21 || playerTrump.getPoint() > dealerTrump.getPoint()) win();
        else if (dealerTrump.getPoint() == playerTrump.getPoint()) push();
        else lose();

        if (playerSplitPanel.isVisible()) {
            if (playerSplitTrump.getPoint() > 21) {}
            else if (dealerTrump.getPoint() > 21 || playerSplitTrump.getPoint() > dealerTrump.getPoint()) win();
            else if (dealerTrump.getPoint() == playerSplitTrump.getPoint()) push();
            else lose();
        }
    }

    public void doubleDown() {
        if (playerPoint - playerBetPoint < 0) {
            showMessageDialog("ポイントが足りません。");
            return;
        }

        isDoubleDown = true;
        playerPoint -= playerBetPoint;

        doubleDownButton.setVisible(false);
        insuranceButton.setVisible(false);
        splitButton.setVisible(false);

        Trump trump = draw();
        trump.flip(Trump.FRONT);
        playerFocusTrump.add(trump);

        stand();
    }

    public void insurance() {
        if (playerPoint - playerBetPoint / 2f < 0) {
            showMessageDialog("ポイントが足りません。");
            return;
        }

        isInsurance = true;
        playerPoint -= playerBetPoint / 2f;

        insuranceButton.setVisible(false);

        infoUpdate();
    }

    public void split() {
        if (playerPoint - playerBetPoint < 0) {
            showMessageDialog("ポイントが足りません。");
            return;
        }

        playerPoint -= playerBetPoint;
        splitButton.setVisible(false);
        doubleDownButton.setVisible(false);

        changePlayerDeck("split");
        playerSplitTrump.add(playerTrump.trumps.remove(1));

        Trump trump = draw();
        trump.flip(Trump.FRONT);
        playerTrump.add(trump);

        trump = draw();
        trump.flip(Trump.FRONT);
        playerSplitTrump.add(trump);

        playerFocusTrump = playerTrump;
        playerSplitPanel1.setBorder(new LineBorder(Color.black));
        if (playerTrump.getPoint() == 21) {
            stand();
            if (playerSplitTrump.getPoint() == 21) stand();
        }

        trumpUpdate();
        infoUpdate();
    }

    public void gameContinue() {
        dealButton.setVisible(true);

        continueButton.setVisible(false);
        finishButton.setVisible(false);
        
        dealField.setEnabled(true);
    }

    public void win() {
        playerPoint += playerBetPoint * 2;
        if (isDoubleDown) playerPoint += playerBetPoint * 2;
        
        infoUpdate();
        trumpUpdate();

        standButton.setVisible(false);
        hitButton.setVisible(false);
        doubleDownButton.setVisible(false);
        insuranceButton.setVisible(false);
        splitButton.setVisible(false);

        continueButton.setVisible(true);
        finishButton.setVisible(true);
    }

    public void lose() {
        infoUpdate();
        trumpUpdate();

        standButton.setVisible(false);
        hitButton.setVisible(false);
        doubleDownButton.setVisible(false);
        insuranceButton.setVisible(false);
        splitButton.setVisible(false);

        continueButton.setVisible(true);
        finishButton.setVisible(true);
    }

    public void push() {
        playerPoint += playerBetPoint;
        if (isDoubleDown) playerPoint += playerBetPoint;

        infoUpdate();
        trumpUpdate();

        standButton.setVisible(false);
        hitButton.setVisible(false);
        doubleDownButton.setVisible(false);
        insuranceButton.setVisible(false);
        splitButton.setVisible(false);

        continueButton.setVisible(true);
        finishButton.setVisible(true);
    }

    public void blackjack() {
        standButton.setVisible(false);
        hitButton.setVisible(false);
        doubleDownButton.setVisible(false);
        splitButton.setVisible(false);
        insuranceButton.setVisible(false);

        continueButton.setVisible(true);
        finishButton.setVisible(true);

        dealerTrump.trumps.get(1).flip(Trump.FRONT);
        trumpUpdate();

        if (dealerTrump.getPoint() == 21) {
            playerPoint += playerBetPoint;
            infoUpdate();
            dealerDeckInfoLabel.setText("Score : 21 blackjack");
            
        } else {
            playerPoint += (int)(playerBetPoint * 2.5);
            infoUpdate();
        }

        playerDeckInfoLabel.setText("Score : 21 blackjack");
    }
}