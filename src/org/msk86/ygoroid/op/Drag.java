package org.msk86.ygoroid.op;

import org.msk86.ygoroid.core.Duel;
import org.msk86.ygoroid.core.Item;
import org.msk86.ygoroid.core.SelectableItem;

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
