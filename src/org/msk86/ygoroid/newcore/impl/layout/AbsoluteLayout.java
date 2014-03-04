package org.msk86.ygoroid.newcore.impl.layout;

import android.graphics.Point;
import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Layout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AbsoluteLayout implements Layout {
    Container container;
    List<Item> items;
    Map<Item, Point> positionMap;
    Map<Item, Integer> zIndexMap;

    public AbsoluteLayout(Container container) {
        this.container = container;
        items = new ArrayList<Item>();
        positionMap = new HashMap<Item, Point>();
        zIndexMap = new HashMap<Item, Integer>();
    }

    public void addItem(Item item, int x, int y) {
        addItem(item, x, y, 0);
    }

    public void addItem(Item item, int x, int y, int zIndex) {
        if(items.contains(item)) {
            return;
        }
        items.add(item);
        Point pos = new Point(x, y);
        positionMap.put(item, pos);
        zIndexMap.put(item, zIndex);
    }
    public void removeItem(Item item) {
        items.remove(item);
        positionMap.remove(item);
        zIndexMap.remove(item);
    }
    @Override
    public List<? extends Item> items() {
        return items;
    }

    @Override
    public Item itemAt(int x, int y) {
        Item itemAtXY = null;
        for(Item item : items) {
            Point pos = positionMap.get(item);
            if(pos.x <= x && x < pos.x + item.getRenderer().size().width()
                    && pos.y <= y && y < pos.y + item.getRenderer().size().height()) {
                if(itemAtXY == null || zIndexMap.get(itemAtXY) < zIndexMap.get(item)) {
                    itemAtXY = item;
                }
            }
        }
        return itemAtXY;
    }

    @Override
    public Point itemPosition(Item item) {
        return positionMap.get(item);
    }
}
