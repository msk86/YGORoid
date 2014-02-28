package org.msk86.ygoroid.newcore.impl.layout;

import android.graphics.Point;
import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Layout;
import org.msk86.ygoroid.size.Size;

import java.util.ArrayList;
import java.util.List;

public class CenterLayout implements Layout {
    Container container;
    Item item;


    public CenterLayout(Container container, Item item) {
        this.container = container;
        this.item = item;
    }

    @Override
    public List<? extends Item> items() {
        List<Item> items = new ArrayList<Item>();
        items.add(item);
        return items;
    }

    @Override
    public Item itemAt(int x, int y) {
        return item;
    }

    @Override
    public Point itemPosition(Item item) {
        if(item == null || item != this.item) {
            return null;
        }

        int offsetX, offsetY;

        Size containerSize = ((Item)container).getRenderer().size();
        Size itemSize = this.item.getRenderer().size();

        offsetX = (containerSize.width() - itemSize.width()) / 2;
        offsetY = (containerSize.height() - itemSize.height()) / 2;
        return new Point(offsetX, offsetY);
    }
}
