package org.msk86.ygoroid.newcore.impl.lifepoint.renderer;

import android.graphics.Canvas;
import android.graphics.Point;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.layout.GridLayout;
import org.msk86.ygoroid.newcore.impl.lifepoint.OperationPad;
import org.msk86.ygoroid.size.CalculatorSize;
import org.msk86.ygoroid.size.Size;
import org.msk86.ygoroid.newutils.Style;

public class OperationPadRenderer implements Renderer {
    OperationPad operationPad;

    public OperationPadRenderer(OperationPad operationPad) {
        this.operationPad = operationPad;
        ((GridLayout) operationPad.getLayout()).setMaxPaddingX(Style.padding() * 2)
                .setPaddingY(Style.padding() * 2)
                .setGridSize(CalculatorSize.OPERATOR);
    }

    @Override
    public Size size() {
        return CalculatorSize.OPERATION_PAD;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        canvas.save();
        canvas.translate(x, y);
        for (Item item : operationPad.getLayout().items()) {
            Point pos = operationPad.getLayout().itemPosition(item);
            item.getRenderer().draw(canvas, pos.x ,pos.y);
        }
        canvas.restore();
    }
}
