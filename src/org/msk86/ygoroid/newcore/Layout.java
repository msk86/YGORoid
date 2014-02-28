package org.msk86.ygoroid.newcore;

import android.graphics.Point;

import java.util.List;

public interface Layout {
    List<? extends Item> items();
    Item itemAt(int x, int y);
    Point itemPosition(Item item);
}
