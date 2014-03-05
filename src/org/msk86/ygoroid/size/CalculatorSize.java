package org.msk86.ygoroid.size;

import org.msk86.ygoroid.newutils.Style;

public class CalculatorSize {
    public static Size OPERATOR, NUMBER, BUTTON, NUMBER_PAD, OPERATION_PAD, LP_DISPLAY, CALCULATOR;

    static {
        OPERATOR = new Size(FieldSize.SQUARE.width() * 2 / 3, FieldSize.SQUARE.width() * 2 / 3);
        NUMBER = new Size(FieldSize.SQUARE.width() * 2 / 3, FieldSize.SQUARE.width() * 2 / 3);
        BUTTON = new Size(FieldSize.SQUARE.width() * 4 / 3 + Style.padding(), FieldSize.SQUARE.width() * 2 / 3);

        NUMBER_PAD = new Size(NUMBER.width() * 3 + Style.padding() * 4, NUMBER.height() * 4 + Style.padding() * 6);
        OPERATION_PAD = new Size(OPERATOR.width(), OPERATOR.height() * 4 + Style.padding() * 6);
        LP_DISPLAY = new Size((int)(NUMBER.width() * 1.5), NUMBER.height() * 2 + Style.padding() * 2);
        CALCULATOR = new Size(OPERATION_PAD.width() + NUMBER_PAD.width() + LP_DISPLAY.width() + Style.padding() * 24, OPERATION_PAD.height() + BUTTON.height() + Style.padding() * 18);
    }
}
