package org.msk86.ygoroid.newop;

import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Selectable;
import org.msk86.ygoroid.newcore.impl.Duel;

public interface Operation {
    public Duel getDuel();

    public Item getItem();

    public Container getContainer();

    public int x();

    public int y();
}
