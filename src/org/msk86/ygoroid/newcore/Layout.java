package org.msk86.ygoroid.newcore;

import java.util.List;

public interface Layout {
    List<Item> items();
    Item itemAt(int x, int y);
}
