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
        return itemAt((Container) item, x + pos.x, y + pos.y);
    }

    public static Container containerAt(Container container, int x, int y) {
        Item item = container.getLayout().itemAt(x, y);
        if (!(item instanceof Container)) {
            return container;
        }
        Point pos = container.getLayout().itemPosition(item);
        return containerAt((Container) item, x + pos.x, y + pos.y);
    }
}
