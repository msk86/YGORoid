package org.msk86.ygoroid.action;

import org.msk86.ygoroid.op.Operation;

public class CoinAction extends BaseAction {
    public CoinAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        duel.getCoin().throwCoin();
    }
}
