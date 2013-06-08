package android.ygo.core;

import android.graphics.Canvas;
import android.ygo.utils.Utils;

public class OverRay implements SelectableItem, Drawable {
    private boolean selected = false;

    Card topCard;
    CardList overRayUnits;

    public OverRay(Card card) {
        overRayUnits = new CardList("OVERRAY");
        overRay(card);
    }

    public CardList getOverRayUnits() {
        return overRayUnits;
    }

    public void overRay(Card card) {
        if (topCard == null) {
            topCard = card;
        } else {
            if (topCard.getSubTypes().contains(CardSubType.XYZ)
                    && !card.getSubTypes().contains(CardSubType.XYZ)) {
                overRayUnits.push(card);
            } else {
                overRayUnits.push(topCard);
                topCard = card;
            }
        }
        topCard.open();
    }

    public int totalCard() {
        int count = overRayUnits.cards.size();
        if (topCard != null) {
            count++;
        }
        return count;
    }

    public Card topCard() {
        return topCard;
    }

    public Card removeTopCard() {
        Card top = topCard;
        Card card = overRayUnits.pop();
        topCard = card;
        return top;
    }

    public void adjust(Field field) {
        if (totalCard() == 1) {
            field.removeItem();
            field.setItem(topCard);
        }
    }

    private int overRayOffset() {
        return Utils.cardWidth() / 15;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        Utils.DrawHelper helper = new Utils.DrawHelper(x, y);

        drawUnits(canvas, x + overRayOffset(), y);

        helper.drawDrawable(canvas, topCard, helper.center(width(), topCard.width()), 0);
    }

    private void drawUnits(Canvas canvas, int x, int y) {
        Utils.DrawHelper helper = new Utils.DrawHelper(x, y);
        int startShowIndex = overRayUnits.cards.size() - 1;
        startShowIndex = startShowIndex > 3 ? 3 : startShowIndex;
        for (int i = startShowIndex; i >= 0; i--) {
            Card card = overRayUnits.getCards().get(i);
            helper.drawDrawable(canvas, card, i * overRayOffset(), 0);
        }
    }

    @Override
    public int width() {
        return Utils.cardHeight();
    }

    @Override
    public int height() {
        return Utils.cardHeight();
    }

    @Override
    public void select() {
        Card topCard = topCard();
        if (topCard != null) {
            topCard.select();
        }
        selected = true;
    }

    @Override
    public void unSelect() {
        selected = false;
        Card topCard = topCard();
        if (topCard != null) {
            topCard.unSelect();
        }
    }

    @Override
    public boolean isSelect() {
        return selected;
    }
}
