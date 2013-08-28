package org.msk86.ygoroid.action;

import org.msk86.ygoroid.core.Card;
import org.msk86.ygoroid.layout.Layout;
import org.msk86.ygoroid.op.Operation;

public class SelectSideAction extends BaseAction {
    public SelectSideAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        duel.select(item, container);
        duel.getSideWindow().setSelectCard((Card) item, (Layout) container);
    }
}
