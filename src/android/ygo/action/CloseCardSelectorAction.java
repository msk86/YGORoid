package android.ygo.action;

import android.ygo.core.CardList;
import android.ygo.core.CardSelector;
import android.ygo.core.Overlay;
import android.ygo.core.SelectableItem;
import android.ygo.touch.Touch;

public class CloseCardSelectorAction extends BaseAction {
    public CloseCardSelectorAction(Touch touch) {
        super(touch.getDuel(), touch.getContainer(), touch.getItem());
    }

    @Override
    public void execute() {
        CardSelector selector = duel.getCardSelector();
        duel.setCardSelector(null);
        duel.select(selector.getSourceItem());
    }
}
