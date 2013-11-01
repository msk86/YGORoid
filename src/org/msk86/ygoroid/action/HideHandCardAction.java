package org.msk86.ygoroid.action;

import org.msk86.ygoroid.core.Card;
import org.msk86.ygoroid.core.HandCards;
import org.msk86.ygoroid.op.Operation;
import org.msk86.ygoroid.utils.Configuration;

public class HideHandCardAction extends BaseAction {
    public HideHandCardAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        HandCards handCards = (HandCards) container;
        handCards.setAll(true);
    }
}
