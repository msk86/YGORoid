package org.msk86.ygoroid.newop;

import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.Item;

public interface Operation {
    public Container getBaseContainer();

    public Item getItem();

    public Container getContainer();

    public int x();

    public int y();
}
