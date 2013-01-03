package android.ygo.action;

import android.ygo.core.Duel;
import android.ygo.core.SelectableItem;

public class SelectAction implements Action {

    private Duel duel;
    private SelectableItem item;

    public SelectAction(Duel duel, SelectableItem item) {
        this.duel = duel;
        this.item = item;
    }


    @Override
    public void execute() {
        duel.unSelect();
        duel.select(item);
    }
}
