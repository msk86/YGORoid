package org.msk86.ygoroid.newcore.impl.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.Selectable;
import org.msk86.ygoroid.newcore.impl.HighLight;
import org.msk86.ygoroid.size.Size;
import org.msk86.ygoroid.newutils.Configuration;

public class HighLightRenderer implements Renderer {
    HighLight highLight;

    public HighLightRenderer(HighLight highLight) {
        this.highLight = highLight;
    }

    @Override
    public Size size() {
        if(highLight.getSize() != null) {
            return highLight.getSize();
        }
        return highLight.getTarget().getRenderer().size();
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
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
