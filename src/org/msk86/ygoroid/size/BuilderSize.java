package org.msk86.ygoroid.size;

import org.msk86.ygoroid.newutils.Style;
import org.msk86.ygoroid.newutils.Utils;

public class BuilderSize {
    public static Size MAIN_SECTION, EX_SECTION, SIDE_SECTION, SIDER, BUILDER;

    static {
        int screenWidth = Utils.screenWidth();

        MAIN_SECTION = new Size(screenWidth, Style.padding() * 3 + CardSize.SIDING.height() * 3);
        EX_SECTION = new Size(screenWidth, CardSize.SIDING.height());
        SIDE_SECTION = new Size(screenWidth, CardSize.SIDING.height());
        SIDER = new Size(screenWidth, MAIN_SECTION.height() + EX_SECTION.height() + SIDE_SECTION.height() + InfoBarSize.INFO_BAR.height() + Style.padding() * 2);
        BUILDER = new Size(screenWidth *3 / 4, MAIN_SECTION.height() + EX_SECTION.height() + SIDE_SECTION.height() + InfoBarSize.INFO_BAR.height() + Style.padding() * 2);
    }
}
