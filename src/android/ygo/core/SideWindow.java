package android.ygo.core;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.ygo.layout.GridLayout;
import android.ygo.layout.Layout;
import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;

import java.util.List;

public class SideWindow implements Item, Drawable {

    GridLayout mainLayout;
    GridLayout exLayout;
    GridLayout sideLayout;

    private int padding = 3;

    private Card selectCard;
    private Layout selectLayout;

    public SideWindow(List<Card> main, List<Card> ex, List<Card> side) {
        mainLayout = new GridLayout(main, width(), 3, Utils.cardSnapshotWidth(), Utils.cardSnapshotHeight());
        exLayout = new GridLayout(ex, width(), 1, Utils.cardSnapshotWidth(), Utils.cardSnapshotHeight());
        sideLayout = new GridLayout(side, width(), 1, Utils.cardSnapshotWidth(), Utils.cardSnapshotHeight());
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        drawBackground(canvas, x, y);

        Utils.DrawHelper helper = new Utils.DrawHelper(x, y);
        helper.drawDrawable(canvas, mainLayout, 0, 0);
        helper.drawDrawable(canvas, exLayout, 0, mainLayout.height() + padding);
        helper.drawDrawable(canvas, sideLayout, 0, mainLayout.height() + exLayout.height() + padding * 3);
    }

    public void drawBackground(Canvas canvas, int x, int y) {
        Utils.DrawHelper helper = new Utils.DrawHelper(x, y);
        Paint paint = new Paint();
        paint.setColor(Configuration.windowBackgroundColor());
        paint.setAlpha(150);
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
        if (y < mainLayout.height()) {
            return mainLayout.cardAt(x, y);
        } else if (y < mainLayout.height() + exLayout.height() + padding) {
            return exLayout.cardAt(x, y - mainLayout.height() - padding);
        } else {
            return sideLayout.cardAt(x, y - mainLayout.height() - exLayout.height() - padding * 3);
        }
    }

    public void setSelectCard(Card card, Layout layout) {
        this.selectCard = card;
        this.selectLayout = layout;
    }

    public Layout layoutAt(int x, int y) {
        if (x > Utils.unitLength() * 6) {
            return null;
        }
        if (y < mainLayout.height()) {
            return mainLayout;
        } else if (y < mainLayout.height() + exLayout.height() + padding) {
            return exLayout;
        } else {
            return sideLayout;
        }
    }

    public Card getSelectCard() {
        return selectCard;
    }

    public Layout getSelectLayout() {
        return selectLayout;
    }

    public void clearSelect() {
        selectCard = null;
        selectLayout = null;
    }

    public GridLayout getMainLayout() {
        return mainLayout;
    }

    public GridLayout getExLayout() {
        return exLayout;
    }

    public GridLayout getSideLayout() {
        return sideLayout;
    }
}
