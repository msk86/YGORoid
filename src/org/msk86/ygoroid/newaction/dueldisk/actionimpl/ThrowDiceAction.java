package org.msk86.ygoroid.newaction.dueldisk.actionimpl;

import org.msk86.ygoroid.newop.Operation;

public class ThrowDiceAction extends BaseAction {
    public ThrowDiceAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        duel.getDice().throwDice();
    }
}
