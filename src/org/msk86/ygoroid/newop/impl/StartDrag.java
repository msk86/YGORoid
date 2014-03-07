package org.msk86.ygoroid.newop.impl;

import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.impl.Duel;

public class StartDrag extends CommonOperation {
    public StartDrag(Duel duel, float fx, float fy) {
        super(duel, fx, fy);
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
