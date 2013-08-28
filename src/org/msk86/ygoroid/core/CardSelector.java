package org.msk86.ygoroid.core;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import org.msk86.ygoroid.layout.GridLayout;
import org.msk86.ygoroid.utils.Configuration;
import org.msk86.ygoroid.utils.Utils;

public class CardSelector implements Item, Drawable {
    SelectableItem sourceItem;
    CardList cardList;
    GridLayout layout;

    public CardSelector(SelectableItem sourceItem, CardList cardList) {
        this.sourceItem = sourceItem;
        this.cardList = cardList;
        layout = new GridLayout(cardList.cards, Utils.totalWidth(), 4);
    }

    public SelectableItem getSourceItem() {
        return sourceItem;
    }

    public CardList getCardList() {
        return cardList;
    }

    public Card cardAt(int x, int y) {
        return layout.cardAt(x, y);
    }

    public Card remove(Card card) {
        return cardList.remove(card);
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        drawBackground(canvas, x, y);

        Utils.DrawHelper helper = new Utils.DrawHelper(x, y);

        helper.drawDrawable(canvas, layout, x, y);
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
        return Utils.totalWidth();
    }

    @Override
    public int height() {
        return Utils.screenHeight();
    }
}
