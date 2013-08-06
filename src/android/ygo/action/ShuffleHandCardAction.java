package android.ygo.action;

import android.ygo.core.HandCards;
import android.ygo.op.Operation;

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
