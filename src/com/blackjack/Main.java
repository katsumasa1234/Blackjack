package com.blackjack;

import javax.swing.*;
import java.awt.*;

public class Main {
    //プログラムのエントリーポイント
    //フレームの生成と表示を行う
    public static void main(String[] args) {
        Frame frame = new Frame();
        frame.setVisible(true);
    }
}

//メインのフレームのクラス
class Frame extends JFrame {
    //startPageが起動したとき最初に表示される画面
    //gamePageがゲーム中表示されている画面
    //それぞれをcardLayoutを使用して切り替える
    CardLayout cardLayout = new CardLayout();

    StartPage startPage = new StartPage();
    GamePage gamePage = new GamePage();

    public Frame() {
        frameInitialize();
        pageInitialize();
    }

    //フレームの初期化関数
    //サイズや背景色の設定を行っている
    private void frameInitialize() {
        setTitle("ブラックジャック");
        setSize(500, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(0, 180, 0));
        setLayout(cardLayout);
    }

    //startPageとgamePageの初期化関数
    //ページの表示とコールバック関数の設定を行う
    private void pageInitialize() {
        getContentPane().add(startPage, "startPage");
        getContentPane().add(gamePage, "gamePage");

        startPage.addStartButtonCallback(_ -> startGame());
        gamePage.addFinishCallback(_ -> finishGame());

        showPage("startPage");
    }

    //"startPage"または"gamePage"を引数として渡すことで、フレームに表示されるパネルを切り替える
    public void showPage(String page) {
        cardLayout.show(getContentPane(), page);
    }

    //startPageでスタートボタンが押された時の処理
    public void startGame() {
        gamePage.gameInitialize();
        showPage("gamePage");
    }

    //gamePageで終了ボタンが押された時の処理
    public void finishGame() {
        showPage("startPage");
    }
}