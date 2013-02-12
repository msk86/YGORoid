package android.ygo.action;

import android.ygo.core.Card;
import android.ygo.core.HandCards;
import android.ygo.op.Touch;

public class AddHandCardAction extends BaseAction {
    public AddHandCardAction(Touch touch) {
        super(touch.getDuel(), touch.getContainer(), touch.getItem());
    }

    @Override
    public void execute() {
        HandCards hc = (HandCards) container;
        hc.add((Card) item);
        duel.select(item);
    }
}
