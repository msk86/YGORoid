package android.ygo.touch;

import android.ygo.core.Duel;
import android.ygo.core.SelectableItem;

public class DoubleClick {
    int x;
    int y;

    SelectableItem item;

    public DoubleClick(Duel duel, int x, int y) {
        this.x = x;
        this.y = y;
        item = duel.itemAt(x, y);
    }

}
