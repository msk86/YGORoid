package org.msk86.ygoroid.action;

import org.msk86.ygoroid.core.CardList;
import org.msk86.ygoroid.op.Operation;

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
