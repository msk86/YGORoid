package org.msk86.ygoroid.action;

import org.msk86.ygoroid.op.Operation;

public class RestartAction extends BaseAction {
    public RestartAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        duel.restart();
    }
}
