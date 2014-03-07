package org.msk86.ygoroid.newaction.actionimpl;

import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.Deck;
import org.msk86.ygoroid.newop.Operation;
import org.msk86.ygoroid.newop.impl.StartDrag;

public class DragDeckTopAction extends BaseAction {
    public DragDeckTopAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        if(operation instanceof StartDrag && item instanceof Deck) {
            Deck deck = (Deck) item;
            Card card = deck.getCardList().topCard();
            deck.getCardList().remove(card);
            ((StartDrag) operation).setDragItem(card);
            duel.select(card);
        }
    }
}
