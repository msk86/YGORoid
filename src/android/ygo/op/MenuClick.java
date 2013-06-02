package android.ygo.op;

import android.view.MenuItem;
import android.ygo.core.Duel;
import android.ygo.core.Item;
import android.ygo.core.SelectableItem;

public class MenuClick implements Operation {

    Duel duel;

    private MenuItem menuItem;

    private Item container;

    SelectableItem item;
    public MenuClick(Duel duel, MenuItem menuItem) {
        this.duel = duel;
        this.menuItem = menuItem;
        item = duel.getCurrentSelectItem();
        container = duel.getCurrentSelectItemContainer();
    }

    @Override
    public Duel getDuel() {
        return duel;
    }

    public MenuItem getMenuItem() {
        return menuItem;
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
