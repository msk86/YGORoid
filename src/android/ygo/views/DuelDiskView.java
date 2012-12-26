package android.ygo.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class DuelDiskView extends View {

    private Paint painter = new Paint();

    public DuelDiskView(Context context) {
        super(context);
    }

    @Override
    public void draw(Canvas canvas){
        drawBackground(canvas);
        painter.setColor(Color.WHITE);
        canvas.drawText("TEST FOR DUEL DISK", 10, 10, painter);
    }

    private void drawBackground(Canvas canvas) {
        // IMAGE BACKGROUND
        painter.setColor(Color.GRAY);
        canvas.drawRect(0,0,100,100,painter);
    }

}
