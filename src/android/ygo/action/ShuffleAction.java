package android.ygo.action;

import android.ygo.core.CardList;
import android.ygo.op.Operation;

public class ShuffleAction extends BaseAction {

    public ShuffleAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        CardList cardList = (CardList) item;
        cardList.shuffle();
        // show animation of shuffle
    }
}
