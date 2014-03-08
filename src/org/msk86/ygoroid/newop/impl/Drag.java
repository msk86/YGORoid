package org.msk86.ygoroid.newop.impl;

import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.impl.Duel;
import org.msk86.ygoroid.newop.Operation;
import org.msk86.ygoroid.newutils.LayoutUtils;

public class Drag implements Operation {
    Item item;
    Container container;
    Duel duel;
    int x, y;
    StartDrag startDrag;
    boolean dragging;

    public Drag(Duel duel, float fx, float fy, StartDrag startDrag) {
        item = startDrag.getDragItem();
        this.duel = duel;
        this.startDrag = startDrag;
        move(fx, fy);
    }

    public void move(float fx, float fy) {
        this.x = (int) fx;
        this.y = (int) fy;
    }

    public Container dropTo(float fx, float fy) {
        x = (int) fx;
        y = (int) fy;
        container = LayoutUtils.containerAt(duel, x,  y);
        dragging = false;
        return container;
    }

    public StartDrag getStartDrag() {
        return startDrag;
    }

    @Override
    public Duel getDuel() {
        return duel;
    }

    @Override
    public Item getItem() {
        return item;
    }

    @Override
    public Container getContainer() {
        return container;
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
