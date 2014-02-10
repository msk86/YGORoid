package org.msk86.ygoroid.action;

import org.msk86.ygoroid.core.Card;
import org.msk86.ygoroid.core.Field;
import org.msk86.ygoroid.op.VolClick;

public class IndicatorAction extends BaseAction {
    private int vol;

    public IndicatorAction(VolClick operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
        this.vol = operation.getVol();
    }

    @Override
    public void execute() {
        Field field = (Field) container;
        Card card = field.getTopCard();

        if (card != null && card.isOpen()) {
            if (vol == VolClick.VOL_UP) {
                card.addIndicator();
            }
            if (vol == VolClick.VOL_DOWN) {
                card.removeIndicator();
            }
        }
    }
}
