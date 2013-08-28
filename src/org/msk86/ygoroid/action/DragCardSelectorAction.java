package org.msk86.ygoroid.action;

import org.msk86.ygoroid.core.Card;
import org.msk86.ygoroid.core.CardList;
import org.msk86.ygoroid.op.StartDrag;

public class DragCardSelectorAction extends CloseCardSelectorAction {
    public DragCardSelectorAction(StartDrag startDrag) {
        super(startDrag);
    }

    @Override
    public void execute() {
        Card card = (Card) item;
        CardList cardList = duel.getCardSelector().getCardList();
        cardList.remove(card);
        ((StartDrag) operation).setDragItem(card);

        super.execute();

        duel.select(card, container);
    }
}
