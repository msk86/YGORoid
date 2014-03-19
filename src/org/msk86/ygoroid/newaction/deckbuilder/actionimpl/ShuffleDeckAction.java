package org.msk86.ygoroid.newaction.deckbuilder.actionimpl;

import org.msk86.ygoroid.newop.Operation;

public class ShuffleDeckAction extends BaseAction {
    public ShuffleDeckAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        deckBuilder.getCards().getMainDeckCards().shuffle();
    }
}
