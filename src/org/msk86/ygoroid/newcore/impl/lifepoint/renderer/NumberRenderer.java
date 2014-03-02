package org.msk86.ygoroid.newcore.impl.lifepoint.renderer;

import org.msk86.ygoroid.newcore.impl.lifepoint.Numbers;
import org.msk86.ygoroid.size.CalculatorSize;
import org.msk86.ygoroid.size.Size;

public class NumberRenderer extends IconRenderer {
    private Numbers number;
    public NumberRenderer(Numbers number) {
        this.number = number;
    }

    @Override
    public Size size() {
        return CalculatorSize.NUMBER;
    }

    @Override
    public String getText() {
        return number.toString();
    }

    @Override
    public int getTextFont() {
        return size().height() * 2 / 3;
    }
}
