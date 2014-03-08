package org.msk86.ygoroid.newaction.actionimpl;

import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.OverRay;
import org.msk86.ygoroid.newop.Operation;

public class IndicatorIncreaseAction extends BaseAction {
    public IndicatorIncreaseAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        Card indicatorCard = null;
        if(item instanceof Card) {
            indicatorCard = (Card) item;
        } else if (item instanceof OverRay) {
            indicatorCard = ((OverRay) item).getOverRayCards().topCard();
        }
        if(indicatorCard != null && indicatorCard.isOpen()) {
            indicatorCard.getIndicator().increase();
        }
    }
}
