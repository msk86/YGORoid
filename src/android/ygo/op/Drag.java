package android.ygo.op;

import android.ygo.core.Duel;
import android.ygo.core.Item;
import android.ygo.core.SelectableItem;

public class Drag implements Operation {
    StartDrag startDrag;
    int x, y;
    boolean dragging;
    private Duel duel;
    SelectableItem item;
    Item target;

    public Drag(StartDrag startDrag, Duel duel, float fx, float fy) {
        this.startDrag = startDrag;
        this.duel = duel;
        x = (int) fx;
        y = (int) fy;
        dragging = true;

        item = startDrag.getDragItem();


//        if (duel.inDuelFields(fromX, fromY)) {
//            Field field = duel.fieldAt(fromX, fromY);
//            from = field;
//            if (field != null) {
//                item = field.getItem();
//                if (item instanceof CardList) {
//                    if(item instanceof Deck) {
//                        canSelect = false;
//                    }
//                    from = item;
//                    item = ((CardList) item).pop();
//                } else if (item instanceof Overlay) {
//                    Overlay overlay = (Overlay) item;
//                    if (overlay.topCard().isSelect() || overlay.totalCard() == 1) {
//                        item = overlay.removeTopCard();
//                        if (overlay.totalCard() > 0) {
//                            from = overlay;
//                        } else {
//                            field.removeItem();
//                        }
//                    } else {
//                        field.removeItem();
//                    }
//                } else {
//                    field.removeItem();
//                }
//            }
//        } else if (duel.inHand(fromX, fromY)) {
//            from = duel.getHandCards();
//            item = duel.itemAt(fromX, fromY);
//            duel.getHandCards().remove((Card) item);
//        } else if (duel.inCardSelector(fromX, fromY)) {
//            from = duel.getCardSelector().getCardList();
//            Card card = duel.getCardSelector().cardAt(fromX, fromY);
//            if (card != null) {
//                card.open();
//                item = duel.getCardSelector().remove(card);
//                new CloseCardSelectorAction(this).execute();
//            }
//        }
//        if (item != null && !item.isSelect() && canSelect) {
//            duel.select(item);
//        }
    }

    public void move(float fx, float fy) {
        this.x = (int) fx;
        this.y = (int) fy;
    }

    public Item dropTo(float tx, float ty) {
        x = (int) tx;
        y = (int) ty;
        if (duel.inHand(x, y)) {
            target = duel.getHandCards();
        } else if (duel.inDuelFields(x, y)) {
            target = duel.fieldAt(x, y);
        }
        dragging = false;
        return target;
    }

    public StartDrag getStartDrag() {
        return startDrag;
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
