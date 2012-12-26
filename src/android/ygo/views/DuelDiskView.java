package android.ygo.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.ygo.core.DuelFields;

public class DuelDiskView extends View {

    private Paint painter;

    private DuelFields duelFields;

    public DuelDiskView(Context context) {
        super(context);
        initDuelDisk();
    }

    private void initDuelDisk() {
        painter = new Paint();
        duelFields = new DuelFields();
    }

    @Override
    public void draw(Canvas canvas){
        drawBackground(canvas);
        duelFields.draw(canvas, painter);
    }

    private void drawBackground(Canvas canvas) {
        canvas.drawColor(Color.GRAY);
        // IMAGE BACKGROUND
    }

}
