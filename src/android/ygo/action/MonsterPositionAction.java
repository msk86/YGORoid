package android.ygo.action;

import android.ygo.core.Card;
import android.ygo.core.Overlay;
import android.ygo.op.Touch;

public class MonsterPositionAction extends BaseAction {

    public MonsterPositionAction(Touch touch) {
        super(touch.getDuel(), touch.getContainer(), touch.getItem());
    }

    @Override
    public void execute() {
        Card card;
        if (item instanceof Overlay) {
            card = ((Overlay) item).topCard();
        } else {
            card = (Card) item;
        }
        if (card != null) {
            card.changePosition();
            duel.select(card);
        }
    }
}
