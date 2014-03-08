package org.msk86.ygoroid.newop.impl;

import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.impl.Duel;
import org.msk86.ygoroid.newutils.LayoutUtils;

public class ReturnClick extends CommonOperation {
    public ReturnClick(Duel duel) {
        super(duel, -1, -1);
        item = (Item) duel.getCurrentSelectItem();
        container = LayoutUtils.itemContainer(duel, item);
    }
}
