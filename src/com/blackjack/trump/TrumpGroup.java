package com.blackjack.trump;

import java.util.ArrayList;
import java.util.Collections;

//トランプの束を表すクラス
//山札や手札に使用する
public class TrumpGroup {
    //トランプの束に含まれるトランプを保存する変数
    final public ArrayList<Trump> trumps = new ArrayList<>();

    //トランプをシャッフルする関数
    public void shuffle() {
        Collections.shuffle(trumps);
    }

    //トランプの束から一枚取り出して返す関数
    public Trump draw() {
        return trumps.remove(0);
    }

    //トランプの束に一枚トランプを追加する
    public void add(Trump trump) {
        trumps.add(trump);
    }

    //格納されているトランプの数を返す
    public int getSize() {
        return trumps.size();
    }

    //格納されているトランプをすべて削除する
    public void clear() { 
        trumps.clear();
    }

    //含まれているトランプの合計スコアを返す関数
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