package org.msk86.ygoroid.newaction.deckbuilder.actionimpl;

import org.msk86.ygoroid.newop.Operation;

public class SortDeckAction extends BaseAction {
    public SortDeckAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        deckBuilder.getCards().getMainDeckCards().sort();
        deckBuilder.getCards().getExDeckCards().sort();
        deckBuilder.getCards().getSideDeckCards().sort();
    }
}
