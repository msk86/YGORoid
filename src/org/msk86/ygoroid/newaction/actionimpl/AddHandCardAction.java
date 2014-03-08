package org.msk86.ygoroid.newaction.actionimpl;

import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.HandCards;
import org.msk86.ygoroid.newop.Operation;

public class AddHandCardAction extends BaseAction {
    public AddHandCardAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        HandCards handCards = (HandCards) container;
        handCards.add((Card) item);
    }
}
