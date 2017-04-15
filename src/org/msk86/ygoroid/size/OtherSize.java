package org.msk86.ygoroid.size;

import org.msk86.ygoroid.newutils.Utils;

public class OtherSize extends Size {
    public OtherSize(int width, int height) {
        super(width, height);
    }

    public static Size SCREEN, TOTAL, HAND_CARDS, CARD_SELECTOR, COIN, DICE, DUEL_FIELDS, LP, CALCULATOR;

    static {
        SCREEN = new OtherSize(Utils.screenWidth(), Utils.screenHeight());
        TOTAL = new OtherSize(FieldSize.SQUARE.width() * 5 + FieldSize.RECT.width() * 2,
                FieldSize.RECT.height() * 4);
        HAND_CARDS = new OtherSize(FieldSize.SQUARE.width() * 5 + FieldSize.RECT.width() * 2 - CardSize.NORMAL.width() / 2,
                CardSize.NORMAL.height());
        CARD_SELECTOR = new OtherSize(FieldSize.SQUARE.width() * 5 + FieldSize.RECT.width() * 2,
                FieldSize.RECT.height() * 4);
        COIN = new OtherSize((FieldSize.SQUARE.width() - 10) / 2, (FieldSize.SQUARE.width() - 10) / 2);
        DICE = new OtherSize((FieldSize.SQUARE.width() - 10) / 2, (FieldSize.SQUARE.width() - 10) / 2);
        DUEL_FIELDS = new OtherSize(FieldSize.SQUARE.width() * 5 + FieldSize.RECT.width() * 2, FieldSize.SQUARE.height() * 4);
        LP = new OtherSize(FieldSize.SQUARE.width(), FieldSize.SQUARE.height());
        CALCULATOR = new OtherSize(FieldSize.SQUARE.width() * 5 + FieldSize.RECT.width() * 2,
                FieldSize.RECT.height() * 4);
    }
}
