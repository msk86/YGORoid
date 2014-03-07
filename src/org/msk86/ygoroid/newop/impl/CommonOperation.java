package org.msk86.ygoroid.newop.impl;

import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.impl.Duel;
import org.msk86.ygoroid.newop.Operation;
import org.msk86.ygoroid.newutils.LayoutUtils;

public class CommonOperation implements Operation {
    int x, y;
    Duel duel;
    Container container;
    Item item;

    public CommonOperation(Duel duel, float fx, float fy) {
        this.x = (int) fx;
        this.y = (int) fy;
        this.duel = duel;
        container = LayoutUtils.containerAt(duel, x, y);
        item = LayoutUtils.itemAt(duel, x, y);
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
        return x;
    }

    @Override
    public int y() {
        return y;
    }
}
