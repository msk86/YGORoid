package org.msk86.ygoroid.newaction.actionimpl;

import org.msk86.ygoroid.newcore.Listable;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.CardSelector;
import org.msk86.ygoroid.newcore.impl.Deck;
import org.msk86.ygoroid.newop.Operation;

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
        }
    }
}
