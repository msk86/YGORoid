package org.msk86.ygoroid.size;

import org.msk86.ygoroid.newutils.Utils;

public class CardSize extends Size {
    public CardSize(int height) {
        super((int)(height / 1.45), height);
    }

    public static Size NORMAL;
    static {
        int medianH = Utils.screenHeight() / 4 - Utils.commonPadding() * 2;
        int medianW = (int) ((Utils.screenWidth() - 14 * Utils.commonPadding()) * 1.45 / 9.25);
        NORMAL = new CardSize(medianW < medianH ? medianW : medianH);
    }
}
