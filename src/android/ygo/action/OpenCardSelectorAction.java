package android.ygo.action;

import android.ygo.core.CardList;
import android.ygo.core.CardSelector;
import android.ygo.core.Overlay;
import android.ygo.core.SelectableItem;
import android.ygo.op.Touch;

public class OpenCardSelectorAction extends BaseAction {
    public OpenCardSelectorAction(Touch touch) {
        super(touch.getDuel(), touch.getContainer(), touch.getItem());
    }

    @Override
    public void execute() {
        CardList list;
        SelectableItem source;
        if(item instanceof Overlay) {
            list = ((Overlay)item).getMaterials();
            source = item;
        } else {
            list = (CardList)item;
            source = item;
        }
        CardSelector selector = new CardSelector(source, list);
        duel.setCardSelector(selector);
    }
}
