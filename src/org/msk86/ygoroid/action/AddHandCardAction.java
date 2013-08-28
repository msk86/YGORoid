package org.msk86.ygoroid.action;

import org.msk86.ygoroid.core.Card;
import org.msk86.ygoroid.core.HandCards;
import org.msk86.ygoroid.op.Operation;

public class AddHandCardAction extends BaseAction {
    public AddHandCardAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        HandCards hc = (HandCards) container;
        hc.add((Card) item);
        duel.select(item, container);
    }
}
