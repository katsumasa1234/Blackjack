package com.blackjack;

import java.awt.*;
import javax.swing.*;

public class StartPage extends JPanel{
    JLabel titleLabel = new JLabel("ブラックジャック");

    public StartPage() {
        pageInitialize();
        contentInitialize();
    }

    private void pageInitialize() {
        setOpaque(false);
    }

    private void contentInitialize() {
        add(titleLabel);

        titleLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 48));
    }
}
