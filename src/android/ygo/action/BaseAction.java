package android.ygo.action;

import android.ygo.core.Duel;
import android.ygo.core.Item;
import android.ygo.core.SelectableItem;

public abstract class BaseAction implements Action {
    protected Duel duel;
    protected Item container;
    protected SelectableItem item;

    protected BaseAction(Duel duel, Item container, SelectableItem item) {
        this.duel = duel;
        this.container = container;
        this.item = item;
    }
}
