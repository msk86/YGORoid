package org.msk86.ygoroid.newaction.dueldisk.actionimpl;

import org.msk86.ygoroid.newcore.Selectable;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.HandCards;
import org.msk86.ygoroid.newop.Operation;
import org.msk86.ygoroid.newop.impl.StartDrag;

public class DragHandCardAction extends BaseAction {
    public DragHandCardAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        Card card = (Card) item;
        HandCards handCards = (HandCards) container;
        handCards.getCardList().remove(card);
        ((StartDrag) operation).setDragItem(card);
        if(item instanceof Selectable) {
            duel.select((Selectable) item);
        }
    }
}
