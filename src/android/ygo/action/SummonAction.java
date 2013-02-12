package android.ygo.action;

import android.ygo.core.Card;
import android.ygo.core.Field;
import android.ygo.op.Touch;

public class SummonAction extends BaseAction {
    public SummonAction(Touch touch) {
        super(touch.getDuel(), touch.getContainer(), touch.getItem());
    }

    @Override
    public void execute() {
        Card card = (Card) item;
        card.open();
        card.positive();
        ((Field) container).setItem(card);
        duel.select(card);
    }
}
