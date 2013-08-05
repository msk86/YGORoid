package android.ygo.layout;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.ygo.YGOActivity;
import android.ygo.core.Card;
import android.ygo.core.Drawable;
import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;

import java.util.List;

public class LinerLayout implements Layout, Drawable {
    List<Card> cards;

    int maxWidth;
    int cardPadding;

    int paddingY = 0;
    private int unitWidth;
    private int unitHeight;

    public LinerLayout(List<Card> cards, int maxWidth) {
        this(cards, maxWidth, 0);
    }

    public LinerLayout(List<Card> cards, int maxWidth, int paddingY) {
        this(cards, maxWidth, paddingY, Utils.cardWidth(), Utils.cardHeight());
    }

    public LinerLayout(List<Card> cards, int maxWidth, int paddingY, int unitWidth, int unitHeight) {
        this.maxWidth = maxWidth;
        this.cards = cards;
        this.paddingY = paddingY;
        this.unitWidth = unitWidth;
        this.unitHeight = unitHeight;
    }

    public void fixPosition() {
        int maxPadding = unitWidth / 10;
        if (cards.size() > 1) {
            cardPadding = (maxWidth - unitWidth + 1) / (cards.size() - 1) - unitWidth;
            cardPadding = cardPadding < maxPadding ? cardPadding : maxPadding;
        } else if (cards.size() == 1) {
            cardPadding = maxPadding;
        } else {
            cardPadding = 0;
        }
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        fixPosition();

        int posX = 1;
        int posY;
        Utils.DrawHelper helper = new Utils.DrawHelper(x, y);
        for (Card card : cards) {
            posY = paddingY;
            if (card.isSelect()) {
                posY = 0;
            }
            if (card.isOpen()) {
                helper.drawBitmap(canvas, card.getBmpCache().get(unitWidth, unitHeight), posX, posY, new Paint());
            } else {
                helper.drawBitmap(canvas, Card.CARD_PROTECTOR, posX, posY, new Paint());
            }
            if(card.isSelect()) {
                drawHighLight(canvas, x + posX, y + posY);
            }
            posX += unitWidth + cardPadding;
        }
    }

    private void drawHighLight(Canvas canvas, int x, int y) {
        Utils.DrawHelper helper = new Utils.DrawHelper(x, y);
        Paint paint = new Paint();
        paint.setColor(Configuration.highlightColor());
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);

        Rect highLightRect = new Rect(0, 0, unitWidth, unitHeight);
        helper.drawRect(canvas, highLightRect, paint);
    }

    @Override
    public int width() {
        return cards.size() * unitWidth + (cards.size() - 1) * cardPadding + 1;
    }

    @Override
    public int height() {
        return unitHeight + cardPadding;
    }

    @Override
    public Card cardAt(int x, int y) {
        fixPosition();
        if (x < 0 || x >= width()) {
            return null;
        }

        int index = x / (unitWidth + cardPadding);
        index = index < cards.size() ? index : cards.size() - 1;
        return cards.get(index);
    }

    @Override
    public List<Card> cards() {
        return cards;
    }
}
