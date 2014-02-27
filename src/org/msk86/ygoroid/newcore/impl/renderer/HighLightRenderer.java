package org.msk86.ygoroid.newcore.impl.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.Selectable;
import org.msk86.ygoroid.newcore.impl.HighLight;
import org.msk86.ygoroid.size.Size;
import org.msk86.ygoroid.utils.Configuration;

public class HighLightRenderer implements Renderer {
    int x, y;
    HighLight highLight;

    public HighLightRenderer(HighLight highLight) {
        this.highLight = highLight;
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
        return highLight.getTarget().getRenderer().size();
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        this.x = x;
        this.y = y;

        if(!(highLight.getTarget() instanceof Selectable)) {
            return;
        }
        if(!((Selectable)highLight.getTarget()).isSelect()) {
            return;
        }

        Paint paint = new Paint();
        paint.setColor(Configuration.highlightColor());
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);

        canvas.save();
        canvas.translate(x, y);

        Rect highLightRect = new Rect(0, 0, size().width(), size().height());
        canvas.drawRect(highLightRect, paint);
        canvas.restore();
    }
}
