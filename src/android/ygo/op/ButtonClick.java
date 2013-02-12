package android.ygo.op;

import android.ygo.core.Duel;
import android.ygo.core.SelectableItem;

public interface ButtonClick {

    public Duel getDuel();

    public SelectableItem getItem();
}
