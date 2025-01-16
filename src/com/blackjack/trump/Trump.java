package com.blackjack.trump;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Trump extends JPanel{
    public static enum Mark {spade, heart, diamond, clover}
    public static final Color[] colors = {Color.red, Color.black};
    public static final String[] numbers = {null, "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    public static final Map<Mark, String> markStr = new HashMap<>() {{
        put(Mark.spade, "♠");
        put(Mark.heart, "♥");
        put(Mark.diamond, "♦");
        put(Mark.clover, "♣");
    }};


    final public int num;
    final public Color color;
    final public Mark mark;

    final Dimension cardSize = new Dimension(40, 60);

    public static final int FRONT = 0;
    public static final int BACK = 1;
    public int face = BACK;

    public Trump(int num, Color color, Mark mark) {
        this.num = num;
        this.color = color;
        this.mark = mark;

        panelInitialize();
    }

    private void panelInitialize() {
        setPreferredSize(cardSize);
        setBorder(new LineBorder(Color.black));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, (int)cardSize.getWidth(), (int)cardSize.getHeight());

        if (face == BACK) {
            g.setColor(Color.red);
            g.fillRect(4, 4, (int)(cardSize.getWidth() - 8), (int)(cardSize.getHeight() - 8));
        } else {
            g.setColor(color);
            g.setFont(new Font(Font.DIALOG, Font.PLAIN, 16));
            g.drawString(numbers[num], 3, 16);
            g.setFont(new Font(Font.DIALOG, Font.PLAIN, 64));
            g.drawString(markStr.get(mark), 4, 60);
        }
    }


    public void flip(int face) {
        this.face = face;
    }

    public int getPoint() {
        if (face == FRONT) return Math.min(num, 10);
        else return 0;
    }
}
