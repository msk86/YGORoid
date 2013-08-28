package org.msk86.ygoroid.action;

import org.msk86.ygoroid.op.Operation;

public class CloseCardWindowAction extends BaseAction {
    public CloseCardWindowAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        duel.setCardWindow(null);
    }
}
