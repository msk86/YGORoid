package org.msk86.ygoroid.newaction.actionimpl;

import org.msk86.ygoroid.newop.Operation;

public class ChangeLpAction extends BaseAction {
    public ChangeLpAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        duel.getLifePointCalculator().calculate();
    }
}
