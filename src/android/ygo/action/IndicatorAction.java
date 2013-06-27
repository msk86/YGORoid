package android.ygo.action;

import android.ygo.core.Card;
import android.ygo.core.Field;
import android.ygo.core.OverRay;
import android.ygo.op.VolClick;

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

        if(card != null && card.isOpen()) {
            if(vol == VolClick.VOL_UP) {
                card.addIndicator();
            }
            if(vol == VolClick.VOL_DOWN) {
                card.removeIndicator();
            }
        }
    }
}
