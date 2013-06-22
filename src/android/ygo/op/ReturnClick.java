package android.ygo.op;

import android.ygo.core.Duel;
import android.ygo.core.Item;
import android.ygo.core.SelectableItem;

public class ReturnClick implements Operation {

    Duel duel;
    SelectableItem item;
    Item container;

    public ReturnClick(Duel duel) {
        this.duel = duel;
        item = duel.getCurrentSelectItem();
        container = duel.getCurrentSelectItemContainer();

    }

    @Override
    public Duel getDuel() {
        return duel;
    }

    @Override
    public SelectableItem getItem() {
        return item;
    }

    @Override
    public Item getContainer() {
        return container;
    }

    @Override
    public int x() {
        return 0;
    }

    @Override
    public int y() {
        return 0;
    }
}
