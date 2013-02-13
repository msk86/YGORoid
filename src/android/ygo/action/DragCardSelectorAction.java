package android.ygo.action;

import android.ygo.core.Card;
import android.ygo.core.CardList;
import android.ygo.op.StartDrag;

public class DragCardSelectorAction extends CloseCardSelectorAction {
    public DragCardSelectorAction(StartDrag startDrag) {
        super(startDrag);
    }

    @Override
    public void execute() {
        Card card = (Card) item;
        CardList cardList = (CardList) container;
        cardList.remove(card);
        ((StartDrag) operation).setDragItem(card);

        super.execute();

        duel.select(card);
    }
}
