package org.msk86.ygoroid.newop.impl;

import android.graphics.Point;
import android.view.MenuItem;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.impl.Duel;
import org.msk86.ygoroid.newutils.LayoutUtils;

public class MenuClick extends CommonOperation {
    MenuItem menuItem;
    public MenuClick(Duel duel, MenuItem menuItem) {
        super(duel, -1, -1);
        item = (Item)duel.getCurrentSelectItem();
        Point itemPos = duel.getLayout().itemPosition(item);
        container = LayoutUtils.containerAt(duel, itemPos.x, itemPos.y);
        this.menuItem = menuItem;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }
}
