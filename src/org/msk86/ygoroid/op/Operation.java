package org.msk86.ygoroid.op;

import org.msk86.ygoroid.core.Duel;
import org.msk86.ygoroid.core.Item;
import org.msk86.ygoroid.core.SelectableItem;

public interface Operation {

    public Duel getDuel();

    public SelectableItem getItem();

    public Item getContainer();

    public int x();

    public int y();
}
