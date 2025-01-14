package com.blackjack.trump;

import java.awt.Color;

public class Trump {
    public enum Mark {spade, heart, diamond, clover}

    final public int num;
    final public Color color;
    final public Mark mark;

    public Trump(int num, Color color, Mark mark) {
        this.num = num;
        this.color = color;
        this.mark = mark;
    }
}
