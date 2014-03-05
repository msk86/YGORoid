package org.msk86.ygoroid.newop;

import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.Selectable;
import org.msk86.ygoroid.newcore.impl.Duel;

public class StartDrag implements Operation {
    int x, y;
    Duel duel;
    Container container;
    Selectable item;

    public StartDrag(Duel duel, float fx, float fy) {
        this.x = (int) fx;
        this.y = (int) fy;
        this.duel = duel;
    }

    @Override
    public Duel getDuel() {
        return duel;
    }

    public void setItem(Selectable item) {
        this.item = item;
    }

    @Override
    public Selectable getItem() {
        return item;
    }

    @Override
    public Container getContainer() {
        return container;
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
