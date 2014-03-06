package org.msk86.ygoroid.newcore.impl.lifepoint.renderer;

import org.msk86.ygoroid.newcore.impl.lifepoint.Button;
import org.msk86.ygoroid.size.CalculatorSize;
import org.msk86.ygoroid.size.Size;

public class ButtonRenderer extends IconRenderer {
    private Button button;

    public ButtonRenderer(Button button) {
        this.button = button;
    }

    @Override
    public Size size() {
        if(button.getSize() == 1) {
            return CalculatorSize.NUMBER;
        } else {
            return CalculatorSize.BUTTON;
        }
    }

    @Override
    public String getText() {
        return button.toString();
    }

    @Override
    public int getTextFont() {
        return size().height() * 2 / 3;
    }
}
