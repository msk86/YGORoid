package org.msk86.ygoroid.size;

import org.msk86.ygoroid.utils.Utils2;

public class CardSize extends Size {
    public CardSize(int height) {
        super((int)(height / 1.45), height);
    }

    public static Size NORMAL;
    static {
        int medianH = Utils2.screenHeight() / 4 - Utils2.commonPadding() * 2;
        int medianW = (int) ((Utils2.screenWidth() - 14 * Utils2.commonPadding()) * 1.45 / 9.25);
        NORMAL = new CardSize(medianW < medianH ? medianW : medianH);
    }
}
