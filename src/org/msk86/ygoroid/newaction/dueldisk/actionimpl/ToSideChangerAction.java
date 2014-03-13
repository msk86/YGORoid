package org.msk86.ygoroid.newaction.dueldisk.actionimpl;

import org.msk86.ygoroid.newcore.deck.DeckCards;
import org.msk86.ygoroid.newop.Operation;
import org.msk86.ygoroid.newutils.Utils;

public class ToSideChangerAction extends BaseAction {
    public ToSideChangerAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        if (duel.getDeckCards() != null) {
            DeckCards deckCards = duel.getDeckCards();
            Utils.getContext().showSideChangerWithDeck(deckCards);
        }
    }
}
