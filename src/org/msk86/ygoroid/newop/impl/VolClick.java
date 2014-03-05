package org.msk86.ygoroid.newop.impl;

import android.graphics.Point;
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
        Point itemPos = duel.getLayout().itemPosition(item);
        container = LayoutUtils.containerAt(duel, itemPos.x, itemPos.y);
        this.vol = vol;
    }

    public int getVol() {
        return vol;
    }
}
