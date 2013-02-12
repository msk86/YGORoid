package android.ygo.op;

import android.ygo.core.Duel;
import android.ygo.core.SelectableItem;

public class ReturnClick implements ButtonClick {

    Duel duel;
    SelectableItem item;

    public ReturnClick(Duel duel) {
        this.duel = duel;
        item = duel.getCurrentSelectItem();
    }
    
    @Override
    public Duel getDuel() {
        return duel; 
    }

    @Override
    public SelectableItem getItem() {
        return item; 
    }
}
