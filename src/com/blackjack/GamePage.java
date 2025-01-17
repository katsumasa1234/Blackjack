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
        //gamePageにパネルを追加
        add(dealerInfoPanel);
        add(dealerPanel);
        add(actionPanel);
        add(playerPanel);
        add(playerInfoPanel);

        //画面真ん中のスタンドやヒットなどのボタンを追加
        actionPanel.add(dealButton);
        actionPanel.add(standButton);
        actionPanel.add(hitButton);
        actionPanel.add(doubleDownButton);
        actionPanel.add(splitButton);
        actionPanel.add(insuranceButton);
        actionPanel.add(continueButton);
        actionPanel.add(finishButton);

        //プレイヤーの手札用のパネルの設定
        playerPanel.setLayout(playerTrumpCardLayout);
        playerPanel.add(playerTrumpPanel, "single");
        playerPanel.add(playerSplitPanel, "split");
        playerTrumpCardLayout.show(playerPanel, "single");

        //スプリット用の手札パネルの設定
        playerSplitPanel.setLayout(playerTrumpGridLayout);
        playerSplitPanel.add(playerSplitPanel1);
        playerSplitPanel.add(playerSplitPanel2);

        //手持ちポイントなどのプレイヤーの情報を表示するパネルの設定
        playerInfoPanel.setLayout(new GridLayout(3, 1));
        playerInfoPanel.add(playerDeckInfoPanel);
        playerInfoPanel.add(playerBetInfoPanel);
        playerInfoPanel.add(playerPointInfoPanel);

        //プレイヤー情報用のラベルやテキストフィールドの設定
        dealerInfoPanel.add(dealerDeckInfoLabel);
        playerDeckInfoPanel.add(playerDeckInfoLabel);
        playerBetInfoPanel.add(playerBetInfoLabel);
        playerBetInfoPanel.add(dealField);
        playerPointInfoPanel.add(playerPointInfoLabel);

        //パネルの背景の透明化
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

        //ボタンのフォーカスペイントの無効化
        dealButton.setFocusPainted(false);
        standButton.setFocusPainted(false);
        hitButton.setFocusPainted(false);
        doubleDownButton.setFocusPainted(false);
        splitButton.setFocusPainted(false);
        insuranceButton.setFocusPainted(false);
        continueButton.setFocusPainted(false);
        finishButton.setFocusPainted(false);

        //賭けポイント入力フィールドの設定
        dealField.setColumns(10);
        dealField.setHorizontalAlignment(JFormattedTextField.RIGHT);
        dealField.setText("0");

        //ボタンのlistenerの設定
        dealButton.addActionListener(_ -> deal());
        standButton.addActionListener(_ -> stand());
        hitButton.addActionListener(_ -> hit());
        doubleDownButton.addActionListener(_ -> doubleDown());
        insuranceButton.addActionListener(_ -> insurance());
        splitButton.addActionListener(_ -> split());
        continueButton.addActionListener(_ -> gameContinue());
    }

    //ゲームを初期化する関数
    public void gameInitialize() {
        //プレイヤーポイントの初期化
        playerPoint = 10000;
        playerBetPoint = 0;

        //ボタン表示の初期化
        dealButton.setVisible(true);
        standButton.setVisible(false);
        hitButton.setVisible(false);
        doubleDownButton.setVisible(false);
        splitButton.setVisible(false);
        insuranceButton.setVisible(false);
        continueButton.setVisible(false);
        finishButton.setVisible(false);

        //テキストフィールドの初期化
        dealField.setEnabled(true);
        dealField.setText("0");

        //プレイヤーの手札用のパネルをスプリットしていない状態に設定
        changePlayerDeck("single");

        //山札や手札の初期化
        deckTrump.clear();
        dealerTrump.clear();
        playerTrump.clear();
        trashTrump.clear();
        for (Color color : Trump.colors) for (Mark mark : Mark.values()) for (int i = 1; i <= 13; i++) {
            deckTrump.add(new Trump(i, color, mark));
        }
        deckTrump.shuffle();

        //情報描画の更新
        trumpUpdate();
        infoUpdate();
    }

    //トランプの描画更新を行う関数
    public void trumpUpdate() {
        //描画されているトランプを一度すべて削除する
        playerTrumpPanel.removeAll();
        playerSplitPanel1.removeAll();
        playerSplitPanel2.removeAll();
        dealerPanel.removeAll();
        
        //プレイヤーとディーラーの手札を描画する
        for (Trump trump : dealerTrump.trumps) dealerPanel.add(trump);
        if (playerTrumpPanel.isVisible()) {
            for (Trump trump : playerTrump.trumps) playerTrumpPanel.add(trump);
        } else {
            for (Trump trump : playerTrump.trumps) playerSplitPanel1.add(trump);
            for (Trump trump : playerSplitTrump.trumps) playerSplitPanel2.add(trump);
        }

        //画面の再描画を強制的に行う
        dealerPanel.setVisible(false);
        dealerPanel.setVisible(true);
    }

    //手持ちポイントなどの情報を更新する関数
    public void infoUpdate() {
        if (playerTrumpPanel.isVisible()) playerDeckInfoLabel.setText("Score : " + playerTrump.getPoint() + (playerTrump.getPoint() > 21 ? " burst" : ""));
        else playerDeckInfoLabel.setText("Score : " + playerTrump.getPoint() + (playerTrump.getPoint() > 21 ? " burst" : "") + " | Score : " + playerSplitTrump.getPoint() + (playerSplitTrump.getPoint() > 21 ? " burst" : ""));

        dealerDeckInfoLabel.setText("Score : " + dealerTrump.getPoint() + (dealerTrump.getPoint() > 21 ? " burst" : ""));

        playerPointInfoLabel.setText("所持ポイント : " + playerPoint);
    }

    //プレイヤーの手札を描画するパネルをスプリット用と普通のもので切り替える
    public void changePlayerDeck(String deck) {
        playerTrumpCardLayout.show(playerPanel, deck);
    }

    //finishButtonのlistenerを追加する関数
    public void addFinishCallback(ActionListener listener) {
        finishButton.addActionListener(listener);
    }

    //メッセージダイアログの表示を簡略化するための関数
    private void showMessageDialog(String text) {
        JOptionPane.showMessageDialog(getParent(), text);
    }

    //最初の手札を用意する関数
    //ディーラーとプレイヤーそれぞれにトランプを二枚ずつ配布する
    public void setDefaultDeck() {
        //手札が残っていれば使用済み手札に移動させる
        while (dealerTrump.getSize() != 0) trashTrump.add(dealerTrump.draw());
        while (playerTrump.getSize() != 0) trashTrump.add(playerTrump.draw());
        while (playerSplitTrump.getSize() != 0) trashTrump.add(playerSplitTrump.draw());

        //フォーカスされているスプリット用のパネルのフォーカスを解除する
        playerSplitPanel1.setBorder(null);
        playerSplitPanel2.setBorder(null);

        //スプリット、ダブルダウン、インシュアランスの解除を行う
        changePlayerDeck("single");
        playerFocusTrump = playerTrump;
        isInsurance = false;
        isDoubleDown = false;

        //ディーラーに手札を二枚与える
        Trump trump = draw();
        trump.flip(Trump.FRONT);
        if (trump.num == 1) insuranceButton.setVisible(true); //もし一枚目がAであれば、インシュアランスを行える状態にする
        dealerTrump.add(trump);
        trump = draw();
        trump.flip(Trump.BACK);
        dealerTrump.add(trump);

        //プレイヤーに手札を二枚与える
        trump = draw();
        trump.flip(Trump.FRONT);
        int num = trump.getPoint();
        playerTrump.add(trump);
        trump = draw();
        trump.flip(Trump.FRONT);
        if (num == trump.getPoint()) splitButton.setVisible(true); //もし2枚が同じスコアのトランプであれば、スプリットを行える状態にする
        playerTrump.add(trump);

        //ボタンの表示非表示を切り替える
        dealButton.setVisible(false);
        standButton.setVisible(true);
        hitButton.setVisible(true);
        doubleDownButton.setVisible(true);

        //賭けポイント入力フィールドを無効化する
        dealField.setEnabled(false);

        //情報描画の更新を行う
        trumpUpdate();
        infoUpdate();
        
        //プレイヤーのブラックジャックの判定を行う
        if (playerTrump.getPoint() == 21) {
            blackjack();
        }
    }

    //賭けポイントを決定したときの処理を行う関数
    public void deal() {
        //入力されたポイントを取得する
        try {
            playerBetPoint = Long.parseLong(dealField.getText().replace(",", ""));
        } catch (NumberFormatException e) {
            showMessageDialog("ベットしたポイントが不正です。");
            return;
        }

        //所持ポイントが足りるかの確認
        if (playerBetPoint > playerPoint) {
            showMessageDialog("所持ポイントが足りません。");
            return;
        }

        //マイナスの値を賭けていないかの確認
        if (playerBetPoint < 0) {
            showMessageDialog("0未満のポイントをかけることはできません。");
            return;
        }

        //所持ポイントから賭けポイントを引く
        playerPoint -= playerBetPoint;

        //最初の手札を用意する
        setDefaultDeck();
    }

    //山札からトランプを一枚引く関数
    public Trump draw() {
        //山札がなくなれば、使用済みトランプを山札に戻し、シャッフルする
        try {
            return deckTrump.draw();
        } catch (IndexOutOfBoundsException e) {
            while (trashTrump.getSize() != 0) deckTrump.add(trashTrump.draw());
            deckTrump.shuffle();
            return deckTrump.draw();
        }
    }

    //ヒットを行う関数
    public void hit() {
        //できなくなる動作のボタンを非表示にする
        doubleDownButton.setVisible(false);
        insuranceButton.setVisible(false);
        splitButton.setVisible(false);

        //トランプを一枚引く
        Trump trump = draw();
        trump.flip(Trump.FRONT);
        playerFocusTrump.add(trump);

        //描画の更新を行う
        trumpUpdate();
        infoUpdate();

        //もし21以上のスコアになれば自動的にスタンドを行う
        if (playerFocusTrump.getPoint() >= 21) stand();
    }

    //スタンドを行う関数
    public void stand() {
        //スプリットを行っていて、一つ目の手札をスタンドしたときの処理
        if (playerSplitPanel.isVisible() && playerFocusTrump == playerTrump) {
            //フォーカスをもう一つの手札にうつす
            playerFocusTrump = playerSplitTrump;
            playerSplitPanel1.setBorder(null);
            playerSplitPanel2.setBorder(new LineBorder(Color.black));

            //もう一つの手札のスコアがすでに21であれば自動的にスタンドする
            if (playerSplitTrump.getPoint() == 21) stand();

            //描画の更新
            trumpUpdate();
            infoUpdate();

            return;
        }

        //できなくなる処理のボタンを非表示にする
        standButton.setVisible(false);
        hitButton.setVisible(false);
        doubleDownButton.setVisible(false);
        insuranceButton.setVisible(false);
        splitButton.setVisible(false);

        //ディーラーの二枚目の手札をひっくり返す
        dealerTrump.trumps.get(1).flip(Trump.FRONT);

        //描画の更新
        infoUpdate();
        trumpUpdate();

        //バーストしていた場合の処理
        if (playerSplitPanel.isVisible() && playerSplitTrump.getPoint() > 21) lose();
        if (playerSplitPanel.isVisible() && playerTrump.getPoint() > 21) lose();
        if (playerSplitPanel.isVisible() && playerSplitTrump.getPoint() > 21 && playerTrump.getPoint() > 21) return;
        if (!playerSplitPanel.isVisible() && playerTrump.getPoint() > 21) {
            lose();
            return;
        }

        //ディーラーがブラックジャックだった場合の処理
        if (dealerTrump.getPoint() == 21) {
            continueButton.setVisible(true);
            finishButton.setVisible(true);
            if (isInsurance) playerPoint += playerBetPoint / 2f * 3; //インシュアランスが行われていた場合の処理
            infoUpdate();
            dealerDeckInfoLabel.setText("Score : 21 blackjack");
            return;
        }

        //ディーラーのスコアが17以上になるまでトランプを引く
        while (dealerTrump.getPoint() < 17) {
            Trump trump = draw();
            trump.flip(Trump.FRONT);
            dealerTrump.add(trump);
        }

        //描画の更新
        trumpUpdate();
        infoUpdate();

        //プレイヤーとディーラーの勝敗確認
        if (playerTrump.getPoint() > 21) {}
        else if (dealerTrump.getPoint() > 21 || playerTrump.getPoint() > dealerTrump.getPoint()) win();
        else if (dealerTrump.getPoint() == playerTrump.getPoint()) push();
        else lose();

        //スプリットが行われていた場合の勝敗確認
        if (playerSplitPanel.isVisible()) {
            if (playerSplitTrump.getPoint() > 21) {}
            else if (dealerTrump.getPoint() > 21 || playerSplitTrump.getPoint() > dealerTrump.getPoint()) win();
            else if (dealerTrump.getPoint() == playerSplitTrump.getPoint()) push();
            else lose();
        }
    }

    //ダブルダウンを行う関数
    public void doubleDown() {
        //所持ポイントが足りるかどうかの確認
        if (playerPoint - playerBetPoint < 0) {
            showMessageDialog("ポイントが足りません。");
            return;
        }

        //ダブルダウンを有効化する処理
        isDoubleDown = true;
        playerPoint -= playerBetPoint;

        //できなくなる動作のボタンを非表示にする
        doubleDownButton.setVisible(false);
        insuranceButton.setVisible(false);
        splitButton.setVisible(false);

        //トランプを一枚引く
        Trump trump = draw();
        trump.flip(Trump.FRONT);
        playerFocusTrump.add(trump);

        //自動でスタンドする
        stand();
    }

    //インシュアランスを行う関数
    public void insurance() {
        //所持ポイントが足りるかどうかの確認
        if (playerPoint - playerBetPoint / 2f < 0) {
            showMessageDialog("ポイントが足りません。");
            return;
        }

        //インシュアランスの有効化処理
        isInsurance = true;
        playerPoint -= playerBetPoint / 2f;

        //できなくなる動作のボタンを非表示にする
        insuranceButton.setVisible(false);

        //描画の更新
        infoUpdate();
    }

    //スプリットを行う関数
    public void split() {
        //所持ポイントが足りるかどうかの確認
        if (playerPoint - playerBetPoint < 0) {
            showMessageDialog("ポイントが足りません。");
            return;
        }

        //使用するポイントを引く
        playerPoint -= playerBetPoint;

        //できなくなる動作のボタンを非表示にする
        splitButton.setVisible(false);
        doubleDownButton.setVisible(false);

        //表示されるパネルをスプリット用のものに切り替える
        changePlayerDeck("split");

        //手札の一つをもう一つの手札にうつす
        playerSplitTrump.add(playerTrump.trumps.remove(1));

        //新しいトランプをそれぞれの手札に一枚ずつ追加する
        Trump trump = draw();
        trump.flip(Trump.FRONT);
        playerTrump.add(trump);

        trump = draw();
        trump.flip(Trump.FRONT);
        playerSplitTrump.add(trump);

        //フォーカスする手札を設定する
        playerFocusTrump = playerTrump;
        playerSplitPanel1.setBorder(new LineBorder(Color.black));

        //すでにポイントが21になっていれば自動でスタンドを行う
        if (playerTrump.getPoint() == 21) {
            stand();
            if (playerSplitTrump.getPoint() == 21) stand();
        }

        //情報の更新を行う
        trumpUpdate();
        infoUpdate();
    }

    //ゲームを続ける場合の処理を行う関数
    public void gameContinue() {
        //できるようになる動作のボタンを表示する
        dealButton.setVisible(true);

        //できなくなる動作のボタンを非表示にする
        continueButton.setVisible(false);
        finishButton.setVisible(false);

        //賭けポイントを入力するフィールドを有効化する
        dealField.setEnabled(true);
    }

    //プレイヤーが勝利したときの処理を行う関数
    public void win() {
        //賭けポイントの2倍を獲得する
        playerPoint += playerBetPoint * 2;
        if (isDoubleDown) playerPoint += playerBetPoint * 2; //もしダブルダウンを行っていれば追加でポイントを獲得する

        //描画の更新
        infoUpdate();
        trumpUpdate();

        //できなくなる動作のボタンを非表示にする
        standButton.setVisible(false);
        hitButton.setVisible(false);
        doubleDownButton.setVisible(false);
        insuranceButton.setVisible(false);
        splitButton.setVisible(false);

        //できるようになる動作のボタンを表示する
        continueButton.setVisible(true);
        finishButton.setVisible(true);
    }

    //プレイヤーが敗北したときの処理を行う関数
    public void lose() {
        //描画の更新
        infoUpdate();
        trumpUpdate();

        //できなくなる動作のボタンを非表示にする
        standButton.setVisible(false);
        hitButton.setVisible(false);
        doubleDownButton.setVisible(false);
        insuranceButton.setVisible(false);
        splitButton.setVisible(false);

        //できるようになる動作のボタンを表示する
        continueButton.setVisible(true);
        finishButton.setVisible(true);
    }

    //引き分けだった場合の処理を行う関数
    public void push() {
        //賭けポイントが返却される
        playerPoint += playerBetPoint;
        if (isDoubleDown) playerPoint += playerBetPoint; //ダブルダウンを行ってい場合はその分も返却する

        //描画の更新
        infoUpdate();
        trumpUpdate();

        //できなくなる動作のボタンを非表示にする
        standButton.setVisible(false);
        hitButton.setVisible(false);
        doubleDownButton.setVisible(false);
        insuranceButton.setVisible(false);
        splitButton.setVisible(false);

        //できるようになる動作のボタンを表示する
        continueButton.setVisible(true);
        finishButton.setVisible(true);
    }

    //プレイヤーがブラックジャックだった場合の処理
    public void blackjack() {
        //できなくなる動作のボタンを非表示にする
        standButton.setVisible(false);
        hitButton.setVisible(false);
        doubleDownButton.setVisible(false);
        splitButton.setVisible(false);
        insuranceButton.setVisible(false);

        //できるようになる動作のボタンを表示する
        continueButton.setVisible(true);
        finishButton.setVisible(true);

        //ディーラーの二枚目のトランプをひっくり返す
        dealerTrump.trumps.get(1).flip(Trump.FRONT);
        trumpUpdate();

        if (dealerTrump.getPoint() == 21) { //ディーラーもブラックジャックだった場合は引き分けとして処理する
            playerPoint += playerBetPoint;
            infoUpdate();
            dealerDeckInfoLabel.setText("Score : 21 blackjack");
        } else { //そうでなければ賭けポイントの2.5倍のポイントを獲得する
            playerPoint += (int)(playerBetPoint * 2.5);
            infoUpdate();
        }

        //ブラックジャック専用の情報を表示する
        playerDeckInfoLabel.setText("Score : 21 blackjack");
    }
}