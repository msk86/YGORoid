package org.msk86.ygoroid.newaction.actionimpl;

import org.msk86.ygoroid.newcore.impl.Deck;
import org.msk86.ygoroid.newop.Operation;

public class ShuffleDeckAction extends BaseAction {
    public ShuffleDeckAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        Deck deck = (Deck) item;
        deck.getCardList().shuffle();
    }
}
