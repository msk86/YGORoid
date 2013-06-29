package android.ygo.core;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.ygo.layout.GridLayout;
import android.ygo.layout.Layout;
import android.ygo.layout.LinerLayout;
import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;

import java.util.List;

public class SideWindow implements Item, Drawable {

    GridLayout mainLayout;
    LinerLayout exLayout;
    GridLayout sideLayout;

    boolean showMain = true;
    private Card selectCardMain;
    private Card selectCardSide;
    private Layout selectLayoutMain;
    private Layout selectLayoutSide;

    public SideWindow(List<Card> main, List<Card> ex, List<Card> side) {
        mainLayout = new GridLayout(main, width(), 3);
        exLayout = new LinerLayout(ex, width(), 0);
        sideLayout = new GridLayout(side, width(), 2);
    }

    public void flip() {
        showMain = !showMain;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        drawBackground(canvas, x, y);

        Utils.DrawHelper helper = new Utils.DrawHelper(x, y);
        if (showMain) {
            helper.drawDrawable(canvas, mainLayout, 0, 0);
            helper.drawDrawable(canvas, exLayout, 0, mainLayout.height() + 3);
        } else {
            helper.drawDrawable(canvas, sideLayout, 0, Utils.cardHeight());
        }
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
        if (showMain) {
            if (y < mainLayout.height()) {
                return mainLayout.cardAt(x, y);
            } else {
                return exLayout.cardAt(x, y - mainLayout.height());
            }
        } else {
            return sideLayout.cardAt(x, y - Utils.cardHeight());
        }
    }

    public void setSelectCard(Card card, Layout layout) {
        if(showMain) {
            this.selectCardMain = card;
            this.selectLayoutMain = layout;
        } else {
            this.selectCardSide = card;
            this.selectLayoutSide = layout;
        }
    }

    public Layout layoutAt(int x, int y) {
        if(x > Utils.unitLength() * 6) {
            return null;
        }
        if (showMain) {
            if (y < mainLayout.height()) {
                return mainLayout;
            } else {
                return exLayout;
            }
        } else {
            return sideLayout;
        }
    }

    public Card getSelectCardMain() {
        return selectCardMain;
    }

    public Card getSelectCardSide() {
        return selectCardSide;
    }

    public Layout getSelectLayoutMain() {
        return selectLayoutMain;
    }

    public Layout getSelectLayoutSide() {
        return selectLayoutSide;
    }

    public void clearSelect() {
        selectCardMain = null;
        selectCardSide = null;
        selectLayoutMain = null;
        selectLayoutSide = null;
    }

    public GridLayout getMainLayout() {
        return mainLayout;
    }

    public LinerLayout getExLayout() {
        return exLayout;
    }
}
