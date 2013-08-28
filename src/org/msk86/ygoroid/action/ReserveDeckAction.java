package org.msk86.ygoroid.action;

import org.msk86.ygoroid.core.Deck;
import org.msk86.ygoroid.op.Operation;

public class ReserveDeckAction extends BaseAction {

    public ReserveDeckAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        Deck deck = (Deck) item;
        deck.reserve();
    }
}
