package org.msk86.ygoroid.newcore.impl.renderer;

import android.graphics.Canvas;
import android.text.TextPaint;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.LifePoint;
import org.msk86.ygoroid.size.OtherSize;
import org.msk86.ygoroid.size.Size;
import org.msk86.ygoroid.utils.Style;

public class LifePointRenderer implements Renderer {
    private LifePoint lifePoint;

    public LifePointRenderer(LifePoint lifePoint) {
        this.lifePoint = lifePoint;
    }

    @Override
    public Size size() {
        return OtherSize.LP;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(size().height());
        textPaint.setColor(Style.fontColor());
        canvas.drawText(lifePoint.toString(), x, y, textPaint);
    }
}
