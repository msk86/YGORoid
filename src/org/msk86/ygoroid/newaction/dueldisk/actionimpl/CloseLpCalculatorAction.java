package org.msk86.ygoroid.newaction.dueldisk.actionimpl;

import org.msk86.ygoroid.newop.Operation;

public class CloseLpCalculatorAction extends BaseAction {
    public CloseLpCalculatorAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        duel.setLifePointCalculator(null);
    }
}
