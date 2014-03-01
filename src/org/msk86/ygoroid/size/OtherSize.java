package org.msk86.ygoroid.size;

public class OtherSize extends Size {
    public OtherSize(int width, int height) {
        super(width, height);
    }

    public static Size HAND_CARDS, CARD_SELECTOR;
    static {
        HAND_CARDS = new OtherSize(FieldSize.SQUARE.width() * 5 - CardSize.NORMAL.width() / 2,
                CardSize.NORMAL.height());
        CARD_SELECTOR = new OtherSize(FieldSize.SQUARE.width() * 5 + FieldSize.RECT.width() * 2,
                FieldSize.RECT.height() * 4);
    }
}