package android.ygo.touch;

import android.ygo.core.*;

public class Drag implements Touch {
    int fromX, fromY;
    int x, y;
    int toX, toY;
    boolean dragging;
    private Duel duel;
    SelectableItem item;
    Item target;

    public Drag(Duel duel, float fx, float fy) {
        this.duel = duel;
        fromX = (int)fx;
        fromY = (int)fy;
        x = fromX;
        y = fromY;
        toX = -1;
        toY = -1;
        dragging = true;
        if(duel.inDuelFields(fromX, fromY)) {
            Field field = duel.fieldAt(fromX, fromY);
            if(field != null) {
                item = field.removeItem();
            }
        } else if(duel.inHand(fromX, fromY)) {
            item = duel.itemAt(fromX, fromY);
            duel.getHandCards().remove((Card)item);
        }
    }

    public void move(float fx, float fy) {
        this.x = (int)fx;
        this.y = (int)fy;
    }

    public Item dropTo(float tx, float ty) {
        toX = (int)tx;
        toY = (int)ty;
        if(duel.inHand(toX, toY)){
            target = duel.getHandCards();
        } else if(duel.inDuelFields(toX, toY)) {
            target = duel.fieldAt(toX, toY);
        }
        dragging = false;
        if(target instanceof HandCards) {
            ((HandCards)target).add((Card) item);
        } else if(target instanceof Field) {
            ((Field)target).setItem(item);
        }
        return target;
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

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }
}
