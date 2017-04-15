package org.msk86.ygoroid.size;

import org.msk86.ygoroid.newutils.Utils;

public class InfoBarSize {
    public static Size INFO_BAR;

    static {
        int infoBarHeight = Math.max(25, Utils.screenHeight() / 18);
        InfoBarSize.INFO_BAR = new OtherSize(Utils.screenWidth(), infoBarHeight);
    }
}
