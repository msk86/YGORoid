package android.ygo.core;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class DuelFields implements Item {

    @Override
    public void draw(Canvas canvas, Paint painter) {
        painter.setColor(Color.RED);
        canvas.drawText("DUEL FIELDS TEST", 10, 10, painter);
    }
}
