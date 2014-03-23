package org.msk86.ygoroid.newaction.dueldisk.actionimpl;

import org.msk86.ygoroid.newcore.Listable;
import org.msk86.ygoroid.newcore.Selectable;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.CardSelector;
import org.msk86.ygoroid.newop.Operation;
import org.msk86.ygoroid.newop.impl.StartDrag;

public class DragCardSelectorCardAction extends BaseAction {
    public DragCardSelectorCardAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        if(item instanceof Card && container instanceof CardSelector) {
            Card card = (Card) item;
            CardSelector selector = (CardSelector) container;
            Listable listable = selector.getSource();
            if(!listable.listCards().isOpen()) {
                card.open();
            }
            listable.listCards().remove(card);
            ((StartDrag) operation).setDragItem(card);
            if(item instanceof Selectable) {
                duel.select((Selectable) item);
            }
        }
    }
}
