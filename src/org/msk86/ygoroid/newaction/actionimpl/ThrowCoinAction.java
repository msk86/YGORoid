package org.msk86.ygoroid.newaction.actionimpl;

import org.msk86.ygoroid.newop.Operation;

public class ThrowCoinAction extends BaseAction {
    public ThrowCoinAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        duel.getCoin().throwCoin();
    }
}
