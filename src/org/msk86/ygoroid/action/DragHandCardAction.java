package org.msk86.ygoroid.action;

import org.msk86.ygoroid.core.Card;
import org.msk86.ygoroid.core.HandCards;
import org.msk86.ygoroid.op.StartDrag;

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
