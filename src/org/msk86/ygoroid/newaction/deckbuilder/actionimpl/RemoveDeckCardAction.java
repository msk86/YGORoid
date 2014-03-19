package org.msk86.ygoroid.newaction.deckbuilder.actionimpl;

import org.msk86.ygoroid.newcore.deck.DeckCards;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newop.Operation;

public class RemoveDeckCardAction extends BaseAction {
    public RemoveDeckCardAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        Card card = (Card) item;
        DeckCards cards = deckBuilder.getCards();
        cards.getMainDeckCards().remove(card);
        cards.getExDeckCards().remove(card);
        cards.getSideDeckCards().remove(card);
        if(deckBuilder.getInfoBar().getInfoItem() == card) {
            deckBuilder.getInfoBar().clearInfo();
        }
    }
}
