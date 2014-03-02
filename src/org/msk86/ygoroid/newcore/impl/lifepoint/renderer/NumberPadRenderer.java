package org.msk86.ygoroid.newcore.impl.lifepoint.renderer;

import android.graphics.Canvas;
import android.graphics.Point;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.layout.GridLayout;
import org.msk86.ygoroid.newcore.impl.lifepoint.NumberPad;
import org.msk86.ygoroid.size.CalculatorSize;
import org.msk86.ygoroid.size.Size;
import org.msk86.ygoroid.utils.Style;

public class NumberPadRenderer implements Renderer {
    private NumberPad numberPad;

    public NumberPadRenderer(NumberPad numberPad) {
        this.numberPad = numberPad;
        ((GridLayout) numberPad.getLayout()).setMaxPaddingX(Style.padding() * 2)
                .setPaddingY(Style.padding() * 2)
                .setGridSize(CalculatorSize.NUMBER);
    }

    @Override
    public Size size() {
        return CalculatorSize.NUMBER_PAD;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        canvas.save();
        canvas.translate(x, y);
        for (Item item : numberPad.getLayout().items()) {
            Point pos = numberPad.getLayout().itemPosition(item);
            item.getRenderer().draw(canvas, pos.x ,pos.y);
        }
        canvas.restore();
    }
}
