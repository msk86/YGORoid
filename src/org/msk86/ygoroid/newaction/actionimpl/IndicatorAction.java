package org.msk86.ygoroid.newaction.actionimpl;

import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.OverRay;
import org.msk86.ygoroid.newop.Operation;
import org.msk86.ygoroid.newop.impl.VolClick;

public class IndicatorAction extends BaseAction {
    int vol = VolClick.VOL_DOWN;
    public IndicatorAction(Operation operation) {
        super(operation);
        if(operation instanceof VolClick) {
            vol = ((VolClick) operation).getVol();
        }
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
            switch (vol) {
                case VolClick.VOL_DOWN:
                    indicatorCard.getIndicator().decrease();
                    break;
                case VolClick.VOL_UP:
                    indicatorCard.getIndicator().increase();
                    break;
            }
        }
    }
}
