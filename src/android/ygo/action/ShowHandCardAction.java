package android.ygo.action;

import android.util.Log;
import android.ygo.core.*;
import android.ygo.op.Operation;

public class ShowHandCardAction extends BaseAction {
    public ShowHandCardAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        HandCards handCards = (HandCards) container;
        for (Card card : handCards.getCards()) {
            card.open();
        }
    }
}
