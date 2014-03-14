package org.msk86.ygoroid.newop.impl;

import org.msk86.ygoroid.newcore.BaseContainer;
import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.impl.Duel;
import org.msk86.ygoroid.newop.Operation;
import org.msk86.ygoroid.newutils.LayoutUtils;

public class Drag implements Operation {
    Container baseContainer;
    Item item;
    Container container;
    int x, y;
    StartDrag startDrag;
    boolean dragging;

    public Drag(BaseContainer baseContainer, float fx, float fy, StartDrag startDrag) {
        item = startDrag.getDragItem();
        this.baseContainer = baseContainer;
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
        container = LayoutUtils.containerAt(baseContainer, x,  y);
        dragging = false;
        return container;
    }

    public StartDrag getStartDrag() {
        return startDrag;
    }

    public Duel getDuel() {
        return (Duel) getBaseContainer();
    }

    @Override
    public Container getBaseContainer() {
        return baseContainer;
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
