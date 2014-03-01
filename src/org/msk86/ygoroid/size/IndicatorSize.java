package org.msk86.ygoroid.size;

public class IndicatorSize extends Size {
    public IndicatorSize(int width) {
        super(width, width);
    }

    public static Size NORMAL;
    static {
        NORMAL = new IndicatorSize(CardSize.NORMAL.width() / 4);
    }
}
