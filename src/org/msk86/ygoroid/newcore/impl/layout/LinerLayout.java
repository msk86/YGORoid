package org.msk86.ygoroid.newcore.impl.layout;

import android.graphics.Point;
import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Layout;
import org.msk86.ygoroid.newutils.Style;

import java.util.List;

public class LinerLayout implements Layout {
    Container container;
    List<? extends Item> items;

    boolean centerAligned = false;
    int maxPadding = Style.padding();

    public LinerLayout(Container container, List<? extends Item> items) {
        this.container = container;
        this.items = items;
    }

    public boolean isCenterAligned() {
        return centerAligned;
    }

    public LinerLayout setCenterAligned(boolean centerAligned) {
        this.centerAligned = centerAligned;
        return this;
    }

    public int getMaxPadding() {
        return maxPadding;
    }

    public LinerLayout setMaxPadding(int maxPadding) {
        this.maxPadding = maxPadding;
        return this;
    }

    @Override
    public List<? extends Item> items() {
        return items;
    }

    @Override
    public Item itemAt(int x, int y) {
        int rx = x - centerOffset();
        int currentRightX = 0;
        for (int i = 0; i< items().size(); i++) {
            Item item = items().get(i);
            currentRightX += item.getRenderer().size().width();
            if(i != items().size() - 1) {
                currentRightX += getPadding();
            }
            if(rx < currentRightX) {
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

        int x = centerOffset();
        for (int i = 1; i <= index; i++) {
            x += items().get(i - 1).getRenderer().size().width() + getPadding();
        }

        return new Point(x, 0);
    }

    private int getPadding() {
        if(items().size() == 0) {
            return 0;
        }
        int containerWidth = ((Item) this.container).getRenderer().size().width();

        int totalItemWidth = 0;
        for (Item item : items()) {
            totalItemWidth += item.getRenderer().size().width();
        }

        int padding = (containerWidth - totalItemWidth) / (items().size() - 1);

        return Math.min(padding, getMaxPadding());
    }

    private int getTotalWidth() {
        int totalItemWidth = 0;
        for (Item item : items()) {
            totalItemWidth += item.getRenderer().size().width();
        }
        return totalItemWidth + getPadding() * (items().size() - 1);
    }

    private int centerOffset() {
        if (centerAligned) {
            return (((Item) container).getRenderer().size().width() - getTotalWidth()) / 2;
        } else {
            return 0;
        }
    }
}
