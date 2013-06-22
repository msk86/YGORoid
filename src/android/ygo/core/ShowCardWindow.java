package android.ygo.core;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;

public class ShowCardWindow implements Item, Drawable {
    Card card;

    public ShowCardWindow(Card card) {
        this.card = card;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        drawBackground(canvas, x, y);

        Utils.DrawHelper helper = new Utils.DrawHelper(x, y);
        helper.drawBitmap(canvas, card.bigCardPic(), 0, 0, new Paint());
    }

    public void drawBackground(Canvas canvas, int x, int y) {
        Utils.DrawHelper helper = new Utils.DrawHelper(x, y);
        Paint paint = new Paint();
        paint.setColor(Configuration.showCardWindowBackgroundColor());
        helper.drawRect(canvas, new Rect(0, 0, width(), height()), paint);
    }

    @Override
    public int width() {
        return Utils.unitLength() * 6;
    }

    @Override
    public int height() {
        return Utils.screenHeight();
    }
}
