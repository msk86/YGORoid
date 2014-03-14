package org.msk86.ygoroid.newop.impl;

import org.msk86.ygoroid.newcore.BaseContainer;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newutils.LayoutUtils;

public class ReturnClick extends CommonOperation {
    public ReturnClick(BaseContainer baseContainer) {
        super(baseContainer, -1, -1);
        item = (Item) baseContainer.getCurrentSelectItem();
        container = LayoutUtils.itemContainer(baseContainer, item);
    }
}
