package android.ygo.action;

import android.ygo.core.Card;
import android.ygo.core.CardList;
import android.ygo.core.Deck;
import android.ygo.core.Overlay;
import android.ygo.touch.Touch;

public class SelectAction extends BaseAction {

    public SelectAction(Touch touch) {
        super(touch.getDuel(), touch.getContainer(), touch.getItem());
    }

    @Override
    public void execute() {
        duel.select(item);
    }
}
