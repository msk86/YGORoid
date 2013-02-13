package android.ygo.action;

import android.ygo.core.CardList;
import android.ygo.core.CardSelector;
import android.ygo.core.Overlay;
import android.ygo.core.SelectableItem;
import android.ygo.op.Operation;

public class OpenCardSelectorAction extends BaseAction {
    public OpenCardSelectorAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        CardList list;
        SelectableItem source;
        if (item instanceof Overlay) {
            list = ((Overlay) item).getMaterials();
            source = item;
        } else {
            list = (CardList) item;
            source = item;
        }
        if (list.size() == 0) {
            return;
        }
        CardSelector selector = new CardSelector(source, list);
        duel.setCardSelector(selector);
    }
}
