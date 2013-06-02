package android.ygo.action;

import android.ygo.core.Card;
import android.ygo.core.HandCards;
import android.ygo.op.StartDrag;

public class DragHandCardAction extends BaseAction {
    public DragHandCardAction(StartDrag startDrag) {
        super(startDrag);
    }

    @Override
    public void execute() {
        Card card = (Card) item;
        HandCards handCards = (HandCards) container;
        handCards.remove(card);
        duel.select(card, container);
        ((StartDrag) operation).setDragItem(card);
    }
}
