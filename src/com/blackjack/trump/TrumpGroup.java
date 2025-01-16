package com.blackjack.trump;

import java.util.ArrayList;
import java.util.Collections;

public class TrumpGroup {
    final public ArrayList<Trump> trumps = new ArrayList<>();

    public void shuffle() {
        Collections.shuffle(trumps);
    }

    public Trump draw() {
        return trumps.remove(0);
    }

    public void add(Trump trump) {
        trumps.add(trump);
    }

    public int getSize() {
        return trumps.size();
    }

    public void clear() { 
        trumps.clear();
    }

    public int getPoint() {
        int sum = 0;
        int aceCount = 0;
        for (Trump trump : trumps) {
            int point = trump.getPoint();
            sum += point;
            if (point == 1) aceCount++;
        }
        for (int i = 0; i < aceCount; i++) {
            if (sum + 10 <= 21) sum += 10;
        }
        return sum;
    }
}