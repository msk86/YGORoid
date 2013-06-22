package android.ygo.core;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.ygo.layout.GridLayout;
import android.ygo.layout.LinerLayout;
import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;

import java.util.List;

public class SideWindow implements Item, Drawable {

    GridLayout mainLayout;
    LinerLayout exLayout;
    LinerLayout sideLayout;

    public SideWindow(List<Card> main, List<Card> ex, List<Card> side) {
        mainLayout = new GridLayout(main, width(), 3);
        exLayout = new LinerLayout(ex, width(), 0);
        sideLayout = new LinerLayout(side, width(), 0);


    }


    @Override
    public void draw(Canvas canvas, int x, int y) {
        drawBackground(canvas, x, y);

        Utils.DrawHelper helper = new Utils.DrawHelper(x, y);
        helper.drawDrawable(canvas, mainLayout, 0, 0);
        helper.drawDrawable(canvas, exLayout, 0, mainLayout.height() + 3);
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

    public Card cardAt(int x, int y) {
        if(y < mainLayout.height()) {
            return mainLayout.cardAt(x, y);
        } else {
            return exLayout.cardAt(x, y - mainLayout.height());
        }
    }
}
