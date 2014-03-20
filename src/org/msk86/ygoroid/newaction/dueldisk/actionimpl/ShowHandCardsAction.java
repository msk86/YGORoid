package org.msk86.ygoroid.newaction.dueldisk.actionimpl;

import org.msk86.ygoroid.newcore.impl.HandCards;
import org.msk86.ygoroid.newop.Operation;

public class ShowHandCardsAction extends BaseAction {
    public ShowHandCardsAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        HandCards handCards = (HandCards) container;
        handCards.getCardList().openAll();
        handCards.getCardList().setOpen(true);
    }
}
