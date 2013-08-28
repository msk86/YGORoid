package org.msk86.ygoroid.action;

import org.msk86.ygoroid.core.Card;
import org.msk86.ygoroid.core.CardList;
import org.msk86.ygoroid.core.Deck;
import org.msk86.ygoroid.op.Operation;

public class CloseRemoveTopAction extends BaseAction {
    public CloseRemoveTopAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        Card card;
        Deck deck = (Deck) item;
        card = deck.pop();
        CardList removed = (CardList) duel.getDuelFields().getRemovedField().getItem();
        removed.push(card, true);
    }
}
