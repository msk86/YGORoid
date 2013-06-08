package android.ygo.layout;

import android.graphics.Canvas;
import android.ygo.core.Card;
import android.ygo.core.Drawable;
import android.ygo.utils.Utils;

import java.util.List;

public class LinerLayout implements Drawable {
    List<Card> cards;

    int maxWidth;
    int cardPadding;

    public LinerLayout(List<Card> cards, int maxWidth) {
        this.maxWidth = maxWidth;
        this.cards = cards;
    }

    public void fixPosition() {
        int maxPadding = Utils.cardWidth() / 10;
        if (cards.size() > 1) {
            cardPadding = (maxWidth - Utils.cardWidth() + 1) / (cards.size() - 1) - Utils.cardWidth();
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

        int posX = - (Utils.cardHeight() - Utils.cardWidth()) / 2 + 1;
        int posY;
        Utils.DrawHelper helper = new Utils.DrawHelper(x, y);
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            posY = Utils.cardHeight() / 7;
            if (card.isSelect()) {
                posY = 0;
            }
            helper.drawDrawable(canvas, card, posX, posY);
            posX += Utils.cardWidth() + cardPadding;
        }
    }

    @Override
    public int width() {
        return cards.size() * Utils.cardWidth() + (cards.size() - 1) * cardPadding + 1;
    }

    @Override
    public int height() {
        return Utils.cardHeight();
    }

    public Card cardAt(int x, int y) {
        fixPosition();
        if (x < 0 || x >= width()) {
            return null;
        }

        int index = x / (Utils.cardWidth() + cardPadding);
        index = index < cards.size() ? index : cards.size() - 1;
        return cards.get(index);
    }
}
