package android.ygo.action;

import android.ygo.core.Card;
import android.ygo.core.HandCards;
import android.ygo.op.Operation;

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
