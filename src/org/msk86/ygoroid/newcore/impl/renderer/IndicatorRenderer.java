package org.msk86.ygoroid.newcore.impl.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.Indicator;
import org.msk86.ygoroid.newutils.Style;
import org.msk86.ygoroid.newutils.Utils;
import org.msk86.ygoroid.size.IndicatorSize;
import org.msk86.ygoroid.size.Size;
import org.msk86.ygoroid.newutils.Configuration;

public class IndicatorRenderer implements Renderer {

    private Indicator indicator;

    public IndicatorRenderer(Indicator indicator) {
        this.indicator = indicator;
    }

    @Override
    public Size size() {
        return IndicatorSize.NORMAL;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        if(indicator.getCount() == 0) {
            return;
        }

        Utils.DrawHelper helper = new Utils.DrawHelper(x, y);

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
