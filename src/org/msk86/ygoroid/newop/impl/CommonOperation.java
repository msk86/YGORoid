package org.msk86.ygoroid.newop.impl;

import org.msk86.ygoroid.newcore.BaseContainer;
import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newop.Operation;
import org.msk86.ygoroid.newutils.LayoutUtils;

public class CommonOperation implements Operation {
    int x, y;
    Container baseContainer;
    Container container;
    Item item;

    public CommonOperation(BaseContainer baseContainer, float fx, float fy) {
        this.x = (int) fx;
        this.y = (int) fy;
        this.baseContainer = baseContainer;
        container = LayoutUtils.containerAt(baseContainer, x, y);
        item = LayoutUtils.itemAt(baseContainer, x, y);
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
        return x;
    }

    @Override
    public int y() {
        return y;
    }
}
