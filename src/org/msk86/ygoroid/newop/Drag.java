package org.msk86.ygoroid.newop;

import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.Selectable;
import org.msk86.ygoroid.newcore.impl.Duel;
import org.msk86.ygoroid.newutils.LayoutUtils;

public class Drag implements Operation {
    StartDrag startDrag;
    int x, y;
    boolean dragging;
    Duel duel;
    Selectable item;
    Container target;

    public Drag(StartDrag startDrag, Duel duel, float fx, float fy) {
        this.startDrag = startDrag;
        this.duel = duel;
        this.x = (int)fx;
        this.y = (int)fy;
        item = startDrag.getItem();
    }

    public void move(float fx, float fy) {
        this.x = (int) fx;
        this.y = (int) fy;
    }

    public Container dropTo(float fx, float fy) {
        x = (int) fx;
        y = (int) fy;
        target = LayoutUtils.containerAt(duel, x,  y);
        dragging = false;
        return target;
    }


    @Override
    public Duel getDuel() {
        return duel;
    }

    @Override
    public Selectable getItem() {
        return item;
    }

    @Override
    public Container getContainer() {
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

    public StartDrag getStartDrag() {
        return startDrag;
    }
}
