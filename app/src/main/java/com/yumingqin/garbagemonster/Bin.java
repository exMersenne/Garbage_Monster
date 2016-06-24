package com.yumingqin.garbagemonster;

/**
 * Created by yumin on 6/24/2016.
 */
public class Bin {
    String BinID;

    public Bin() {
        BinID = "Testing";
    }

    public boolean play(Garbage garbage) {
        if (garbage.id.equals(BinID)) {
            return true;
        }
        return false;
    }

    public int score(boolean scored, int currScore) {
        if (scored) {
            currScore++;
        } else {
            currScore--;
        }
        return currScore;
    }
}
