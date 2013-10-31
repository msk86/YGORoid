package org.msk86.ygoroid.action;

import org.msk86.ygoroid.core.*;
import org.msk86.ygoroid.op.Operation;

public class NextPageDescAction extends BaseAction {
    public NextPageDescAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        duel.getCardWindow().nextPage();
    }
}
