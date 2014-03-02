package org.msk86.ygoroid.newcore.impl.lifepoint.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.utils.Style;

public abstract class IconRenderer implements Renderer {
    public abstract String getText();
    public abstract int getTextFont();

    @Override
    public void draw(Canvas canvas, int x, int y) {
        drawFrame(canvas, x, y);
        drawText(canvas, x, y);
    }

    private void drawText(Canvas canvas, int x, int y) {
        TextPaint paint = new TextPaint();
        paint.setColor(Style.fontColor());
        paint.setAntiAlias(true);
        paint.setTextSize(getTextFont());
        StaticLayout layout = new StaticLayout(getText(), paint, size().width(), Layout.Alignment.ALIGN_CENTER, 1, 0, true);
        int lineHeight = layout.getHeight();
        canvas.save();
        canvas.translate(x, y);
        canvas.translate(0, (size().height() - lineHeight) / 2);
        layout.draw(canvas);
        canvas.restore();

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
