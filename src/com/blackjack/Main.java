package com.blackjack;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Frame frame = new Frame();
        frame.setVisible(true);
    }
}

class Frame extends JFrame {
    CardLayout cardLayout = new CardLayout();

    StartPage startPage = new StartPage();
    GamePage gamePage = new GamePage();

    public Frame() {
        frameInitialize();
        pageInitialize();
    }

    private void frameInitialize() {
        setTitle("ブラックジャック");
        setSize(500, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(0, 180, 0));
        setLayout(cardLayout);
    }

    private void pageInitialize() {
        getContentPane().add(startPage, "startPage");
        getContentPane().add(gamePage, "gamePage");

        startPage.addStartButtonCallback(_ -> startGame());
        gamePage.addFinishCallback(_ -> finishGame());

        showPage("startPage");
    }

    public void showPage(String page) {
        cardLayout.show(getContentPane(), page);
    }

    public void startGame() {
        gamePage.gameInitialize();
        showPage("gamePage");
    }

    public void finishGame() {
        showPage("startPage");
    }
}