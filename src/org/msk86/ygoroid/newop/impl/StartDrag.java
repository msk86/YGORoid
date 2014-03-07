package org.msk86.ygoroid.newop.impl;

import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.impl.Duel;

public class StartDrag extends CommonOperation {
    boolean draggable;
    public StartDrag(Duel duel, float fx, float fy) {
        super(duel, fx, fy);
        draggable = true;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public boolean isDraggable() {
        return draggable;
    }

    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }

    @Override
    public int x() {
        return x - item.getRenderer().size().width() / 2;
    }
    @Override
    public int y() {
        return y - item.getRenderer().size().height() / 2;
    }
}
