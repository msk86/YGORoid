package org.msk86.ygoroid.action;

import org.msk86.ygoroid.op.Operation;

public class SelectAction extends BaseAction {

    public SelectAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        duel.select(item, container);
    }
}
