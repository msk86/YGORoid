package org.msk86.ygoroid.newop.impl;

import android.graphics.Point;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.impl.Duel;
import org.msk86.ygoroid.newutils.LayoutUtils;

public class ReturnClick extends CommonOperation {
    public ReturnClick(Duel duel) {
        super(duel, -1, -1);
        item = (Item) duel.getCurrentSelectItem();
        Point itemPos = duel.getLayout().itemPosition(item);
        if(itemPos != null) {
            container = LayoutUtils.containerAt(duel, itemPos.x, itemPos.y);
        }
    }
}
