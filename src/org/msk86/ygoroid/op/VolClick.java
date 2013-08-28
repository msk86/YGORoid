package org.msk86.ygoroid.op;

import org.msk86.ygoroid.core.Duel;
import org.msk86.ygoroid.core.Item;
import org.msk86.ygoroid.core.SelectableItem;

public class VolClick implements Operation {
    public static final int VOL_UP = 1;
    public static final int VOL_DOWN = 2;

    int vol;
    Duel duel;
    SelectableItem item;
    Item container;

    public VolClick(Duel duel, int vol) {
        this.duel = duel;
        item = duel.getCurrentSelectItem();
        container = duel.getCurrentSelectItemContainer();
        this.vol = vol;
    }

    public int getVol() {
        return vol;
    }

    @Override
    public Duel getDuel() {
        return duel;
    }

    @Override
    public SelectableItem getItem() {
        return item;
    }

    @Override
    public Item getContainer() {
        return container;
    }

    @Override
    public int x() {
        return 0;
    }

    @Override
    public int y() {
        return 0;
    }
}
