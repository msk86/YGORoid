package android.ygo.op;

import android.ygo.core.Duel;
import android.ygo.core.Item;
import android.ygo.core.SelectableItem;

public interface Touch {

    public Duel getDuel();

    public SelectableItem getItem();

    public Item getContainer();

    public int x();

    public int y();
}
