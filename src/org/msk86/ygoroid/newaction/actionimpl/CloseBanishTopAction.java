package org.msk86.ygoroid.newaction.actionimpl;

import org.msk86.ygoroid.newcore.constant.FieldType;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.Deck;
import org.msk86.ygoroid.newop.Operation;

public class CloseBanishTopAction extends BaseAction {
    public CloseBanishTopAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        Card card;
        Deck deck = (Deck) item;
        card = deck.getCardList().pop();
        if(card != null) {
            card.set();
            Deck removed = (Deck) duel.getDuelFields().getField(FieldType.BANISHED).getItem();
            removed.getCardList().push(card, true);
        }
    }
}
