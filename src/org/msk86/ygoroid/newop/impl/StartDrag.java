package org.msk86.ygoroid.newop.impl;

import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.impl.Duel;

public class StartDrag extends CommonOperation {
    Item dragItem;

    public StartDrag(Duel duel, float fx, float fy) {
        super(duel, fx, fy);
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getDragItem() {
        return dragItem;
    }

    public void setDragItem(Item dragItem) {
        this.dragItem = dragItem;
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
