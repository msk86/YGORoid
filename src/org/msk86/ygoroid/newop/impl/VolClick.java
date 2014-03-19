package org.msk86.ygoroid.newop.impl;

import org.msk86.ygoroid.newcore.BaseContainer;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newutils.LayoutUtils;

public class VolClick extends CommonOperation {
    public static final int VOL_UP = 1;
    public static final int VOL_DOWN = 2;

    int vol;
    public VolClick(BaseContainer baseContainer, int vol) {
        super(baseContainer, -1, -1);
        item = (Item) baseContainer.getCurrentSelectItem();
        container = LayoutUtils.itemContainer(baseContainer, item);
        this.vol = vol;
    }

    public int getVol() {
        return vol;
    }
}
