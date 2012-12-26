package android.ygo.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.ygo.core.Card;
import android.ygo.core.CardType;
import android.ygo.core.DuelFields;
import android.ygo.core.Field;

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
        Card card = new Card("12345678", CardType.SYNC_MONSTER, false);
        Field f = duelFields.testField();
        f.setItem(card);
        canvas.drawBitmap(duelFields.toBitmap(), 0, 0, painter);
    }

    private void drawBackground(Canvas canvas) {
        canvas.drawColor(Color.GRAY);
        // IMAGE BACKGROUND
    }

}
