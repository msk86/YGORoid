package org.msk86.ygoroid.newcore.impl.layout;

import android.graphics.Point;
import android.util.Log;
import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Layout;
import org.msk86.ygoroid.newutils.Style;

import java.util.List;

public class VerticalLayout implements Layout {
    Container container;
    List<? extends Item> items;

    int maxPadding = Style.padding();

    public VerticalLayout(Container container, List<? extends Item> items) {
        this.container = container;
        this.items = items;
    }

    public int getMaxPadding() {
        return maxPadding;
    }

    public VerticalLayout setMaxPadding(int maxPadding) {
        this.maxPadding = maxPadding;
        return this;
    }

    @Override
    public List<? extends Item> items() {
        return items;
    }

    @Override
    public Item itemAt(int x, int y) {
        int currentBottomY = 0;
        for (int i = 0; i< items().size(); i++) {
            Item item = items().get(i);
            currentBottomY += item.getRenderer().size().height();
            if(i != items().size() - 1) {
                currentBottomY += getPadding();
            }
            if(y < currentBottomY) {
                return item;
            }
        }

        return null;
    }

    @Override
    public Point itemPosition(Item item) {
        if (!items().contains(item)) {
            return null;
        }
        int index = items().indexOf(item);

        int y = 0;
        for (int i = 1; i <= index; i++) {
            y += items().get(i - 1).getRenderer().size().height() + getPadding();
            Log.e("YGO", "Y:" + items().get(i - 1).getRenderer().size().height());
        }

        return new Point(0, y);
    }

    private int getPadding() {
        if(items().size() == 0 || items().size() == 1) {
            return 0;
        }
        int containerHeight = ((Item) this.container).getRenderer().size().height();

        int totalItemHeight = 0;
        for (Item item : items()) {
            totalItemHeight += item.getRenderer().size().height();
        }

        int padding = (containerHeight - totalItemHeight) / (items().size() - 1);

        return Math.min(padding, getMaxPadding());
    }

    private int getTotalHeight() {
        int totalItemHeight = 0;
        for (Item item : items()) {
            totalItemHeight += item.getRenderer().size().height();
        }
        return totalItemHeight + getPadding() * (items().size() - 1);
    }
}