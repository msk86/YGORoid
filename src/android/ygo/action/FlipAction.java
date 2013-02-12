package android.ygo.action;

import android.ygo.core.Card;
import android.ygo.core.Overlay;
import android.ygo.op.Touch;

public class FlipAction extends BaseAction {
    public FlipAction(Touch touch) {
        super(touch.getDuel(), touch.getContainer(), touch.getItem());
    }

    @Override
    public void execute() {
        Card card = null;
        if (item instanceof Card) {
            card = (Card) item;
        } else if (item instanceof Overlay) {
            card = ((Overlay) item).topCard();
        }
        if (card != null) {
            card.flip();
            duel.select(card);
        }
    }
}
