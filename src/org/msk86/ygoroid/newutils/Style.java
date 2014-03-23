package org.msk86.ygoroid.newutils;

import android.graphics.Color;
import org.msk86.ygoroid.size.CardSize;

public class Style {

    public static int padding() {
        return 2;
    }

    public static int fieldPadding() {
        return CardSize.NORMAL.height() / 50;
    }

    public static int border() {
        return 2;
    }

    public static int fontColor () {
        return Color.WHITE;
    }

    public static int textShadowColor () {
        return Color.BLACK;
    }

    public static int fieldBorderColor() {
        return Color.WHITE;
    }
    public static int fieldShadowColor() {
        return Color.BLACK;
    }

    public static int windowBackgroundColor() {
        return Color.BLACK;
    }

    public static int infoBarBackgroundColor() {
        return Color.BLACK;
    }

    public static int infoBarBorderColor() {
        return Color.DKGRAY;
    }

    public static int highlightColor() {
        return Color.BLUE;
    }

    public static int syncFontColor() {
        return Color.BLACK;
    }

    public static int lineColor() {
        return Color.WHITE;
    }
}
