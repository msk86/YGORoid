package org.msk86.ygoroid.newutils;

import android.graphics.Point;
import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.Item;

public class LayoutUtils {
    public static Item itemAt(Container container, int x, int y) {
        Item item = container.getLayout().itemAt(x, y);
        if (!(item instanceof Container)) {
            return item;
        }
        Point pos = container.getLayout().itemPosition(item);
        return itemAt((Container) item, x - pos.x, y - pos.y);
    }

    public static Container containerAt(Container container, int x, int y) {
        Item item = container.getLayout().itemAt(x, y);
        if (!(item instanceof Container)) {
            return container;
        }
        Point pos = container.getLayout().itemPosition(item);
        return containerAt((Container) item, x - pos.x, y - pos.y);
    }

    public static Point itemPosition(Container container, Item item) {
        if(container.getLayout().items().contains(item)) {
            return container.getLayout().itemPosition(item);
        }
        for(Item layoutItem : container.getLayout().items()) {
            if(layoutItem instanceof Container) {
                Point containerPos = container.getLayout().itemPosition(layoutItem);
                Point pos = itemPosition((Container) layoutItem, item);
                if(pos != null) {
                    return new Point(pos.x + containerPos.x, pos.y + containerPos.y);
                }
            }
        }
        return null;
    }

    public static Container itemContainer(Container mainContainer, Item item) {
        Point itemPos = itemPosition(mainContainer, item);
        if(itemPos != null) {
            return LayoutUtils.containerAt(mainContainer, itemPos.x, itemPos.y);
        }
        return null;
    }
}
