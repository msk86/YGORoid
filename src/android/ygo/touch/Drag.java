package android.ygo.touch;

import android.ygo.core.*;

public class Drag {
    int fromX, fromY;
    int x, y;
    int toX, toY;
    boolean dragging;
    private Duel duel;
    SelectableItem item;
    Item target;

    public Drag(Duel duel, int fx, int fy) {
        this.duel = duel;
        fromX = fx;
        fromY = fy;
        x = fx;
        y = fy;
        toX = -1;
        toY = -1;
        dragging = true;
        if(duel.inDuelFields(fx, fy)) {
            Field field = duel.fieldAt(fx, fy);
            item = field.removeItem();
        } else if(duel.inHand(fx, fy)) {
            item = duel.itemAt(fx, fy);
            duel.getHandCards().remove((Card)item);
        }
        duel.setDraggingItem(item, x, y);
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
        duel.setDraggingItem(item, x, y);
    }

    public Item dropTo(int tx, int ty) {
        toX = tx;
        toY = ty;
        if(duel.inHand(tx, ty)){
            target = duel.getHandCards();
        } else if(duel.inDuelFields(tx, ty)) {
            target = duel.fieldAt(tx, ty);
        }
        duel.setDraggingItem(null, -1, -1);
        dragging = false;
        if(target instanceof HandCards) {
            ((HandCards)target).add((Card) item);
        } else if(target instanceof Field) {
            ((Field)target).setItem(item);
        }
        return target;
    }
}
