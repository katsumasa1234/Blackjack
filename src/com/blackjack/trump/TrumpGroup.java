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
}