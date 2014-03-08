package org.msk86.ygoroid.newop.impl;

import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.impl.Duel;
import org.msk86.ygoroid.newutils.LayoutUtils;

public class VolClick extends CommonOperation {
    public static final int VOL_UP = 1;
    public static final int VOL_DOWN = 2;

    int vol;
    public VolClick(Duel duel, int vol) {
        super(duel, -1, -1);
        item = (Item) duel.getCurrentSelectItem();
        container = LayoutUtils.itemContainer(duel, item);
        this.vol = vol;
    }

    public int getVol() {
        return vol;
    }
}
