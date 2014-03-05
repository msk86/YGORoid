package org.msk86.ygoroid.newcore.impl.renderer;

import android.graphics.Canvas;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.LifePoint;
import org.msk86.ygoroid.size.OtherSize;
import org.msk86.ygoroid.size.Size;
import org.msk86.ygoroid.utils.Configuration;
import org.msk86.ygoroid.newutils.Style;

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
        textPaint.setTextSize(getFontSize());
        textPaint.setColor(Style.fontColor());
        textPaint.setShadowLayer(1, 0, 0, Configuration.textShadowColor());

        StaticLayout layout = new StaticLayout(lifePoint.toString(), textPaint, size().width(), Layout.Alignment.ALIGN_NORMAL, 1, 0, true);
        canvas.save();
        int lineCount = layout.getLineCount();
        int textHeight = 0;
        for(int i=0;i<lineCount;i++) {
            textHeight += layout.getLineBottom(i) - layout.getLineTop(i);
        }
        int offsetY = (size().height() - textHeight) / 2;
        canvas.translate(x, y + offsetY);
        layout.draw(canvas);
        canvas.restore();
    }

    private int getFontSize() {
        return size().height() / 4;
    }
}
