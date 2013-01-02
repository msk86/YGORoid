package android.ygo.touch;

import android.ygo.core.Duel;
import android.ygo.core.SelectableItem;

public class Click {
    int x;
    int y;

    SelectableItem item;

    public Click(Duel duel, int x, int y) {
        this.x = x;
        this.y = y;
        item = duel.selectAt(x, y);
    }

    public SelectableItem getItem() {
        return item;
    }

}
