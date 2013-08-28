package org.msk86.ygoroid.action;

import org.msk86.ygoroid.core.HandCards;
import org.msk86.ygoroid.op.Operation;

public class ShuffleHandCardAction extends BaseAction {

    public ShuffleHandCardAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        HandCards handCards = (HandCards) container;
        handCards.shuffle();
    }
}
