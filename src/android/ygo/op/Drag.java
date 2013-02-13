package android.ygo.op;

import android.ygo.action.CloseCardSelectorAction;
import android.ygo.core.*;

public class Drag implements Operation {
    int fromX, fromY;
    int x, y;
    int toX, toY;
    boolean dragging;
    private Duel duel;
    Item from;
    SelectableItem item;
    Item target;

    public Drag(Duel duel, float fx, float fy) {
        this.duel = duel;
        fromX = (int) fx;
        fromY = (int) fy;
        x = fromX;
        y = fromY;
        toX = -1;
        toY = -1;
        dragging = true;
        boolean canSelect = true;
        if (duel.inDuelFields(fromX, fromY)) {
            Field field = duel.fieldAt(fromX, fromY);
            from = field;
            if (field != null) {
                item = field.getItem();
                if (item instanceof CardList) {
                    if(item instanceof Deck) {
                        canSelect = false;
                    }
                    from = item;
                    item = ((CardList) item).pop();
                } else if (item instanceof Overlay) {
                    Overlay overlay = (Overlay) item;
                    if (overlay.topCard().isSelect() || overlay.totalCard() == 1) {
                        item = overlay.removeTopCard();
                        if (overlay.totalCard() > 0) {
                            from = overlay;
                        } else {
                            field.removeItem();
                        }
                    } else {
                        field.removeItem();
                    }
                } else {
                    field.removeItem();
                }
            }
        } else if (duel.inHand(fromX, fromY)) {
            from = duel.getHandCards();
            item = duel.itemAt(fromX, fromY);
            duel.getHandCards().remove((Card) item);
        } else if (duel.inCardSelector(fromX, fromY)) {
            from = duel.getCardSelector().getCardList();
            Card card = duel.getCardSelector().cardAt(fromX, fromY);
            if (card != null) {
                card.open();
                item = duel.getCardSelector().remove(card);
                new CloseCardSelectorAction(this).execute();
            }
        }
        if (item != null && !item.isSelect() && canSelect) {
            duel.select(item);
        }
    }

    public void move(float fx, float fy) {
        this.x = (int) fx;
        this.y = (int) fy;
    }

    public Item dropTo(float tx, float ty) {
        toX = (int) tx;
        toY = (int) ty;
        if (duel.inHand(toX, toY)) {
            target = duel.getHandCards();
        } else if (duel.inDuelFields(toX, toY)) {
            target = duel.fieldAt(toX, toY);
        }
        dragging = false;
        return target;
    }

    public Item getFrom() {
        return from;
    }

    @Override
    public Duel getDuel() {
        return duel;
    }

    @Override
    public SelectableItem getItem() {
        return item;
    }

    @Override
    public Item getContainer() {
        return target;
    }

    @Override
    public int x() {
        return x;
    }

    @Override
    public int y() {
        return y;
    }
}
