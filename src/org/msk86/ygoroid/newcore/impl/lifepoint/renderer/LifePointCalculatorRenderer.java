package org.msk86.ygoroid.newcore.impl.lifepoint.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Layout;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.layout.AbsoluteLayout;
import org.msk86.ygoroid.newcore.impl.lifepoint.LifePointCalculator;
import org.msk86.ygoroid.size.CalculatorSize;
import org.msk86.ygoroid.size.Size;
import org.msk86.ygoroid.utils.Style;

public class LifePointCalculatorRenderer implements Renderer {
    LifePointCalculator lifePointCalculator;

    public LifePointCalculatorRenderer(LifePointCalculator lifePointCalculator) {
        this.lifePointCalculator = lifePointCalculator;
        AbsoluteLayout layout = (AbsoluteLayout) this.lifePointCalculator.getLayout();
        layout.addItem(lifePointCalculator.getLpDisplay(0), padding(), padding());
        layout.addItem(lifePointCalculator.getLpDisplay(1), padding(), lifePointCalculator.getLpDisplay(0).getRenderer().size().height() + padding() * 2);
        layout.addItem(lifePointCalculator.getOperationPad(), lifePointCalculator.getLpDisplay(0).getRenderer().size().width() + padding() * 2, padding());
        layout.addItem(lifePointCalculator.getNumberPad(), lifePointCalculator.getOperationPad().getRenderer().size().width() + lifePointCalculator.getLpDisplay(0).getRenderer().size().width() + padding() * 3, padding());
        layout.addItem(lifePointCalculator.getOkButton(), padding() * 4, lifePointCalculator.getNumberPad().getRenderer().size().height() + padding() * 2);
        layout.addItem(lifePointCalculator.getCancelButton(), size().width() - lifePointCalculator.getCancelButton().getRenderer().size().width() - padding() * 4, lifePointCalculator.getNumberPad().getRenderer().size().height() + padding() * 2);
    }

    private int padding() {
        return Style.padding() * 6;
    }

    @Override
    public Size size() {
        return CalculatorSize.CALCULATOR;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        drawFrame(canvas, x, y);
        drawItems(canvas, x, y);
    }

    private void drawItems(Canvas canvas, int x, int y) {
        canvas.save();
        canvas.translate(x, y);
        Layout layout = lifePointCalculator.getLayout();
        for (Item item : layout.items()) {
            Point point = layout.itemPosition(item);
            item.getRenderer().draw(canvas, point.x, point.y);
        }
    }


    private void drawFrame(Canvas canvas, int x, int y) {
        canvas.save();
        canvas.translate(x, y);

        RectF rect = new RectF(0, 0, size().width(), size().height());

        Paint paint = new Paint();
        paint.setColor(Style.fieldShadowColor());
        paint.setAlpha(100);
        canvas.drawRoundRect(rect, 7, 7, paint);

        paint.setColor(Style.fontColor());
        paint.setAlpha(255);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        canvas.drawRoundRect(rect, 7, 7, paint);

        canvas.restore();
    }
}
