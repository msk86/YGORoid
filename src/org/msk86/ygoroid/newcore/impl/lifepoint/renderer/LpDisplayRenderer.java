package org.msk86.ygoroid.newcore.impl.lifepoint.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.lifepoint.LpDisplay;
import org.msk86.ygoroid.size.CalculatorSize;
import org.msk86.ygoroid.size.Size;
import org.msk86.ygoroid.utils.Style;

public class LpDisplayRenderer implements Renderer {
    LpDisplay lpDisplay;

    public LpDisplayRenderer(LpDisplay lpDisplay) {
        this.lpDisplay = lpDisplay;
    }

    @Override
    public Size size() {
        return CalculatorSize.LP_DISPLAY;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        if(lpDisplay.isSelect()) {
            drawFrame(canvas, x, y);
        }
        drawText(canvas, x, y);

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

    private void drawText(Canvas canvas, int x, int y) {
        canvas.save();
        canvas.translate(x + getPadding(), y);

        TextPaint paint = new TextPaint();
        paint.setColor(Style.fontColor());
        paint.setAntiAlias(true);
        paint.setTextSize(getTextFont());

        String txt = "\n"+lpDisplay.getLifePoint() + "\n" + lpDisplay.getNumber() + "\n" + lpDisplay.getResult();
        String ops = lpDisplay.getName() + "\n\n" + lpDisplay.getOperator() + "\n=";

        StaticLayout layout = new StaticLayout(txt, paint, size().width() - getPadding() * 2, Layout.Alignment.ALIGN_OPPOSITE, 1, 0, true);
        layout.draw(canvas);
        layout = new StaticLayout(ops, paint, size().width() - getPadding() * 2, Layout.Alignment.ALIGN_NORMAL, 1, 0, true);
        layout.draw(canvas);
        canvas.restore();
    }

    private int getTextFont() {
        return size().height() / 5;
    }

    private int getPadding() {
        return Style.padding() * 2;
    }
}
