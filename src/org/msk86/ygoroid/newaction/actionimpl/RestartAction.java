package org.msk86.ygoroid.newaction.actionimpl;

import org.msk86.ygoroid.newop.Operation;

public class RestartAction extends BaseAction {
    public RestartAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        duel.restart();
    }
}
