package org.msk86.ygoroid.newcore.impl.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.Indicator;
import org.msk86.ygoroid.size.IndicatorSize;
import org.msk86.ygoroid.size.Size;
import org.msk86.ygoroid.utils.Configuration;
import org.msk86.ygoroid.utils.Style;
import org.msk86.ygoroid.utils.Utils2;

public class IndicatorRenderer implements Renderer {

    private Indicator indicator;

    private int x, y;

    public IndicatorRenderer(Indicator indicator) {
        this.indicator = indicator;
    }

    @Override
    public int x() {
        return x;
    }

    @Override
    public int y() {
        return y;
    }

    @Override
    public Size size() {
        return IndicatorSize.NORMAL;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        this.x = x;
        this.y = y;

        if(indicator.getCount() == 0) {
            return;
        }

        Utils2.DrawHelper helper = new Utils2.DrawHelper(x, y);

        Paint paint = new Paint();
        paint.setColor(Configuration.lineColor());
        paint.setStrokeWidth(Style.border());
        paint.setStyle(Paint.Style.STROKE);

        Rect indicatorRect = new Rect(0, 0, size().width(), size().height());
        helper.drawRect(canvas, indicatorRect, paint);

        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(defaultFontSize());
        textPaint.setColor(Configuration.fontColor());
        textPaint.setShadowLayer(1, 0, 0, Configuration.textShadowColor());
        textPaint.setAntiAlias(true);
        StaticLayout layout = new StaticLayout(String.valueOf(indicator.getCount()), textPaint, size().width(), Layout.Alignment.ALIGN_CENTER, 1, 0, true);
        while (layout.getLineCount() > 1) {
            textPaint.setTextSize(textPaint.getTextSize() * 0.9f);
            layout = new StaticLayout(String.valueOf(indicator.getCount()), textPaint, size().width(), Layout.Alignment.ALIGN_CENTER, 1, 0, true);
            if(textPaint.getTextSize() < 1) {
                break;
            }
        }
        helper.drawLayout(canvas, layout, 0, 0);
    }

    private int defaultFontSize() {
        return size().width() - Style.border() * 2;
    }
}
